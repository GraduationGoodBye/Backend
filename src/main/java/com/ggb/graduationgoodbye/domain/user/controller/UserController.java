package com.ggb.graduationgoodbye.domain.user.controller;

import com.ggb.graduationgoodbye.domain.user.dto.UserRequestDto;
import com.ggb.graduationgoodbye.domain.user.service.UserService;
import com.ggb.graduationgoodbye.domain.user.vo.User;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody UserRequestDto signupForm) {
        Long signupUserId = userService.join(signupForm.dtoToVo());
        return ApiResponse.ok(signupUserId);
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
