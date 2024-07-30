package com.ggb.graduationgoodbye.domain.user.controller;

import com.ggb.graduationgoodbye.domain.user.dto.UserRequestDto;
import com.ggb.graduationgoodbye.domain.user.service.UserService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody UserRequestDto signupForm){
        Long signupUserId = userService.join(signupForm.dtoToVo());
        return ApiResponse.ok(signupUserId);
    }
}
