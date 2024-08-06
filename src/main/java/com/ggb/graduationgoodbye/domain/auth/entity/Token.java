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
    private String accessToken;
    private String refreshToken;

    public static Token of(String accessToken, String refreshToken) {
        return Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public void updateAccessToken(String reissuedAccessToken, String reissuedRefreshToken) {
        this.accessToken = reissuedAccessToken;
        this.refreshToken = reissuedRefreshToken;
    }
}
