package com.GGB.graduationGoodbye.domain.test.controller;

import com.GGB.graduationGoodbye.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

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
