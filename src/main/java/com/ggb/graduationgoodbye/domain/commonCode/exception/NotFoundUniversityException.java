package com.ggb.graduationgoodbye.domain.commonCode.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;

/**
 * 특정 학교가 DB에 존재 하지 않았을 경우 오류.
 */
@Getter
public class NotFoundUniversityException extends BusinessException {

  private final String code;

  public NotFoundUniversityException() {
    super(CommonCodeErrorType.NOT_FOUND_UNIVERSITY.getMessage());
    this.code = CommonCodeErrorType.NOT_FOUND_UNIVERSITY.name();
  }

  /**
   * 특정 메세지가 필요할 경우 사용.
   */
  public NotFoundUniversityException(final String message) {
    super(message);
    this.code = CommonCodeErrorType.NOT_FOUND_UNIVERSITY.name();
    ;
  }
}
