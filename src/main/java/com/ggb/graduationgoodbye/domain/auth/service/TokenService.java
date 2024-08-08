package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import com.ggb.graduationgoodbye.domain.member.controller.TokenReissueRequest;
import com.ggb.graduationgoodbye.domain.auth.exception.NotExistsTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.NotFoundTokenException;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenProvider tokenProvider;
  private final AuthProvider authProvider;
  private final TokenRepository tokenRepository;

  public TokenDto getToken(Authentication authentication) {
    String accessToken = tokenProvider.createAccessToken(authentication);
    String refreshToken = tokenProvider.createRefreshToken(authentication);
    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  // AccessToken & RefreshToken 재발급
  public TokenDto reissueAccessToken(TokenReissueRequest tokenReissueRequest) {

    String refreshToken = tokenReissueRequest.refreshToken();

    if (!StringUtils.hasText(refreshToken)) {
      throw new NotExistsTokenException();
    }

    tokenProvider.validateRefreshToken(refreshToken);

    Token token = tokenRepository.findToken(refreshToken);
    if (token == null) {
      throw new NotFoundTokenException();
    }

    String reissuedAccessToken = tokenProvider.createAccessToken(refreshToken);
    String reissuedRefreshToken = tokenProvider.createRefreshToken(reissuedAccessToken);

    return TokenDto.builder()
        .accessToken(reissuedAccessToken)
        .refreshToken(reissuedRefreshToken)
        .build();
  }

  public void validateToken(String accessToken) {
    tokenProvider.validateAccessToken(accessToken);
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = tokenProvider.getClaimsFromAccessToken(accessToken);
    return authProvider.getAuthentication(claims);
  }

  // Authorization 헤더에서 token 추출
  public String getTokenFromAuthorizationHeader(HttpServletRequest request) {
    return tokenProvider.getTokenFromAuthorizationHeader(request);
  }
}
