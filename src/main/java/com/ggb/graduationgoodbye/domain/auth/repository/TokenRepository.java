package com.ggb.graduationgoodbye.domain.auth.repository;

import com.ggb.graduationgoodbye.domain.auth.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenRepository {

  void save(RefreshToken token);

  RefreshToken findToken(String refreshToken);

  void update(RefreshToken token);

  int delete(Long id);
}
