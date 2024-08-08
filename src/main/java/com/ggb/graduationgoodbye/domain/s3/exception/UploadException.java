package com.ggb.graduationgoodbye.domain.s3.exception;

import com.ggb.graduationgoodbye.global.error.exception.ServerException;
import lombok.Getter;

@Getter
public class UploadException extends ServerException {

  private final String code;

  public UploadException() {
    super(S3ErrorType.UPLOAD_FAIL.name());
    this.code = S3ErrorType.UPLOAD_FAIL.getMessage();
  }

  public UploadException(final String message) {
    super(message);
    this.code = S3ErrorType.UPLOAD_FAIL.name();
  }
}
