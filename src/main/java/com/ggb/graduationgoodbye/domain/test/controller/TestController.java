package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.domain.auth.exception.ExpiredTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.test.service.TestService;
import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnauthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping("/check")
    public ApiResponse<String> check(){
        return ApiResponse.ok("This service is available");
    }

    @GetMapping("/exception/{name}")
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

    @PostMapping("/image")
    public ApiResponse<String> image(@RequestPart(value = "file") MultipartFile file) throws IOException {
        String url = testService.uploadImageTest(file);
        return ApiResponse.ok(url);
    }
}
