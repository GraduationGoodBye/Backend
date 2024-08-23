package com.ggb.graduationgoodbye.domain.auth.common.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class NotJoinedUserException extends AuthenticationException {

  private final String code;
  private final String accessToken;

  public NotJoinedUserException(final String accessToken) {
    this(AuthErrorType.NOT_JOINED_USER.getMessage(), accessToken);
  }

  public NotJoinedUserException(final String message, final String accessToken) {
    super(message);
    this.accessToken = accessToken;
    this.code = AuthErrorType.NOT_JOINED_USER.name();
  }
}
