package com.ggb.graduationgoodbye.domain.member.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class NotFoundMemberException extends BusinessException {

  private final String code;

  public NotFoundMemberException() {
    super(MemberErrorType.NOT_FOUND_MEMBER.getMessage());
    this.code = MemberErrorType.NOT_FOUND_MEMBER.name();
  }

  public NotFoundMemberException(final String message) {
    super(message);
    this.code = MemberErrorType.NOT_FOUND_MEMBER.name();
  }
}
