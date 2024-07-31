package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.filter.TokenAuthenticationFilter;
import com.ggb.graduationgoodbye.domain.auth.filter.TokenExceptionHandlingFilter;
import com.ggb.graduationgoodbye.domain.auth.service.CustomOAuth2UserService;
import com.ggb.graduationgoodbye.domain.auth.service.TokenProvider;
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
    private final TokenProvider tokenProvider;
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
                                        new AntPathRequestMatcher("/api/v1/users/signup", "POST"),
                                        new AntPathRequestMatcher("/api/v1/users/login", "GET"),
                                        new AntPathRequestMatcher("/oauth2/authorize/**", "GET"),
                                        new AntPathRequestMatcher("/api/swagger"),
                                        new AntPathRequestMatcher("/api/swagger-config"),
                                        new AntPathRequestMatcher("/api/swagger-ui/**"),
                                        new AntPathRequestMatcher("/api-docs/**")
                                ).permitAll()
                                .anyRequest().authenticated()
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/oauth2/**").permitAll()
//                                .requestMatchers("/login/**").permitAll()
//                                .requestMatchers("/v1/admin/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers("/v1/users/**").hasRole(Role.USER.name())
//                                .anyRequest().authenticated()
                )
                /*
                AS-IS 비회원 사용자가 회원 API를 사용하면 구글 로그인을 시킨다

                note : TO-BE
                1. 비회원 사용자가 회원 API를 사용할 경우 로그인 페이지로 redirect - done
                2. 로그인/회원가입 API에 대해서는 Spring Security OAuth2를 이용해서 정보를 가져온다 -
                 - Auth Code / Google Access Token 가지고 사용자 조회 (loadUser)
                3. 이외 회원 인증/인가가 필요한 경우 JWT Filter validation 을 사용한다

                 */

                /*
                1. userInfoEndpoint - CustomOAuth2UserService >> OAuth 회원정보를 가져오는 용도 (비즈니스 로직 X)
                2. successHandler - CustomOAuth2SuccessHandler >> 이 부분에서 GGB DB 조회해서 Validation 체크 + throws Exception
                3. Spring Security FilterChain에서 Exception Handling하는 방법 (TokenExceptionHandlingFilter 대신 전역적으로 사용할 FilterExceptionHandler 을 만드는 건 어떨까?

                 */

                // OAuth2 설정
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(c -> c.baseUri("/oauth2/authorize"))
                        .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(new CustomOAuth2SuccessHandler(tokenProvider))
                )
                // 인증/인가 오류 시 핸들링(커스텀 시 사용)
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(writeResponseUtil))
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                // JWT 필터, 오류 핸들링 / 로깅 필터 추가
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider),UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터
                .addFilterBefore(new TokenExceptionHandlingFilter(writeResponseUtil), TokenAuthenticationFilter.class) // 오류 핸들링
                .addFilterBefore(new LogFilter(), TokenExceptionHandlingFilter.class) // 로깅 필터

        ;

        return http.build();
    }
}