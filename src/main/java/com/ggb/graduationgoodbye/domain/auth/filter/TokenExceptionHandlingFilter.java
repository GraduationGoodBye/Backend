package com.ggb.graduationgoodbye.domain.auth.filter;

import static com.ggb.graduationgoodbye.domain.auth.utils.WriteResponseUtil.writeResponse;

import com.ggb.graduationgoodbye.global.error.exception.ForbiddenException;
import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class TokenExceptionHandlingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (UnAuthenticatedException e) {
      writeResponse(response, HttpStatus.UNAUTHORIZED.value(),
          ApiResponse.error(e.getCode(), e.getMessage()));
    } catch (ForbiddenException e) {
      writeResponse(response, HttpStatus.FORBIDDEN.value(),
          ApiResponse.error(e.getCode(), e.getMessage()));
    }
  }
}
