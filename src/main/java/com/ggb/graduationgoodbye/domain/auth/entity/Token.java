package com.ggb.graduationgoodbye.domain.auth.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token {

  private Long id;
  private String userId;
  private String refreshToken;

  public static Token of(String userId, String refreshToken) {
    return Token.builder()
        .userId(userId)
        .refreshToken(refreshToken)
        .build();
  }

  public void updateRefreshToken(String reissuedRefreshToken) {
    this.refreshToken = reissuedRefreshToken;
  }
}
