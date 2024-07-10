package com.ggb.graduationgoodbye.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf.disable()
                )
                .headers(
                        headers -> headers.frameOptions(
                                options -> options.disable()
                        )
                )
                .authorizeHttpRequests(
                        request -> request
                                .anyRequest().permitAll()
//                                .requestMatchers("/").permitAll()
//                                .requestMatchers("/oauth2/**").permitAll()
//                                .requestMatchers("/login/**").permitAll()
//                                .requestMatchers("/v1/admin/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers("/v1/users/**").hasRole(Role.USER.name())
//                                .anyRequest().authenticated()
                )
//                .logout(
//                        logout -> logout.logoutSuccessUrl("/")
//                )
//                .oauth2Login(
//                        oauth -> oauth
//                                .loginPage("/login")
//                                .defaultSuccessUrl("/loginSuccess")
//                                .failureUrl("/loginFailure")
//                                .userInfoEndpoint(
//                                        endpoint -> endpoint.userService(customOAuth2UserService)
//                                )
//                                .successHandler(customAuthenticationSuccessHandler)
//                                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/loginFailure"))
//                )
        ;

        return http.build();
    }
}
