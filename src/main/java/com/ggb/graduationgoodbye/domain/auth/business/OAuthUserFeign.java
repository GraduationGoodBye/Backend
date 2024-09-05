package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.GoogleInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserTokenDto;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth2")
public interface OAuthUserFeign {

  @PostMapping
  OAuthUserTokenDto requestOauthToken(URI baseUrl,
      @RequestParam("client_id") String clientId,
      @RequestParam("client_secret") String clientSecret,
      @RequestParam("code") String code,
      @RequestParam("grant_type") String grantType,
      @RequestParam("redirect_uri") String redirectUri);

  @GetMapping
  GoogleInfoDto requestInfo(URI baseUrl,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
      @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType);
}
