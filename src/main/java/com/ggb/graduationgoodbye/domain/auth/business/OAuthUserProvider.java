package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.PrincipalDetails;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

@Component
public class OAuthUserProvider {

  public PrincipalDetails getPrincipalDetails(OAuth2UserRequest userRequest, Map<String, Object> attr, Member member) {
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    return PrincipalDetails.builder()
        .member(member)
        .attributes(attr)
        .attributeKey(userNameAttributeName)
        .build();
  }
}
