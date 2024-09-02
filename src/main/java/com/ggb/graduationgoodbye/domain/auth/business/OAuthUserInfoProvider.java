package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserTokenDto;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotSupportedSnsTypeException;
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

  public OAuthUserInfoDto provideOAuthUserInfoByAuthCode(String snsType, String authCode) {
    try {
      OAuthUserTokenDto oAuthUserTokenDto = requestToken(snsType, authCode);
      String oauthToken = oAuthUserTokenDto.accessToken();
      OAuthUserInfoDto oAuthUserInfoDto = requestUserInfo(snsType, oauthToken);
      return oAuthUserInfoDto.addOauthToken(oauthToken);
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      logErrorMessage(e);
      throw new OAuth2FeignException();
    }
  }

  public OAuthUserInfoDto provideOAuthUserInfoByOAuthToken(String snsType, String oauthToken) {
    try {
      return requestUserInfo(snsType, oauthToken);
    } catch (URISyntaxException e) {
      throw new UriSyntaxException();
    } catch (FeignException e) {
      logErrorMessage(e);
      throw new OAuth2FeignException();
    }
  }

  private void logErrorMessage(FeignException e) {
    log.error("\n url : {}, \n method : {}, \n content : {}", e.request().url(),
        e.request().httpMethod().name(), e.contentUTF8());
  }

  private OAuthUserTokenDto requestToken(String snsType, String authCode)
      throws URISyntaxException, FeignException {
    return switch (snsType) {
      case "google" -> googleUserProvider.getOAuthToken(authCode);
      case "kakao" -> null;
      case "naver" -> null;
      default -> throw new NotSupportedSnsTypeException();
    };

  }

  private OAuthUserInfoDto requestUserInfo(String snsType, String accessToken)
      throws URISyntaxException, FeignException {
    return switch (snsType) {
      case "google" -> googleUserProvider.getOAuthInfo(accessToken);
      case "kakao" -> null;
      case "naver" -> null;
      default -> throw new NotSupportedSnsTypeException();
    };
  }
}
