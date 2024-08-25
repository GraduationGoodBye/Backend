package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.exception.DuplicateNicknameException;
import com.ggb.graduationgoodbye.domain.member.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final MemberService memberService;
  private final TokenService tokenService;

  /**
   * 회원가입
   */
  @PostMapping("/signup")
  public ApiResponse<MemberJoinDto.Response> signup(
      @Valid @RequestBody MemberJoinDto.Request request) {
    TokenDto token = memberService.join(request);
    MemberJoinDto.Response response = MemberJoinDto.Response.builder()
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken())
        .build();
    return ApiResponse.ok(response);
  }

  /**
   * 회원정보 조회
   */
  @GetMapping("/info")
  public ApiResponse<Member> info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User u = (User) authentication.getPrincipal();
    Long id = Long.valueOf(u.getUsername());
    Member member = memberService.findById(id).orElseThrow(NotFoundMemberException::new);
    return ApiResponse.ok(member);
  }

  /**
   * 액세스 토큰 재발급
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
    if (memberService.checkNicknameExists(nickname)) {
      throw new DuplicateNicknameException();
    }
    return ApiResponse.ok("사용 가능한 닉네임입니다.");
  }

  /**
   * 작가 등업 신청 요청.
   */
  @PostMapping("promote-artist")
  public ApiResponse<PromoteArtistDto.Response> promoteArtist(
      @RequestPart("request") PromoteArtistDto.Request request,
      @RequestPart("certificate") MultipartFile certificate) {

    Artist artist = memberService.promoteArtist(request, certificate);

    PromoteArtistDto.Response response = new PromoteArtistDto.Response(artist);

    return ApiResponse.ok(response);
  }
}
