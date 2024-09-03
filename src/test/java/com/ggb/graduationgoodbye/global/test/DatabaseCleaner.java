package com.ggb.graduationgoodbye.global.test;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

  private final SqlSession sqlSession;
  private List<String> tableNames;

  public DatabaseCleaner(final SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @PostConstruct
  public void init() {
//    this.tableNames = sqlSession.selectList("DatabaseCleaner.getTableNames");
  }

  //TODO: truncate 적용 필요
  @Transactional
  public void execute() {
//    // 외래 키 무결성 제약 조건을 임시로 비활성화
//    sqlSession.update("DatabaseCleaner.setForeignKeyChecks", 0);
//
//    for (String tableName : tableNames) {
//      sqlSession.update("DatabaseCleaner.truncateTable", tableName);
//    }
//
//    // 외래 키 무결성 제약 조건을 다시 활성화
//    sqlSession.update("DatabaseCleaner.setForeignKeyChecks", 1);
  }
}
