package com.ggb.graduationgoodbye.domain.auth.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class AuthenticationNameNullException extends ServerException {

  private final String code;

  public AuthenticationNameNullException() {
    this(AuthErrorType.AUTHENTICATION_NAME_NULL_ERROR.getMessage());
  }

  public AuthenticationNameNullException(String message) {
    super(message);
    this.code = AuthErrorType.AUTHENTICATION_NAME_NULL_ERROR.name();
  }
}
