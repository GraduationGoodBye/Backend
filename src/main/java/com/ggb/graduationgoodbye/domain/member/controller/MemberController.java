package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.OAuthUserService;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.TokenReissueDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 멤버 Controller.
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final OAuthUserService oAuthUserService;
  private final MemberService memberService;
  private final TokenService tokenService;

  /**
   * 로그인.
   */
  @PostMapping("/login/{snsType}")
  public void login(@PathVariable("snsType") String snsType,
      @Valid @RequestBody MemberLoginDto.Request request) {
    // authCode -> oauthToken -> oauthUserInfo
    OAuthUserInfoDto oAuthUserInfoDto = oAuthUserService.getOAuthUserInfo(snsType, request);
    log.info(oAuthUserInfoDto.toString());
    // OAuthUserInfoDto 으로 회원가입 여부 확인
    // token을 쿠키에 담아서 제공
  }

  /**
   * 회원 가입
   */
  @PostMapping("/signup/{snsType}")
  public ApiResponse<MemberJoinDto.Response> signup(@PathVariable String snsType,
      @Valid @RequestBody MemberJoinDto.Request request) {
    OAuthUserInfoDto oAuthUserInfoDto = oAuthUserService.getOAuthUserInfo(snsType, request);
    TokenDto token = memberService.join(snsType, oAuthUserInfoDto, request);
    MemberJoinDto.Response response = MemberJoinDto.Response.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * Member 정보 반환.
   */
  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/info")
  public ApiResponse<Member> info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User u = (User) authentication.getPrincipal();
    Long id = Long.valueOf(u.getUsername());
    Member member = memberService.getById(id);
    return ApiResponse.ok(member);
  }

  /**
   * Token 재발급.
   */
  @PostMapping("/reissue")
  public ApiResponse<TokenReissueDto.Response> reissue(
      @Valid @RequestBody TokenReissueDto.Request request) {
    TokenDto token = tokenService.reissueToken(request);
    TokenReissueDto.Response response = TokenReissueDto.Response.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * 회원 닉네임 중복 확인
   */
  @GetMapping("/check/nickname/{nickname}")
  public ApiResponse<?> checkNickname(@PathVariable String nickname) {
    memberService.checkNicknameExists(nickname);
    return ApiResponse.ok();
  }

  /**
   * 랜덤 닉네임 제공
   */
  @Hidden
  @GetMapping("/serve/nickname")
  public ApiResponse<?> serveRandomNicknames() {
    List<String> nicknames = memberService.serveRandomNicknames();
    return ApiResponse.ok(nicknames);
  }

  /**
   * 작가 등업 신청.
   */
  @PostMapping("promote-artist")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiResponse<PromoteArtistDto.Response> promoteArtist(
      @RequestPart("request") PromoteArtistDto.Request request,
      @RequestPart("certificate") MultipartFile certificate) {

    Artist artist = memberService.promoteArtist(request, certificate);

    PromoteArtistDto.Response response = new PromoteArtistDto.Response(artist);

    return ApiResponse.ok(response);
  }
}
