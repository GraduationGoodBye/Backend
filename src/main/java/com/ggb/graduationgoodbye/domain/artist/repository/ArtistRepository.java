package com.ggb.graduationgoodbye.domain.artist.repository;

import com.ggb.graduationgoodbye.domain.artist.entity.Artist;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ArtistRepository {
    private final SqlSession mysql;

    @Transactional
    public Artist save(Artist artist) {
        mysql.insert("ArtistMapper.save",artist);
        return artist;
    }
}
