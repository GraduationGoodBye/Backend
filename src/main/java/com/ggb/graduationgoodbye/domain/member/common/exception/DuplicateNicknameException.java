package com.ggb.graduationgoodbye.domain.member.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class DuplicateNicknameException extends BusinessException {

  private final String code;

  public DuplicateNicknameException() {
    super(MemberErrorType.DUPLICATE_NICKNAME.getMessage());
    this.code = MemberErrorType.DUPLICATE_NICKNAME.name();
  }

  public DuplicateNicknameException(final String message) {
    super(message);
    this.code = MemberErrorType.DUPLICATE_NICKNAME.name();
  }
}
