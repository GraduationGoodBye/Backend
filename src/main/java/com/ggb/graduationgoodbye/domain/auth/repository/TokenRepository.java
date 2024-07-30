package com.ggb.graduationgoodbye.domain.auth.repository;

import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenRepository {
    void save(Token token);
    Token findByAccessToken(String accessToken);
    void update(Token token);
    int delete(Long id);

}
