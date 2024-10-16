package com.ggb.graduationgoodbye.domain.member;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserInfoProvider;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotSupportedSnsTypeException;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.business.NicknameProvider;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberInfoDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.TokenReissueDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.DuplicateNicknameException;
import com.ggb.graduationgoodbye.domain.member.common.exception.MaxNicknameCountExceededException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NegativeNicknameCountException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotExistsRemainNicknameException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotSignUpException;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import com.ggb.graduationgoodbye.global.test.IntegrationTest;
import com.ggb.graduationgoodbye.global.util.randomValue.RandomEntityPopulator;
import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MemberTest extends IntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private TokenService tokenService;

  @MockBean
  private OAuthUserInfoProvider oAuthUserInfoProvider;
  @MockBean
  private NicknameProvider nicknameProvider;

  private final RandomEntityPopulator randomEntityPopulator;

  public MemberTest(@Autowired SqlSessionFactory sqlSessionFactory) {
    this.randomEntityPopulator = new RandomEntityPopulator(sqlSessionFactory, "MEMBERS");
  }

  // note : 현재 RandomValueGenerator null 값 생성 문제로 RandomEntityPopulator 사용, 수정 예정
  Member createMember() {
    randomEntityPopulator.setValue("snsType" , SnsType.GOOGLE);
    return (Member) randomEntityPopulator.getPopulatedEntity(Member.class);
  }

  @Test
  void login_비회원() {
    // given
    String snsType = "google";

    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode("authCode")
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/login/" + snsType;

    UnAuthenticatedException exception = new NotSignUpException();

    OAuthUserInfoDto oauthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId("djkagj21")
        .email("djkagj21@gmail.com")
        .profile("profile")
        .oauthToken("token")
        .build();


    // when
    when(oAuthUserInfoProvider.provideOAuthUserInfoByAuthCode(anyString(), anyString()))
        .thenReturn(oauthUserInfoDto);

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<String> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        new TypeToken<ApiResponse<String>>() {}.getType()
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertNotNull(apiResponse);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
    assertThat(apiResponse.getData()).isEqualTo(oauthUserInfoDto.getOauthToken());

  }


  @Test
  void login_회원() {
    // given
    Member member = createMember();
    memberRepository.save(member);

    String snsType = String.valueOf(member.getSnsType());

    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode("authCode")
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/login/" + snsType;

    OAuthUserInfoDto oauthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId(member.getSnsId())
        .email(member.getEmail())
        .profile(member.getProfile())
        .oauthToken("token")
        .build();


    // when
    when(oAuthUserInfoProvider.provideOAuthUserInfoByAuthCode(anyString(), anyString()))
        .thenReturn(oauthUserInfoDto);

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<MemberLoginDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        new TypeToken<ApiResponse<MemberLoginDto.Response>>(){}
    );


    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNotNull(apiResponse.getData());
    assertNotNull(apiResponse.getData().getAccessToken());
    assertNotNull(apiResponse.getData().getRefreshToken());

  }


  @Test
  void login_유효하지_않은_snsType() {

    String snsType = "daum";

    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode("authCode")
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/login/" + snsType;

    BusinessException exception = new NotSupportedSnsTypeException();

    when(oAuthUserInfoProvider.provideOAuthUserInfoByAuthCode(anyString(), anyString()))
        .thenThrow(exception);

    // when
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<MemberLoginDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertNull(apiResponse.getData());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());

  }

  // note : MemberLoginDto에 Not작성되어있는
  // NotBlank(message = "인가코드를 입력 바랍니다.") 는 어떻게 가져오면 좋을지..
  @Test
  void login_인가코드_미전달() {

    String snsType = "daum";

    MemberLoginDto.Request request = new MemberLoginDto.Request();

    String url = "http://localhost:" + port + "/api/v1/members/login/" + snsType;

    BusinessException exception = new BusinessException();

    // when
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<MemberLoginDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());

  }


  @Test
  void signup_성공() {

    // given
    String snsType = "google";

    MemberJoinDto.Request request = MemberJoinDto.Request.builder()
        .oauthToken("asdasd")
        .nickname("nickName")
        .build();

    OAuthUserInfoDto oauthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId("snsId")
        .email("email")
        .profile("profile")
        .oauthToken("token")
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/signup/" + snsType;

    // when
    when(oAuthUserInfoProvider.provideOAuthUserInfoByOAuthToken(anyString(), anyString()))
        .thenReturn(oauthUserInfoDto);


    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<MemberJoinDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        new TypeToken<ApiResponse<MemberJoinDto.Response>>(){}
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNotNull(apiResponse.getData());
    assertNotNull(apiResponse.getData().getAccessToken());
    assertNotNull(apiResponse.getData().getRefreshToken());

  }

  @Test
  void signup_필수값_미전달() {

    // given
    String snsType = "google";

    MemberJoinDto.Request request = MemberJoinDto.Request.builder()
        .oauthToken(null)
        .nickname(null)
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/signup/" + snsType;

    BusinessException exception = new BusinessException();

    // when
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<MemberJoinDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  void withdraw_성공() {
    // given
    Member member = createMember();
    memberRepository.save(member);

    TokenDto tokenDto = tokenService.getToken(member);

    String url = "http://localhost:" + port + "/api/v1/members/withdraw";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(tokenDto.getAccessToken());
    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

    // when
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        url,
        HttpMethod.DELETE,
        requestEntity,
        String.class
    );

    ApiResponse<MemberJoinDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNull(apiResponse.getData());
  }

  @Test
  void withdraw_토큰_미전달() {
    // given
    String url = "http://localhost:" + port + "/api/v1/members/withdraw";

    // when
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        url,
        HttpMethod.DELETE,
        null,
        String.class
    );

    ApiResponse<MemberJoinDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertNull(apiResponse.getData());
  }

  @Test
  void info() {
    // given
    Member member = createMember();
    memberRepository.save(member);

    TokenDto tokenDto = tokenService.getToken(member);

    String url = "http://localhost:" + port + "/api/v1/members/info";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(tokenDto.getAccessToken());
    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

    // when
    ResponseEntity<String> responseEntity = restTemplate.exchange(
        url,
        HttpMethod.GET,
        requestEntity,
        String.class
    );

    ApiResponse<MemberInfoDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        new TypeToken<ApiResponse<MemberInfoDto.Response>>(){}.getType()
    );


    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNotNull(apiResponse.getData());
    assertThat(apiResponse.getData().getId()).isEqualTo(member.getId());
    assertThat(apiResponse.getData().getSnsType()).isEqualTo(String.valueOf(member.getSnsType()));
    assertThat(apiResponse.getData().getEmail()).isEqualTo(member.getEmail());
    assertThat(apiResponse.getData().getProfile()).isEqualTo(member.getProfile());
    assertThat(apiResponse.getData().getNickname()).isEqualTo(member.getNickname());
  }
