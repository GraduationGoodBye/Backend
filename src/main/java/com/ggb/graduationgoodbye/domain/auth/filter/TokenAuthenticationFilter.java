package com.ggb.graduationgoodbye.domain.auth.filter;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("TokenAuthenticationFilter >>>>>");
        // 1. 토큰 추출
        String accessToken = tokenService.getToken(request);

        // 2. 토큰 검증
        if (StringUtils.hasText(accessToken)) {
            tokenService.validateToken(accessToken);
            Authentication auth = tokenService.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request,response);
        log.info("TokenAuthenticationFilter <<<<<");
    }
}
