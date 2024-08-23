package com.ggb.graduationgoodbye.domain.member.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggb.graduationgoodbye.domain.auth.common.exception.InvalidRegistrationIdException;
import com.ggb.graduationgoodbye.domain.member.dto.GoogleInfoDto;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2MemberInfo;
import com.ggb.graduationgoodbye.domain.member.exception.OAuth2FeignException;
import com.ggb.graduationgoodbye.domain.member.exception.UriSyntaxException;
import com.ggb.graduationgoodbye.domain.member.feign.OAuth2FeignClient;
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

  private final OAuth2FeignClient feignClient;

  private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
  private final String BEARER = "Bearer ";

  public OAuth2MemberInfo getInfo(String snsType, String accessToken) {

    return switch (snsType) {
      case "google" -> getGoogleInfo(accessToken);
      default -> throw new InvalidRegistrationIdException();
    };
  }

  private OAuth2MemberInfo getGoogleInfo(String accessToken) {

    String GOOGLE_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
    try {
      GoogleInfoDto googleInfo = feignClient.getInfo(
          new URI(GOOGLE_URI),
          BEARER + accessToken,
          CONTENT_TYPE);

      Map<String, Object> attributes = new ObjectMapper().convertValue(googleInfo,
          new TypeReference<HashMap<String, Object>>() {
          });

      return OAuth2MemberInfo.ofGoogle(attributes);
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      log.error(e.getMessage());
      throw new OAuth2FeignException();
    }
  }
}
