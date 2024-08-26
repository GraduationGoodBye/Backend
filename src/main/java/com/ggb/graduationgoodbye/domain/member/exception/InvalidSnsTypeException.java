package com.ggb.graduationgoodbye.domain.member.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class InvalidSnsTypeException extends BusinessException {

  private final String code;

  public InvalidSnsTypeException() {
    super(MemberErrorType.INVALID_SNS_TYPE.getMessage());
    this.code = MemberErrorType.INVALID_SNS_TYPE.name();
  }

  public InvalidSnsTypeException(final String message) {
    super(message);
    this.code = MemberErrorType.INVALID_SNS_TYPE.name();
  }
}
