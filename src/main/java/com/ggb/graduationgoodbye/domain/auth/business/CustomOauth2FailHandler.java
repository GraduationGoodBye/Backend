package com.ggb.graduationgoodbye.domain.auth.business;

import static com.ggb.graduationgoodbye.domain.auth.common.utils.WriteResponseUtil.writeResponse;

import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthErrorType;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
public class CustomOauth2FailHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    writeResponse(
        response,
        HttpStatus.UNAUTHORIZED.value(),
        ApiResponse.error(
            AuthErrorType.NOT_JOINED_USER.name(),
            AuthErrorType.NOT_JOINED_USER.getMessage(),
            exception.getMessage() // oauthToken
        )
    );
  }
}