//
  @Test
  void reissue() {
    // given
    Member member = createMember();
    memberRepository.save(member);

    TokenDto tokenDto = tokenService.getToken(member);

    TokenReissueDto.Request request = TokenReissueDto.Request.builder()
        .refreshToken(tokenDto.getRefreshToken())
        .build();

    String url = "http://localhost:" + port + "/api/v1/members/reissue";

    // when
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        url,
        request,
        String.class
    );

    ApiResponse<TokenReissueDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        new TypeToken<ApiResponse<TokenReissueDto.Response>>(){}.getType()
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNotNull(apiResponse.getData());
    assertNotNull(apiResponse.getData().getAccessToken());
    assertNotNull(apiResponse.getData().getRefreshToken());
  }
//
  @Test
  void checkNickname_사용_가능_닉네임() {
    // given
    Member member = createMember();
    memberRepository.save(member);

    String nickname = "randomNickname";

    String url = "http://localhost:" + port + "/api/v1/members/check/nickname/" + nickname;

    // when
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }


  @Test
  void checkNickname_중복_닉네임() {
    // given
    Member member = Member.builder()
        .snsType(SnsType.GOOGLE)
        .snsId("snsId")
        .email("email")
        .nickname("randomNickname")
        .build();

    memberRepository.save(member);

    String nickname = member.getNickname();

    String url = "http://localhost:" + port + "/api/v1/members/check/nickname/" + nickname;

    BusinessException exception = new DuplicateNicknameException();
    // when
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );


    ApiResponse<TokenReissueDto.Response> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  void serveRandomNicknames_성공() {
    // given
    int count = 3;
    String url = "http://localhost:" + port + "/api/v1/members/serve/nickname?count=" + count;

    // when
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );

    ApiResponse<?> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertNotNull(apiResponse.getData());
  }

  @Test
  void serveRandomNicknames_추천_닉네임_생성_불가() {
    // given
    int count = 3;
    String url = "http://localhost:" + port + "/api/v1/members/serve/nickname?count=" + count;

    BusinessException exception = new NotExistsRemainNicknameException();
    // when
    when(nicknameProvider.provideRandomNicknames(count)).thenThrow(exception);

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );

    ApiResponse<?> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
  }


  @Test
  @DisplayName("count 가 1보다 작을 경우")
  void serveRandomNicknames_잘못된_count_1() {
    // given
    int count = 0;
    String url = "http://localhost:" + port + "/api/v1/members/serve/nickname?count=" + count;

    BusinessException exception = new NegativeNicknameCountException();
    // when
    when(nicknameProvider.provideRandomNicknames(count)).thenThrow(exception);

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );

    ApiResponse<?> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("count 가 생성 가능한 닉네임 보다 클 경우")
  void serveRandomNicknames_잘못된_count_2() {
    // given
    int count = 10000;
    String url = "http://localhost:" + port + "/api/v1/members/serve/nickname?count=" + count;

    BusinessException exception = new MaxNicknameCountExceededException();
    // when
    when(nicknameProvider.provideRandomNicknames(count)).thenThrow(exception);

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(
        url,
        String.class
    );

    ApiResponse<?> apiResponse = gson.fromJson(
        responseEntity.getBody(),
        ApiResponse.class
    );

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(apiResponse.getCode()).isEqualTo(exception.getCode());
    assertThat(apiResponse.getMessage()).isEqualTo(exception.getMessage());
  }
//
//  @Test
//  void promoteArtist() {
//  }

}
