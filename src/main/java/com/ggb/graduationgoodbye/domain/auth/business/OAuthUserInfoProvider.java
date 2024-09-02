package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserTokenDto;
import com.ggb.graduationgoodbye.domain.member.common.exception.OAuth2FeignException;
import com.ggb.graduationgoodbye.domain.member.common.exception.UriSyntaxException;
import feign.FeignException;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthUserInfoProvider {

  private final GoogleUserProvider googleUserProvider;

  public OAuthUserTokenDto requestOAuthToken(String snsType, String authCode) {
    try {
      return switch (snsType) {
        case "google" -> googleUserProvider.getOAuthToken(authCode);
        case "kakao" -> null;
        case "naver" -> null;
        default -> null;
      };
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      log.error(e.getMessage());
      throw new OAuth2FeignException();
    }
  }

  public OAuthUserInfoDto requestOAuthUserInfo(String snsType, String accessToken) {
    try {
      return switch (snsType) {
        case "google" -> googleUserProvider.getOAuthInfo(accessToken);
        case "kakao" -> null;
        case "naver" -> null;
        default -> null;
      };
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      log.error(e.getMessage());
      throw new OAuth2FeignException();
    }
  }
}
