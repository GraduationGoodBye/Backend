package com.ggb.graduationgoodbye.domain.member.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class MaxNicknameCountExceededException extends BusinessException {

  private final String code;

  public MaxNicknameCountExceededException() {
    this(MemberErrorType.MAX_NICKNAME_COUNT_EXCEEDED.getMessage());
  }

  public MaxNicknameCountExceededException(final String message) {
    super(message);
    this.code = MemberErrorType.MAX_NICKNAME_COUNT_EXCEEDED.name();
  }

}
