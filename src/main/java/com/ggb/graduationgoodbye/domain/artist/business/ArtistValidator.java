package com.ggb.graduationgoodbye.domain.artist.business;

import com.ggb.graduationgoodbye.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ArtistValidator.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArtistValidator {

  private final ArtistRepository artistRepository;

  public boolean checkArtistDuplication(Long memberId) {
    return artistRepository.checkArtistDuplication(memberId);
  }


}
