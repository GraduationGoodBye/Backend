package com.ggb.graduationgoodbye.domain.auth.common.config;

import static com.ggb.graduationgoodbye.domain.auth.common.utils.WriteResponseUtil.writeResponse;

import com.ggb.graduationgoodbye.domain.auth.common.exception.NotJoinedUserException;
import com.ggb.graduationgoodbye.domain.member.dto.OAuth2FailDto;
import com.ggb.graduationgoodbye.domain.member.utils.RandomNicknameUtil;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomOauth2FailHandler implements AuthenticationFailureHandler {

  private final RandomNicknameUtil randomNicknameUtil;

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
              getOAuth2FailDto(e.getAccessToken())
          )
      );
    }
    log.error("Custom Oauth2 Authentication Failed", exception);
  }

  private OAuth2FailDto getOAuth2FailDto(String accessToken) {
    List<String> nicknames = randomNicknameUtil.generateNicknames();
    return OAuth2FailDto.builder()
        .accessToken(accessToken)
        .nicknames(nicknames)
        .build();
  }
}
