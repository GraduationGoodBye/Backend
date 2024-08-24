package com.ggb.graduationgoodbye.domain.auth.business;

import com.ggb.graduationgoodbye.domain.auth.common.dto.PrincipalDetails;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.service.MemberReader;
import com.ggb.graduationgoodbye.domain.member.service.MemberService;
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

  private final MemberReader memberReader;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    Map<String, Object> attr = super.loadUser(userRequest).getAttributes();

    String email = attr.get("email").toString();

    Member member = memberReader.findByEmail(email).orElseThrow(() -> {
      String accessToken = userRequest.getAccessToken().getTokenValue();
      return new NotJoinedUserException(accessToken);
    });

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
