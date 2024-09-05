package com.ggb.graduationgoodbye.domain.member.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class UriSyntaxException extends ServerException {

  private final String code;

  public UriSyntaxException() {
    super(MemberErrorType.URI_SYNTAX_ERROR.getMessage());
    this.code = MemberErrorType.URI_SYNTAX_ERROR.name();
  }

  public UriSyntaxException(final String message) {
    super(message);
    this.code = MemberErrorType.URI_SYNTAX_ERROR.name();
  }

}
