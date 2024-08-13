package com.ggb.graduationgoodbye.domain.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  URI_SYNTAX_ERROR("URI 형식이 잘못되었습니다."),

  NOT_FOUND_MEMBER("존재하지 않는 회원입니다.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
