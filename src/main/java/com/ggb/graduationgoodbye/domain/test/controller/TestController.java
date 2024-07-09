package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.domain.auth.exception.ExpiredTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnauthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/check")
    public ApiResponse<String> check(){
        return ApiResponse.ok("This service is available");
    }

    @GetMapping("/exception?{name}")
    public void exception(@PathVariable String name){
        switch (name) {
            case "UNAUTHENTICATED": throw new UnauthenticatedException();
            case "FORBIDDEN": throw new ForbiddenException();
            case "BAD_REQUEST": throw new BusinessException();
            case "EXPIRED_TOKEN": throw new ExpiredTokenException();
            case "INVALID_TOKEN": throw new InvalidTokenException();
            case "INTERNAL_SERVER_ERROR": throw new RuntimeException("INTERNAL_SERVER_ERROR");
            default: throw new RuntimeException("default");
        }
    }
}
