package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.GoogleInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserTokenDto;
import feign.FeignException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoogleUserProvider {

  private final OAuthUserFeign oauthUserFeign;
  private final String GOOGLE_CLIENT_ID;
  private final String GOOGLE_CLIENT_SECRET;
  private final String GOOGLE_REDIRECT_URI;

  private static final String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
  private static final String GOOGLE_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
  private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
  private static final String BEARER = "Bearer ";
  private static final String GRANT_TYPE = "authorization_code";

  public GoogleUserProvider(
      OAuthUserFeign oauthUserFeign,
      @Value("${oauth2.client.registration.google.client-id}") String clientId,
      @Value("${oauth2.client.registration.google.client-secret}") String clientSecret,
      @Value("${oauth2.client.registration.google.redirect-uri}") String redirectUri
  ) {
    this.oauthUserFeign = oauthUserFeign;
    this.GOOGLE_CLIENT_ID = clientId;
    this.GOOGLE_CLIENT_SECRET = clientSecret;
    this.GOOGLE_REDIRECT_URI = redirectUri;
  }

  public OAuthUserTokenDto getOAuthToken(String authCode)
      throws URISyntaxException, FeignException {
    return oauthUserFeign.requestOauthToken(
        new URI(GOOGLE_TOKEN_URI),
        GOOGLE_CLIENT_ID,
        GOOGLE_CLIENT_SECRET,
        authCode,
        GRANT_TYPE,
        GOOGLE_REDIRECT_URI
    );
  }

  public OAuthUserInfoDto getOAuthInfo(String accessToken)
      throws URISyntaxException, FeignException {
    GoogleInfoDto googleInfoDto = oauthUserFeign.requestInfo(
        new URI(GOOGLE_INFO_URI),
        BEARER + accessToken,
        CONTENT_TYPE
    );
    OAuthUserInfoDto oAuthUserInfoDto = OAuthUserInfoDto.ofGoogle(googleInfoDto);
    log.info("googleInfo : {}", oAuthUserInfoDto.toString());
    return oAuthUserInfoDto;
  }
}
