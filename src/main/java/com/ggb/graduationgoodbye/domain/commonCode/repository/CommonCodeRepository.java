package com.ggb.graduationgoodbye.domain.commonCode.repository;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


/**
 * 공통 코드 Repository.
 */
@Repository
@RequiredArgsConstructor
public class CommonCodeRepository {

  private final SqlSession mysql;

  /**
   * Type 별 공통 코드 찾기.
   */
  public List<CommonCode> findCommonCode(String type) {
    HashMap<String, String> map = new HashMap<>();
    map.put("type", type);
    return mysql.selectList("CommonCodeMapper.findCommonCode", map);
  }

  /**
   * 공통 코드 찾기.
   */
  public Optional<CommonCode> findCommonCode(String type, String name) {
    HashMap<String, String> map = new HashMap<>();
    map.put("type", type);
    map.put("name", name);
    return Optional.ofNullable(mysql.selectOne("CommonCodeMapper.findCommonCode", map));
  }

}
