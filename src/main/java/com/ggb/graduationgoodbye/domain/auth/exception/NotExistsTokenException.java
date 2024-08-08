package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import lombok.Getter;

@Getter
public class NotExistsTokenException extends UnAuthenticatedException {

  private final String code;

  public NotExistsTokenException() {
    super(AuthErrorType.NOT_EXISTS_TOKEN.getMessage());
    this.code = AuthErrorType.NOT_EXISTS_TOKEN.name();
  }

  public NotExistsTokenException(final String message) {
    super(message);
    this.code = AuthErrorType.NOT_EXISTS_TOKEN.name();
  }
}
