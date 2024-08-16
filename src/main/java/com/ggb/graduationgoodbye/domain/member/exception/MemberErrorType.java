package com.ggb.graduationgoodbye.domain.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  OAUTH2_FEIGN_ERROR("OAuth2 리소스 서버와 통신을 실패했습니다."),

  URI_SYNTAX_ERROR("URI 형식이 잘못되었습니다."),

  NOT_FOUND_MEMBER("존재하지 않는 회원입니다.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
