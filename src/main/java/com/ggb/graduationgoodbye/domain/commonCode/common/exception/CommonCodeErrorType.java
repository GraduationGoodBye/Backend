package com.ggb.graduationgoodbye.domain.commonCode.common.exception;

import lombok.Getter;

/**
 * 콩동 코드 에러 코드.
 */
@Getter
public enum CommonCodeErrorType {

  NOT_FOUND_UNIVERSITY("존재하지 않는 대학교입니다."),
  NOT_FOUND_MAJOR("존재하지 않는 학과입니다.");

  private final String message;

  CommonCodeErrorType(String message) {
    this.message = message;
  }
}
