package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserInfoProvider;
import com.ggb.graduationgoodbye.domain.auth.common.dto.OAuthUserInfoDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberJoinDto;
import com.ggb.graduationgoodbye.domain.member.common.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

  private final OAuthUserInfoProvider oauthUserInfoProvider;

  public OAuthUserInfoDto getOAuthUserInfo(String snsType, MemberLoginDto.Request request) {
    return oauthUserInfoProvider.provideOAuthUserInfoByAuthCode(snsType, request.getAuthCode());
  }

  public OAuthUserInfoDto getOAuthUserInfo(String snsType, MemberJoinDto.Request request) {
    return oauthUserInfoProvider.provideOAuthUserInfoByOAuthToken(snsType, request.getOauthToken());
  }
}
