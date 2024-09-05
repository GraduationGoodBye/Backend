package com.ggb.graduationgoodbye.domain.member.common.exception;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  DUPLICATE_NICKNAME("이미 사용중인 닉네임 입니다."),

  INVALID_SNS_TYPE("유효하지 않은 SNS 타입입니다."),

  OAUTH2_FEIGN_ERROR("OAuth2 리소스 서버와 통신을 실패했습니다."),

  URI_SYNTAX_ERROR("URI 형식이 잘못되었습니다."),

  NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),

  NOT_SIGNUP_MEMBER("가입되지 않은 회원입니다.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
