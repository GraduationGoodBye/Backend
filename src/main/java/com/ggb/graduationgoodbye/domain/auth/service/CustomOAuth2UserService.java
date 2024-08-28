package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserParser;
import com.ggb.graduationgoodbye.domain.auth.business.OAuthUserProvider;
import com.ggb.graduationgoodbye.domain.auth.common.dto.PrincipalDetails;
import com.ggb.graduationgoodbye.domain.member.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.service.MemberReader;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final OAuthUserParser oAuthUserParser;
  private final MemberReader memberReader;
  private final OAuthUserProvider oAuthUserProvider;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    Map<String, Object> attr = super.loadUser(userRequest).getAttributes();
    SnsDto snsDto = oAuthUserParser.getSnsDto(userRequest, attr);

    String oauthToken = userRequest.getAccessToken().getTokenValue();
    Member member = memberReader.getMemberOrAuthException(snsDto, oauthToken);

    PrincipalDetails principalDetails = oAuthUserProvider.getPrincipalDetails(userRequest, attr,
        member);

    return principalDetails;
  }
}
