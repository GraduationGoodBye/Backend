package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import static com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil.writeResponse;
import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Token token = tokenService.getToken(authentication);
        tokenService.save(token);

        writeResponse(
                response,
                HttpStatus.UNAUTHORIZED.value(),
                ApiResponse.ok(
                        token
                )
        );

        log.info("Successfully authenticated oauth2 token");
    }
}
