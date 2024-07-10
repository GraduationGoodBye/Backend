package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.error.type.UnAuthErrorType;
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

    @GetMapping("/exception/{name}")
    public void exception(@PathVariable String name){
        switch (name) {
            case "BAD_REQUEST": throw new BusinessException();
            case "UNAUTHENTICATED": throw new UnAuthenticatedException();
            case "FORBIDDEN": throw new ForbiddenException();
            case "EXPIRED_TOKEN": throw new UnAuthenticatedException(UnAuthErrorType.EXPIRED_TOKEN);
            case "INVALID_TOKEN": throw new UnAuthenticatedException(UnAuthErrorType.INVALID_TOKEN);
            case "INTERNAL_SERVER_ERROR": throw new RuntimeException("INTERNAL_SERVER_ERROR");
            default: throw new RuntimeException("default");
        }
    }
}
