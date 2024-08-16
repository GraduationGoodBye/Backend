package com.ggb.graduationgoodbye.domain.auth.config;

import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException e)
      throws IOException, ServletException {
    log.error("Access denied : {}", e.getMessage());
    throw new ForbiddenException();
  }
}
