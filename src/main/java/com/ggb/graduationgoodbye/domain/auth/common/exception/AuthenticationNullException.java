package com.ggb.graduationgoodbye.domain.auth.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class AuthenticationNullException extends ServerException {

  private final String code;

  public AuthenticationNullException() {
    this(AuthErrorType.AUTHENTICATION_NULL_ERROR.getMessage());
  }

  public AuthenticationNullException(String message) {
    super(message);
    this.code = AuthErrorType.AUTHENTICATION_NULL_ERROR.name();
  }
}
