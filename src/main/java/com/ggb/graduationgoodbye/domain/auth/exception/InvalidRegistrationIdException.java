package com.ggb.graduationgoodbye.domain.auth.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class InvalidRegistrationIdException extends ServerException {

  private final String code;

  public InvalidRegistrationIdException() {
    super(AuthErrorType.INVALID_REGISTRATION_ID.getMessage());
    this.code = AuthErrorType.INVALID_REGISTRATION_ID.name();
  }

  public InvalidRegistrationIdException(final String message) {
    super(message);
    this.code = AuthErrorType.INVALID_REGISTRATION_ID.name();
  }
}
