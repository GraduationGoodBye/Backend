package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.dto.TokenReissueRequest;
import com.ggb.graduationgoodbye.domain.auth.exception.InvalidTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.NotExistsTokenException;
import com.ggb.graduationgoodbye.domain.auth.exception.NotFoundTokenException;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
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
    private final AuthProvider  authProvider;
    private final TokenRepository tokenRepository;

    public Token getToken(Authentication authentication) {
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);
        return Token.of(accessToken, refreshToken);
    }

    // Authorization 헤더에서 token 추출
    public String getToken(HttpServletRequest request) {
        return tokenProvider.getToken(request);
    }

    public void save(Token token) {
        tokenRepository.save(token);
    }

    // accessToken 재발급
    // NOTE : AccessToken create 시, 함께 생성된 RefreshToken 인지 여부를 확인함으로써, 보안 강화.
    public Token reissueAccessToken(TokenReissueRequest tokenReissueRequest) {

        String accessToken = tokenReissueRequest.accessToken();
        String refreshToken = tokenReissueRequest.refreshToken();

        if (!StringUtils.hasText(accessToken) || !StringUtils.hasText(refreshToken)) {
            throw new NotExistsTokenException();
        }

        Token token = tokenRepository.findByAccessTokenAndRefreshToken(accessToken, refreshToken);
        if (token == null) {
            throw new NotFoundTokenException();
        }

        String reissuedAccessToken = tokenProvider.createAccessToken(refreshToken);
        String reissuedRefreshToken = tokenProvider.createRefreshToken(reissuedAccessToken);
        token.updateAccessToken(reissuedAccessToken, reissuedRefreshToken);
        tokenRepository.update(token);
        return token;
    }

    public void validateToken(String accessToken) {
        tokenProvider.validateAccessToken(accessToken);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = tokenProvider.getClaimsFromAccessToken(accessToken);
        return authProvider.getAuthentication(claims);
    }
}
