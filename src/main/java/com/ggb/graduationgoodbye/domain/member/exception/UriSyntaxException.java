package com.ggb.graduationgoodbye.domain.member.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class UriSyntaxException extends ServerException {

  private final String code;

  public UriSyntaxException() {
    super(MemberErrorType.URI_SYNTAX_ERROR.name());
    this.code = MemberErrorType.URI_SYNTAX_ERROR.getMessage();
  }

  public UriSyntaxException(final String message) {
    super(message);
    this.code = MemberErrorType.URI_SYNTAX_ERROR.name();
  }

}
