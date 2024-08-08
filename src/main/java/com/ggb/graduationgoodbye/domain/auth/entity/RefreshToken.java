package com.ggb.graduationgoodbye.domain.auth.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

  private Long id;
  private String tokenValue;

  public static RefreshToken of(String refreshToken) {
    return RefreshToken.builder()
        .tokenValue(refreshToken)
        .build();
  }

  public void updateTokenValue(String reissuedRefreshToken) {
    this.tokenValue = reissuedRefreshToken;
  }
}
