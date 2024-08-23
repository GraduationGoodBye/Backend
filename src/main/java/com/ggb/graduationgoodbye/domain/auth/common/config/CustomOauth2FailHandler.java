package com.ggb.graduationgoodbye.domain.auth.common.config;

import static com.ggb.graduationgoodbye.domain.auth.common.utils.WriteResponseUtil.writeResponse;

import com.ggb.graduationgoodbye.domain.auth.common.exception.NotJoinedUserException;
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
    if (exception instanceof NotJoinedUserException e) {
      writeResponse(
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
