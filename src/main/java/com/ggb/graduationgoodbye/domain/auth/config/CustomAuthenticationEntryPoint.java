package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final WriteResponseUtil writeResponseUtil;

    public CustomAuthenticationEntryPoint (WriteResponseUtil writeResponseUtil) {
        this.writeResponseUtil = writeResponseUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        if (authException instanceof NotJoinedUserException notJoinedUserException) {
            writeResponseUtil.writeResponse(
                    response,
                    HttpStatus.UNAUTHORIZED.value(),
                    ApiResponse.error(
                            notJoinedUserException.getCode(),
                            notJoinedUserException.getMessage(),
                            notJoinedUserException.getAccessToken()
                    )
            );
        } else {
            log.info("CustomAuthenticationEntryPoint commence");
        }
    }
}
