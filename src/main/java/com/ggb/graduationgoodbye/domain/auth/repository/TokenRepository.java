package com.ggb.graduationgoodbye.domain.auth.repository;

import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenRepository {

  void save(Token token);

  Token findToken(String refreshToken);

  Token findByUserId(String userId);

  void update(Token token);

  int delete(Long id);
}
