package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final WriteResponseUtil writeResponseUtil;

    public CustomAuthenticationEntryPoint (WriteResponseUtil writeResponseUtil) {
        this.writeResponseUtil = writeResponseUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        if(authException instanceof NotJoinedUserException){
            writeResponseUtil.writeResponse(response, HttpStatus.BAD_REQUEST.value(),
                    ((NotJoinedUserException) authException).getCode(), authException.getMessage(),
                    ((NotJoinedUserException) authException).getAccessToken());
        }else{
            // asis : 프론트의 URL에 의존적이게 되어, 프론트의 변경에 백엔드 영향이 발생
            // todo : redirect 말고, 401 / 403 케이스에 맞춰서 http status 리턴해주기
            final String REDIRECT_URL = "http://localhost:8080/api/v1/users/login";
            response.sendRedirect(REDIRECT_URL);
        }
    }
}
