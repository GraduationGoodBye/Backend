package com.ggb.graduationgoodbye.domain.artist.common.exception;

import com.ggb.graduationgoodbye.global.error.exception.BusinessException;
import lombok.Getter;


/**
 * 등록 되어있는 작가 정보가 존재할 경우 중복 처리.
 */
@Getter
public class DuplicationArtistException extends BusinessException {

  private final String code;

  public DuplicationArtistException() {
    super(ArtistErrorType.DUPLICATE_ARTIST.getMessage());
    this.code = ArtistErrorType.DUPLICATE_ARTIST.name();
  }

  /**
   * 특정 메세지가 필요할 경우 사용.
   */
  public DuplicationArtistException(final String message) {
    super(message);
    this.code = ArtistErrorType.DUPLICATE_ARTIST.name();
  }
}
