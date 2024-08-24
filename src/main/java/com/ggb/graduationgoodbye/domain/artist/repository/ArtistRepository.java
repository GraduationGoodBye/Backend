package com.ggb.graduationgoodbye.domain.artist.repository;

import com.ggb.graduationgoodbye.domain.artist.common.entity.Artist;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 작가 Repository.
 */
@Repository
@RequiredArgsConstructor
public class ArtistRepository {

  private final SqlSession mysql;

  @Transactional
  public Artist save(Artist artist) {
    mysql.insert("ArtistMapper.save", artist);
    return artist;
  }

  public boolean checkArtistDuplication(Long memberId) {
    return mysql.selectOne("ArtistMapper.checkArtistDuplication", memberId);
  }

}
