package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.filter.TokenAuthenticationFilter;
import com.ggb.graduationgoodbye.domain.auth.filter.TokenExceptionHandlingFilter;
import com.ggb.graduationgoodbye.domain.auth.service.CustomOAuth2UserService;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil;
import com.ggb.graduationgoodbye.global.config.log.LogFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenService tokenService;
    private final WriteResponseUtil writeResponseUtil;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring() // Security ignore
                .requestMatchers("/h2-console/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .cors(AbstractHttpConfigurer::disable) // cors 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
                .headers(c -> c
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // X-Frame-Options sameOrigin 제한
                .sessionManagement(c -> c
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화

                // 요청에 따른 리소스 접근 설정
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        new AntPathRequestMatcher("/api/auth/**")
                                ).authenticated()
                                .anyRequest().permitAll()
                )

                // OAuth2 설정
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(c -> c.baseUri("/oauth2/authorize"))
                        .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(new CustomOAuth2SuccessHandler(tokenService, writeResponseUtil))
                        .failureHandler(new CustomOauth2FailHandler(writeResponseUtil))
                )

                // JWT 필터, 오류 핸들링 / 로깅 필터 추가
                .addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터
                .addFilterBefore(new TokenExceptionHandlingFilter(writeResponseUtil), TokenAuthenticationFilter.class) // 오류 핸들링
                .addFilterBefore(new LogFilter(), TokenExceptionHandlingFilter.class) // 로깅 필터
        ;

        return http.build();
    }
}
