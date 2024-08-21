package com.ggb.graduationgoodbye.domain.member.repository;

import com.ggb.graduationgoodbye.domain.member.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final SqlSession mysql;

  @Transactional
  public void save(Member member) {
    mysql.insert("MemberMapper.save", member);
  }

  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(mysql.selectOne("MemberMapper.findById", id));
  }

  public Optional<Member> findBySns(SnsDto snsDto) {
    return Optional.ofNullable(mysql.selectOne("MemberMapper.findBySns", snsDto));
  }
}
