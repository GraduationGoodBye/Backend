package com.ggb.graduationgoodbye.domain.auth.service;

import com.ggb.graduationgoodbye.domain.auth.exception.NoTokenException;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import com.ggb.graduationgoodbye.domain.auth.vo.Token;
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
    public String reissueAccessToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            // NOTE : NoTokenException 생성
            throw new NoTokenException();
        }

        Token token = tokenRepository.findByAccessToken(accessToken);
        String refreshToken = token.getRefreshToken();

        tokenProvider.validateToken(refreshToken);

        String reissuedAccessToken = tokenProvider.createAccessToken(refreshToken);
        token.updateAccessToken(reissuedAccessToken);
        tokenRepository.update(token);
        return reissuedAccessToken;
    }

    public void validateToken(String accessToken) {
        tokenProvider.validateToken(accessToken);
    }

    public Authentication getAuthentication(String token) {
        return tokenProvider.getAuthentication(token);
    }
}
