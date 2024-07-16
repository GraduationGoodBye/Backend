package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.domain.auth.exception.ExpiredTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.test.service.TestService;
import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService){
        this.testService = testService;
    }

    @Operation(summary = "연결 확인")
    @GetMapping("/check")
    public ApiResponse<?> check(){
        return ApiResponse.ok("This service is available");
    }

    @GetMapping("/exception/{name}")
    public void exception(
            @Parameter(
                    in = ParameterIn.PATH,
                    schema = @Schema(type = "string", allowableValues = {"UNAUTHENTICATED", "FORBIDDEN", "BAD_REQUEST", "EXPIRED_TOKEN", "INVALID_TOKEN", "INTERNAL_SERVER_ERROR"})
            )
            @PathVariable String name){
        switch (name) {
            case "UNAUTHENTICATED": throw new UnAuthenticatedException();
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
