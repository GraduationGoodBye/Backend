package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.domain.auth.exception.NotExistsTokenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e)
      throws IOException, ServletException {
    if (!StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION))) {
      throw new NotExistsTokenException();
    }
    log.error("Unauthorized access : {}", e.getMessage());
    throw new UnAuthenticatedException();
  }
}
