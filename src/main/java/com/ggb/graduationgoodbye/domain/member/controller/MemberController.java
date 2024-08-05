package com.ggb.graduationgoodbye.domain.member.controller;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenReissueRequest;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import com.ggb.graduationgoodbye.domain.member.dto.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.exception.MemberNotFoundException;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
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
    public ApiResponse<Token> signup(@RequestBody MemberJoinRequest memberJoinRequest) {
        Token token = memberService.join(memberJoinRequest);
        return ApiResponse.ok(token);
    }

    @GetMapping("/info")
    public ApiResponse<Member> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();
        Long id = Long.valueOf(u.getUsername());
        Member member = memberService.findById(id).orElseThrow(MemberNotFoundException::new);
        return ApiResponse.ok(member);
    }

    @PostMapping("/reissue")
    public ApiResponse<Token> reissue(@RequestBody TokenReissueRequest tokenReissueRequest){
        Token token = tokenService.reissueAccessToken(tokenReissueRequest);
        return ApiResponse.ok(token);
    }
}
