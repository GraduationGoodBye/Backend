package com.ggb.graduationgoodbye.domain.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  NOT_FOUND_MEMBER("존재하지 않는 회원입니다.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
