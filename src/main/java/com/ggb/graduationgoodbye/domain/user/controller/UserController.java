package com.ggb.graduationgoodbye.domain.user.controller;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.domain.user.dto.UserJoinRequest;
import com.ggb.graduationgoodbye.domain.user.service.UserService;
import com.ggb.graduationgoodbye.domain.user.vo.User;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    public ApiResponse<Token> signup(@RequestBody UserJoinRequest userJoinRequest) {
        Token token = userService.join(userJoinRequest);
        return ApiResponse.ok(token);
    }

    @GetMapping("/info")
    public ApiResponse<User> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Long id = Long.valueOf(u.getUsername());
        User user = userService.findById(id).orElse(null);
        return ApiResponse.ok(user);
    }
}
