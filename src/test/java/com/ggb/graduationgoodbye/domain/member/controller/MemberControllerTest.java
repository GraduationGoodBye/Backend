package com.ggb.graduationgoodbye.domain.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthErrorType;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotSupportedSnsTypeException;
import com.ggb.graduationgoodbye.domain.auth.service.OAuthUserService;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.TokenReissueDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.MemberErrorType;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotSignUpException;
import com.ggb.graduationgoodbye.domain.member.common.exception.OAuth2FeignException;
import com.ggb.graduationgoodbye.domain.member.common.exception.UriSyntaxException;
import com.ggb.graduationgoodbye.domain.member.entity.TestMember;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.global.test.ControllerTest;
import com.ggb.graduationgoodbye.global.util.randomValue.RandomValueGenerator;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
@WithMockUser
public class MemberControllerTest extends ControllerTest {

  @MockBean
  private OAuthUserService oAuthUserService;
  @MockBean
  private MemberService memberService;
  @Autowired
  private MockMvc mockMvc;

  @Override
  protected Object initController() {
    return new MemberController(oAuthUserService, memberService, tokenService);
  }

  @Test
  void login_성공() throws Exception {
    // given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String authCode = RandomValueGenerator.getRandomString(40);
    String oauthToken = RandomValueGenerator.getRandomString(40);
    String accessToken = RandomValueGenerator.getRandomString(40);
    String refreshToken = RandomValueGenerator.getRandomString(40);

    Member mockMember = TestMember.testMember();
    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode(authCode)
        .build();
    OAuthUserInfoDto oAuthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId(mockMember.getSnsId())
        .oauthToken(oauthToken)
        .build();
    TokenDto token = TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class),
        any(MemberLoginDto.Request.class))).thenReturn(oAuthUserInfoDto);
    when(memberService.getMemberOrAuthException(any(String.class), any(String.class),
        any(String.class))).thenReturn(mockMember);
    when(tokenService.getToken(mockMember)).thenReturn(token);

    // when, then
    mvc.perform(post("/api/v1/members/login/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data.accessToken").value(token.getAccessToken()))
        .andExpect(jsonPath("$.data.refreshToken").value(token.getRefreshToken()));

    verify(oAuthUserService, times(1)).getOAuthUserInfo(any(String.class),
        any(MemberLoginDto.Request.class));
    verify(memberService, times(1)).getMemberOrAuthException(any(String.class),
        any(String.class), any(String.class));
    verify(tokenService, times(1)).getToken(any(Member.class));
  }

  @Test
  void login_실패_URI_문법오류() throws Exception {
    //given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String authCode = RandomValueGenerator.getRandomString(40);
    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode(authCode)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class), any(MemberLoginDto.Request.class)))
        .thenThrow(new UriSyntaxException());

    // when, then
    mockMvc.perform(post("/api/v1/members/login/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .with(csrf())
        )
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(MemberErrorType.URI_SYNTAX_ERROR.name()))
        .andExpect(jsonPath("$.message").value(MemberErrorType.URI_SYNTAX_ERROR.getMessage()));
  }

  @Test
  void login_실패_FeignClient_요청오류() throws Exception {
    // given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String authCode = RandomValueGenerator.getRandomString(40);
    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode(authCode)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class), any(MemberLoginDto.Request.class)))
        .thenThrow(new OAuth2FeignException());

    // when, then
    mockMvc.perform(post("/api/v1/members/login/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .with(csrf())
        )
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(MemberErrorType.OAUTH2_FEIGN_ERROR.name()))
        .andExpect(jsonPath("$.message").value(MemberErrorType.OAUTH2_FEIGN_ERROR.getMessage()));
  }

  @Test
  void login_실패_SnsType_미지원() throws Exception {
    // given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String authCode = RandomValueGenerator.getRandomString(40);
    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode(authCode)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class), any(MemberLoginDto.Request.class)))
        .thenThrow(new NotSupportedSnsTypeException());

    // when, then
    mockMvc.perform(post("/api/v1/members/login/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .with(csrf())
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(AuthErrorType.NOT_SUPPORTED_SNS_TYPE.name()))
        .andExpect(jsonPath("$.message").value(AuthErrorType.NOT_SUPPORTED_SNS_TYPE.getMessage()));
  }

  @Test
  void login_실패_미가입_회원() throws Exception {
    // given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String snsId = RandomValueGenerator.getRandomString(40);
    String oauthToken = RandomValueGenerator.getRandomString(40);
    String authCode = RandomValueGenerator.getRandomString(40);
    OAuthUserInfoDto oAuthUserInfoDto = OAuthUserInfoDto.builder()
        .snsId(snsId)
        .oauthToken(oauthToken)
        .build();
    MemberLoginDto.Request request = MemberLoginDto.Request.builder()
        .authCode(authCode)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class),
        any(MemberLoginDto.Request.class))).thenReturn(oAuthUserInfoDto);
    when(memberService.getMemberOrAuthException(any(String.class), any(String.class),
        any(String.class))).thenThrow(new NotSignUpException(oauthToken));

    // when, then
    mockMvc.perform(post("/api/v1/members/login/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .with(csrf())
        )
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(MemberErrorType.NOT_SIGNUP_MEMBER.name()))
        .andExpect(jsonPath("$.message").value(MemberErrorType.NOT_SIGNUP_MEMBER.getMessage()))
        .andExpect(jsonPath("$.data").value(oauthToken));
  }

  @Test
  void signup_성공() throws Exception {
    // given
    String snsType = RandomValueGenerator.getRandomEnum(SnsType.class).name();
    String oauthToken = RandomValueGenerator.getRandomString(40);
    String accessToken = RandomValueGenerator.getRandomString(40);
    String refreshToken = RandomValueGenerator.getRandomString(40);
    Member mockMember = TestMember.testMember();
    MemberJoinDto.Request request = MemberJoinDto.Request.builder()
        .oauthToken(oauthToken)
        .nickname(mockMember.getNickname())
        .address(mockMember.getAddress())
        .gender(mockMember.getGender())
        .age(mockMember.getAge())
        .phone(mockMember.getPhone())
        .build();
    OAuthUserInfoDto oAuthUserInfoDto = OAuthUserInfoDto.builder().build();
    TokenDto token = TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    when(oAuthUserService.getOAuthUserInfo(any(String.class),
        any(MemberJoinDto.Request.class))).thenReturn(oAuthUserInfoDto);
    when(memberService.join(any(String.class), any(OAuthUserInfoDto.class),
        any(MemberJoinDto.Request.class))).thenReturn(mockMember);
    when(tokenService.getToken(any(Member.class))).thenReturn(token);

    // when, then
    mvc.perform(post("/api/v1/members/signup/" + snsType)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data.accessToken").value(token.getAccessToken()))
        .andExpect(jsonPath("$.data.refreshToken").value(token.getRefreshToken()));

    verify(oAuthUserService, times(1)).getOAuthUserInfo(any(String.class),
        any(MemberJoinDto.Request.class));
    verify(memberService, times(1)).join(any(String.class),
        any(OAuthUserInfoDto.class), any(MemberJoinDto.Request.class));
    verify(tokenService, times(1)).getToken(any(Member.class));
  }

  @Test
  void withdraw_성공() throws Exception {
    // given
    String accessToken = "accessToken";
    String bearerToken = "Bearer " + accessToken;
    Authentication authentication = new UsernamePasswordAuthenticationToken("", "",
        Collections.singletonList(new SimpleGrantedAuthority(bearerToken)));

    when(tokenService.getTokenFromAuthorizationHeader(any(HttpServletRequest.class))).thenReturn(
        accessToken);
    when(tokenService.getAuthenticationByAccessToken(any(String.class))).thenReturn(authentication);

    // when, then
    mockMvc.perform(delete("/api/v1/members/withdraw")
            .header(HttpHeaders.AUTHORIZATION, bearerToken)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    verify(tokenService, times(1)).getTokenFromAuthorizationHeader(any(HttpServletRequest.class));
    verify(tokenService, times(1)).validateToken(any(String.class));
    verify(tokenService, times(1)).getAuthenticationByAccessToken(any(String.class));
    verify(memberService, times(1)).withdraw();
  }

  @Test
  void info_성공() throws Exception {
    // given
    Long id = RandomValueGenerator.getRandomLong(10);
    Member mockMember = TestMember.testMember();
    ReflectionTestUtils.setField(mockMember, "id", id);
    String accessToken = "accessToken";
    String bearerToken = "Bearer " + accessToken;
    Authentication authentication = new UsernamePasswordAuthenticationToken("", "",
        Collections.singletonList(new SimpleGrantedAuthority(bearerToken)));

    when(tokenService.getTokenFromAuthorizationHeader(any(HttpServletRequest.class))).thenReturn(
        accessToken);
    when(tokenService.getAuthenticationByAccessToken(any(String.class))).thenReturn(authentication);
    when(memberService.getInfo()).thenReturn(mockMember);

    // when, then
    mockMvc.perform(get("/api/v1/members/info")
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data.id").value(mockMember.getId()));

    verify(tokenService, times(1)).getTokenFromAuthorizationHeader(any(HttpServletRequest.class));
    verify(tokenService, times(1)).validateToken(any(String.class));
    verify(tokenService, times(1)).getAuthenticationByAccessToken(any(String.class));
    verify(memberService, times(1)).getInfo();
  }

  @Test
  void reissue_성공() throws Exception {
    // given
    String refreshToken = RandomValueGenerator.getRandomString(40);
    String accessToken = RandomValueGenerator.getRandomString(40);
    String newRefreshToken = RandomValueGenerator.getRandomString(40);
    TokenReissueDto.Request request = TokenReissueDto.Request.builder()
        .refreshToken(refreshToken)
        .build();
    TokenDto token = TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(newRefreshToken)
        .build();
    TokenReissueDto.Response response = TokenReissueDto.Response.builder()
        .accessToken(accessToken)
        .refreshToken(newRefreshToken)
        .build();

    when(tokenService.reissueToken(any(TokenReissueDto.Request.class))).thenReturn(token);

    // when, then
    mockMvc.perform(post("/api/v1/members/reissue")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request))
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data.accessToken").value(response.getAccessToken()))
        .andExpect(jsonPath("$.data.refreshToken").value(response.getRefreshToken()));

    verify(tokenService, times(1)).reissueToken(any(TokenReissueDto.Request.class));
  }

  @Test
  void checkNickname_성공() throws Exception {
    // given
    String nickname = "nick";

    // when, then
    mockMvc.perform(get("/api/v1/members/check/nickname/" + nickname)
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()));

    verify(memberService, times(1)).checkNicknameExists(any(String.class));
  }

  @Test
  void serveRandomNicknames_성공() throws Exception {
    //given
    int count = RandomValueGenerator.getRandomInt(100);
    Set<String> nicknames = new HashSet<>();
    when(memberService.serveRandomNicknames(anyInt())).thenReturn(nicknames);

    // when, then
    mockMvc.perform(get("/api/v1/members/serve/nickname")
            .param("count", String.valueOf(count))
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()))
        .andExpect(jsonPath("$.data").exists());

    verify(memberService, times(1)).serveRandomNicknames(anyInt());
  }
}
