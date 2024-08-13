package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.member.controller.TokenReissueRequest;
import com.ggb.graduationgoodbye.domain.auth.exception.NotFoundTokenException;
import com.ggb.graduationgoodbye.domain.auth.mapper.TokenMapper;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenProvider tokenProvider;
  private final TokenMapper tokenMapper;

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
      throw new InvalidTokenException();
    }

    tokenProvider.validateRefreshToken(refreshToken);

    tokenMapper.findToken(refreshToken).orElseThrow(NotFoundTokenException::new);

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
    Optional<Token> optionalToken = tokenMapper.findByUserId(userId);
    if (optionalToken.isPresent()) {
      Token token = optionalToken.get();
      token.updateRefreshToken(refreshToken);
      tokenMapper.update(token);
    } else {
      Token token = Token.builder()
          .userId(userId)
          .refreshToken(refreshToken)
          .build();
      tokenMapper.save(token);
    }
  }

  // AccessToken 검증
  public void validateToken(String accessToken) {
    tokenProvider.validateAccessToken(accessToken);
  }

  // Authentication 생성
  public Authentication getAuthenticationByAccessToken(String accessToken) {
    return tokenProvider.getAuthenticationByAccessToken(accessToken);
  }

  public Authentication getAuthenticationByMember(Member member) {
    return tokenProvider.getAuthenticationByMember(member);
  }

  // Header 에서 Token 추출
  public String getTokenFromAuthorizationHeader(HttpServletRequest request) {
    return tokenProvider.getTokenFromAuthorizationHeader(request);
  }
}
