package com.ggb.graduationgoodbye.domain.auth.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class NotParsedValueException extends ServerException {

  private final String code;

  public NotParsedValueException() {
    this(AuthErrorType.NOT_PARSED_VALUE_ERROR.getMessage());
  }

  public NotParsedValueException(String message) {
    super(message);
    this.code = AuthErrorType.NOT_PARSED_VALUE_ERROR.name();
  }
}
