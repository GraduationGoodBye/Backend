package com.ggb.graduationgoodbye.domain.member.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.UnAuthenticatedException;
import lombok.Getter;

@Getter
public class NotSignUpException extends UnAuthenticatedException {

  private final String code;

  public NotSignUpException() {
    this(MemberErrorType.NOT_SIGNUP_MEMBER.getMessage());
  }

  public NotSignUpException(final String message) {
    super(message);
    this.code = MemberErrorType.NOT_SIGNUP_MEMBER.name();
  }
}
