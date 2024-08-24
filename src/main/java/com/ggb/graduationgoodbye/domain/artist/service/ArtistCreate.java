package com.ggb.graduationgoodbye.domain.artist.service;

import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import com.ggb.graduationgoodbye.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ArtistCreate.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArtistCreate {

  private final ArtistRepository artistRepository;

  public Artist save(Artist artist) {
    return artistRepository.save(artist);
  }

}
