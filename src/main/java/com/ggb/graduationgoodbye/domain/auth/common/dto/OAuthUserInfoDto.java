package com.ggb.graduationgoodbye.domain.auth.common.dto;

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

  public static OAuthUserInfoDto ofGoogle(GoogleInfoDto googleInfoDto) {
    return OAuthUserInfoDto.builder()
        .snsId(googleInfoDto.sub())
        .email(googleInfoDto.email())
        .profile(googleInfoDto.picture())
        .build();
  }

  public OAuthUserInfoDto addOauthToken(String oauthToken) {
    this.oauthToken = oauthToken;
    return this;
  }
}
