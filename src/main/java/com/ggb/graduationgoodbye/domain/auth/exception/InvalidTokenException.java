package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import java.io.Serial;
import lombok.Getter;

@Getter
public class InvalidTokenException extends UnAuthenticatedException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String code;

  public InvalidTokenException() {
    super(AuthErrorType.INVALID_TOKEN.getMessage());
    this.code = AuthErrorType.INVALID_TOKEN.name();
  }

  public InvalidTokenException(final String message) {
    super(message);
    this.code = AuthErrorType.INVALID_TOKEN.name();
  }
}
