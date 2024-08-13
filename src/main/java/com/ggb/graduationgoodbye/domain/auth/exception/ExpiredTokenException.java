package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import java.io.Serial;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends UnAuthenticatedException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final String code;

  public ExpiredTokenException() {
    super(AuthErrorType.EXPIRED_TOKEN.getMessage());
    this.code = AuthErrorType.EXPIRED_TOKEN.name();
  }

  public ExpiredTokenException(final String message) {
    super(message);
    this.code = AuthErrorType.EXPIRED_TOKEN.name();
  }
}
