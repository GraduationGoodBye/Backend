package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class CustomOauth2FailHandler implements AuthenticationFailureHandler {
    private final WriteResponseUtil writeResponseUtil;

    public CustomOauth2FailHandler(WriteResponseUtil writeResponseUtil) {
        this.writeResponseUtil = writeResponseUtil;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception instanceof NotJoinedUserException e) {
            writeResponseUtil.writeResponse(
                    response,
                    HttpStatus.UNAUTHORIZED.value(),
                    ApiResponse.error(
                            e.getCode(),
                            e.getMessage(),
                            e.getAccessToken()
                    )
            );
        }
        log.error("Custom Oauth2 Authentication Failed", exception);
    }
}
