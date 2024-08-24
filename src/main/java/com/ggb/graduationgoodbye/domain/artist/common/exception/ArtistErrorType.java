package com.ggb.graduationgoodbye.domain.artist.common.exception;

import lombok.Getter;

/**
 * 콩동 코드 에러 코드.
 */
@Getter
public enum ArtistErrorType {

  DUPLICATE_ARTIST("등록 되어 있는 작가 회원 정보가 존재합니다.");

  private final String message;

  ArtistErrorType(String message) {
    this.message = message;
  }
}
