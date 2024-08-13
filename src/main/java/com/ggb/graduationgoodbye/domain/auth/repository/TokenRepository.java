package com.ggb.graduationgoodbye.domain.auth.repository;

import com.ggb.graduationgoodbye.domain.auth.entity.Token;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

  private final SqlSession mysql;

  @Transactional
  public void save(Token token) {
    mysql.insert("TokenRepository.save", token);
  }

  @Transactional
  public void update(Token token) {
    mysql.update("TokenRepository.update", token);
  }

  public Optional<Token> findToken(String refreshToken) {
    return Optional.ofNullable(mysql.selectOne("TokenRepository.findToken", refreshToken));
  }

  public Optional<Token> findByUserId(String userId) {
    return Optional.ofNullable(mysql.selectOne("TokenRepository.findByUserId", userId));
  }
}
