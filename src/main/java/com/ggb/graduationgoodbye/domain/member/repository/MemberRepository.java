package com.ggb.graduationgoodbye.domain.member.repository;

import com.ggb.graduationgoodbye.domain.member.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
    Member findById(Long id);
    void save(Member member);
    Member findByEmail(String email);
    boolean existsByEmail(String email);
}
