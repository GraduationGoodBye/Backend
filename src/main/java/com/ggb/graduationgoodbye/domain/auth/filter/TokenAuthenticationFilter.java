package com.ggb.graduationgoodbye.domain.auth.filter;

import com.ggb.graduationgoodbye.domain.auth.service.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public TokenAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. 토큰 추출
        String accessToken = tokenProvider.getToken(request);

        // 2. 토큰 검증
        if(tokenProvider.validateToken(accessToken)){
            setAuthentication(accessToken);
        }
        else{
            String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);
            if(StringUtils.hasText(reissueAccessToken)){
                setAuthentication(reissueAccessToken);
                response.setHeader(HttpHeaders.AUTHORIZATION, reissueAccessToken);
            }
        }

        filterChain.doFilter(request,response);
    }

    private void setAuthentication(String accessToken){
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
