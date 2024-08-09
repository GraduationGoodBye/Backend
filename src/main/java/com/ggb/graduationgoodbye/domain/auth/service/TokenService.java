package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import com.ggb.graduationgoodbye.domain.member.controller.TokenReissueRequest;
import com.ggb.graduationgoodbye.domain.auth.exception.NotExistsTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.NotFoundTokenException;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenProvider tokenProvider;
  private final TokenRepository tokenRepository;

  // AccessToken 생성 & RefreshToken 생성 및 저장
  public TokenDto getToken(Authentication authentication) {
    String accessToken = tokenProvider.createAccessToken(authentication);
    String refreshToken = tokenProvider.createRefreshToken(authentication);

    saveOrUpdateToken(authentication.getName(), refreshToken);

    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  // AccessToken & RefreshToken 재발급
  public TokenDto reissueToken(TokenReissueRequest tokenReissueRequest) {

    String refreshToken = tokenReissueRequest.refreshToken();

    if (!StringUtils.hasText(refreshToken)) {
      throw new NotExistsTokenException();
    }

    tokenProvider.validateRefreshToken(refreshToken);

    Token token = tokenRepository.findToken(refreshToken);
    if (token == null) {
      throw new NotFoundTokenException();
    }

    String reissuedAccessToken = reissueAccessToken(refreshToken);
    String reissuedRefreshToken = reissueRefreshToken(reissuedAccessToken);

    return TokenDto.builder()
        .accessToken(reissuedAccessToken)
        .refreshToken(reissuedRefreshToken)
        .build();
  }

  private String reissueAccessToken(String refreshToken) {
    Authentication authentication = tokenProvider.getAuthenticationByRefreshToken(refreshToken);
    return tokenProvider.createAccessToken(authentication);
  }

  private String reissueRefreshToken(String accessToken) {
    Authentication authentication = tokenProvider.getAuthenticationByAccessToken(accessToken);
    String reissuedRefreshToken = tokenProvider.createRefreshToken(authentication);
    saveOrUpdateToken(authentication.getName(), reissuedRefreshToken);
    return reissuedRefreshToken;
  }

  private void saveOrUpdateToken(String userId, String refreshToken) {
    Token token = tokenRepository.findByUserId(userId);

    if (token == null) {
      token = Token.of(userId, refreshToken);
      tokenRepository.save(token);
    } else {
      token.updateRefreshToken(refreshToken);
      tokenRepository.update(token);
    }
  }

  // AccessToken 검증
  public void validateToken(String accessToken) {
    tokenProvider.validateAccessToken(accessToken);
  }

  //
  public Authentication getAuthenticationByAccessToken(String accessToken) {
    return tokenProvider.getAuthenticationByAccessToken(accessToken);
  }

  public String getTokenFromAuthorizationHeader(HttpServletRequest request) {
    return tokenProvider.getTokenFromAuthorizationHeader(request);
  }
}
