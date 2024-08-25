package com.ggb.graduationgoodbye.domain.auth.common.config;

import static com.ggb.graduationgoodbye.domain.auth.common.utils.WriteResponseUtil.writeResponse;

import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.service.TokenService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    TokenDto token = tokenService.getToken(authentication);

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
