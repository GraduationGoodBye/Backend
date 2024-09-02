package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserInfoProvider;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserTokenDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

  private final OAuthUserInfoProvider oauthUserInfoProvider;

  public OAuthUserInfoDto getOAuthUserInfo(String snsType, MemberLoginDto.Request request) {
    OAuthUserTokenDto oauthTokenDto = oauthUserInfoProvider.requestOAuthToken(snsType,
        request.getAuthCode());
    String token = oauthTokenDto.accessToken();
    OAuthUserInfoDto oAuthUserInfoDto = oauthUserInfoProvider.requestOAuthUserInfo(snsType, token);
    return oAuthUserInfoDto.addOauthToken(token);
  }
}
