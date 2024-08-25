package com.ggb.graduationgoodbye.domain.auth.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import lombok.Getter;

@Getter
public class InvalidJwtSignatureException extends UnAuthenticatedException {

  private final String code;

  public InvalidJwtSignatureException() {
    super(AuthErrorType.INVALID_JWT_SIGNATURE.getMessage());
    this.code = AuthErrorType.INVALID_JWT_SIGNATURE.name();
  }

  public InvalidJwtSignatureException(final String message) {
    super(message);
    this.code = AuthErrorType.INVALID_JWT_SIGNATURE.name();
  }
}
