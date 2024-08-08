package com.ggb.graduationgoodbye.domain.auth.repository;

import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenRepository {

  void save(Token token);

  Token findByAccessTokenAndRefreshToken(String accessToken, String refreshToken);

  Token findByAccessToken(String accessToken);

  void update(Token token);

  int delete(Long id);
}
