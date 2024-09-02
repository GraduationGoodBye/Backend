package com.ggb.graduationgoodbye.domain.auth.common.exception;

import lombok.Getter;

@Getter
public enum AuthErrorType {

  // Common
  NOT_JOINED_USER("가입되지 않은 회원입니다."),
  NOT_EXISTS_TOKEN("인증 토큰이 존재하지 않습니다."),

  // JWT
  EXPIRED_TOKEN("만료된 토큰입니다."),
  INVALID_TOKEN("유효하지 않은 토큰입니다."),
  INVALID_JWT_SIGNATURE("유효하지 않은 서명입니다."),
  NOT_FOUND_TOKEN("토큰정보를 찾을 수 없습니다."),

  // Google
  INVALID_REGISTRATION_ID("유효하지 않은 리소스 서버입니다."),
  NOT_SUPPORTED_SNS_TYPE("지원하지 않는 SNS 타입입니다.");

  private final String message;

  AuthErrorType(String message) {
    this.message = message;
  }
}
