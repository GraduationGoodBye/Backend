package com.ggb.graduationgoodbye.domain.auth.common.config;

import com.ggb.graduationgoodbye.domain.auth.business.CustomOAuth2SuccessHandler;
import com.ggb.graduationgoodbye.domain.auth.business.CustomOauth2FailHandler;
import com.ggb.graduationgoodbye.domain.auth.service.CustomOAuth2UserService;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
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
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;
  private final TokenService tokenService;

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
            .frameOptions(
                HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // X-Frame-Options sameOrigin 제한
        .sessionManagement(c -> c
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화

        // OAuth2 설정
        .oauth2Login(oauth -> oauth
            .authorizationEndpoint(c -> c.baseUri("/oauth2/authorize"))
            .userInfoEndpoint(c -> c.userService(oAuth2UserService))
            .successHandler(new CustomOAuth2SuccessHandler(tokenService))
            .failureHandler(new CustomOauth2FailHandler())
        )

        // 로깅 필터 추가
        .addFilterBefore(new LogFilter(), SecurityContextHolderFilter.class)
    ;

    return http.build();
  }
}
