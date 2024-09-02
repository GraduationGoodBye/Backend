package com.ggb.graduationgoodbye.domain.auth.common.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthUserInfoDto {

  private String snsId;
  private String email;
  private String profile;
  private String oauthToken;

  public static OAuthUserInfoDto ofGoogle(Map<String, Object> attributes) {
    return OAuthUserInfoDto.builder()
        .snsId(attributes.get("sub").toString())
        .email(attributes.get("email").toString())
        .profile(attributes.get("picture").toString())
        .build();
  }

  public OAuthUserInfoDto addOauthToken(String oauthToken) {
    this.oauthToken = oauthToken;
    return this;
  }
}
