package com.ggb.graduationgoodbye.domain.test.controller;

import com.ggb.graduationgoodbye.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping("/check")
    public ApiResult<String> check(){
        return ApiResult.success("This service is available");
    }

    @PostMapping("/connection")
    public ApiResult<String> connection(String msg){
        try{
            log.info(msg);
            return ApiResult.success(msg);
        }catch (Exception e){
            return null;
        }
    }
}
