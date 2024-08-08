package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.member.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;
  private final TokenService tokenService;

  @PostMapping("/signup")
  public ApiResponse<TokenResponse> signup(@RequestBody MemberJoinRequest memberJoinRequest) {
    TokenDto token = memberService.join(memberJoinRequest);
    TokenResponse tokenResponse = new TokenResponse(token.getAccessToken(),
        token.getRefreshToken());
    return ApiResponse.ok(tokenResponse);
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
  public ApiResponse<TokenResponse> reissue(@RequestBody TokenReissueRequest tokenReissueRequest) {
    TokenDto token = tokenService.reissueAccessToken(tokenReissueRequest);
    TokenResponse tokenResponse = new TokenResponse(token.getAccessToken(),
        token.getRefreshToken());
    return ApiResponse.ok(tokenResponse);
  }
}
