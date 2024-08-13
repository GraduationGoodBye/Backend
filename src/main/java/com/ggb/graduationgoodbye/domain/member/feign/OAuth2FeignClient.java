package com.ggb.graduationgoodbye.domain.member.feign;

import com.ggb.graduationgoodbye.domain.member.vo.GoogleInfoVo;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "oauth2")
public interface OAuth2FeignClient {

  @GetMapping
  GoogleInfoVo getInfo(URI baseUrl,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
      @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType);
}
