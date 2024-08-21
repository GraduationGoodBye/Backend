package com.ggb.graduationgoodbye.domain.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("token")
@Getter
@NoArgsConstructor
public class Token {

  private Long id;
  private String memberId;
  private String refreshToken;

  @Builder
  public Token(String memberId, String refreshToken) {
    this.memberId = memberId;
    this.refreshToken = refreshToken;
  }

  public void updateRefreshToken(String reissuedRefreshToken) {
    this.refreshToken = reissuedRefreshToken;
  }
}
