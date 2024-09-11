package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.OAuthUserService;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberInfoDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.PromoteArtistDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.TokenReissueDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestParam;
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
public class MemberController implements MemberApi {

  private final OAuthUserService oAuthUserService;
  private final MemberService memberService;
  private final TokenService tokenService;

  /**
   * 로그인.
   */
  @Override
  @PostMapping("/login/{snsType}")
  public ApiResponse<MemberLoginDto.Response> login(@PathVariable("snsType") String snsType,
      @Valid @RequestBody MemberLoginDto.Request request) {
    OAuthUserInfoDto oAuthUserInfoDto = oAuthUserService.getOAuthUserInfo(snsType, request);
    String snsId = oAuthUserInfoDto.getSnsId();
    String oauthToken = oAuthUserInfoDto.getOauthToken();
    Member member = memberService.getMemberOrAuthException(snsType, snsId, oauthToken);
    TokenDto token = tokenService.getToken(member);
    MemberLoginDto.Response response = MemberLoginDto.Response.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * 회원 가입
   */
  @Override
  @PostMapping("/signup/{snsType}")
  public ApiResponse<MemberJoinDto.Response> signup(@PathVariable String snsType,
      @Valid @RequestBody MemberJoinDto.Request request) {
    OAuthUserInfoDto oAuthUserInfoDto = oAuthUserService.getOAuthUserInfo(snsType, request);
    Member member = memberService.join(snsType, oAuthUserInfoDto, request);
    TokenDto token = tokenService.getToken(member);
    MemberJoinDto.Response response = MemberJoinDto.Response.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * Member 정보 반환.
   */
  @Override
  @PreAuthorize("hasAuthority('MEMBER')")
  @GetMapping("/info")
  public ApiResponse<MemberInfoDto.Response> info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User u = (User) authentication.getPrincipal();
    Long id = Long.valueOf(u.getUsername());
    Member member = memberService.getById(id);
    MemberInfoDto.Response response = MemberInfoDto.Response.builder()
        .id(member.getId())
        .snsType(member.getSnsType().name())
        .snsId(member.getSnsId())
        .email(member.getEmail())
        .profile(member.getProfile())
        .nickname(member.getNickname())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * Token 재발급.
   */
  @Override
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
  @Override
  @GetMapping("/check/nickname/{nickname}")
  public ApiResponse<?> checkNickname(@PathVariable String nickname) {
    memberService.checkNicknameExists(nickname);
    return ApiResponse.ok();
  }

  /**
   * 랜덤 닉네임 제공
   */
  @Override
  @GetMapping("/serve/nickname")
  public ApiResponse<?> serveRandomNicknames(@RequestParam int count) {
    Set<String> nicknames = memberService.serveRandomNicknames(count);
    return ApiResponse.ok(nicknames);
  }

  /**
   * 작가 등업 신청.
   */
  @Override
  @PostMapping("promote-artist")
  @PreAuthorize("hasAuthority('MEMBER')")
  public ApiResponse<PromoteArtistDto.Response> promoteArtist(
      @RequestPart("certificate") MultipartFile certificate,
      @RequestPart("request") PromoteArtistDto.Request request) {
    Artist artist = memberService.promoteArtist(request, certificate);
    PromoteArtistDto.Response response = new PromoteArtistDto.Response(artist);
    return ApiResponse.ok(response);
  }
}
