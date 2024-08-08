package com.ggb.graduationgoodbye.global.error.exception;

import com.ggb.graduationgoodbye.global.error.type.ApiErrorType;
import lombok.Getter;

@Getter
public class UnAuthenticatedException extends RuntimeException {

  private final String code;

  public UnAuthenticatedException() {
    super(ApiErrorType.UNAUTHENTICATED.getMessage());
    this.code = ApiErrorType.UNAUTHENTICATED.name();
  }

  public UnAuthenticatedException(final String message) {
    super(message);
    this.code = ApiErrorType.UNAUTHENTICATED.name();
  }

  public UnAuthenticatedException(final String code, final String message) {
    super(message);
    this.code = code;
  }
}
