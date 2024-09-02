package com.ggb.graduationgoodbye.domain.member.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserFeign;
import com.ggb.graduationgoodbye.domain.auth.common.dto.GoogleInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.exception.InvalidRegistrationIdException;
import com.ggb.graduationgoodbye.domain.member.common.exception.OAuth2FeignException;
import com.ggb.graduationgoodbye.domain.member.common.exception.UriSyntaxException;
import feign.FeignException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 멤버 InfoProvider.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberInfoProvider {

  private final OAuthUserFeign feignClient;

  private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
  private final String BEARER = "Bearer ";

  public OAuthUserInfoDto getInfo(String snsType, String accessToken) {

    return switch (snsType) {
      case "google" -> getGoogleInfo(accessToken);
      default -> throw new InvalidRegistrationIdException();
    };
  }

  private OAuthUserInfoDto getGoogleInfo(String accessToken) {

    String GOOGLE_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
    try {
      GoogleInfoDto googleInfo = feignClient.requestInfo(
          new URI(GOOGLE_URI),
          BEARER + accessToken,
          CONTENT_TYPE);

      Map<String, Object> attributes = new ObjectMapper().convertValue(googleInfo,
          new TypeReference<HashMap<String, Object>>() {
          });

      return OAuthUserInfoDto.ofGoogle(attributes);
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      log.error(e.getMessage());
      throw new OAuth2FeignException();
    }
  }
}
