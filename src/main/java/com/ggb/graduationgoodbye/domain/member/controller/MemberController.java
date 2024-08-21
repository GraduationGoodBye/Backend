package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;
  private final TokenService tokenService;

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

  @GetMapping("/info")
  public ApiResponse<Member> info() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User u = (User) authentication.getPrincipal();
    Long id = Long.valueOf(u.getUsername());
    Member member = memberService.findById(id).orElseThrow(NotFoundMemberException::new);
    return ApiResponse.ok(member);
  }

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
}
