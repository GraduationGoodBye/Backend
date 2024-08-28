package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

@Component
public class OAuthUserParser {

  public SnsDto getSnsDto(OAuth2UserRequest userRequest, Map<String, Object> attr) {
    String snsType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
    String snsId = attr.get("sub").toString();
    return new SnsDto(snsType, snsId);
  }
}
