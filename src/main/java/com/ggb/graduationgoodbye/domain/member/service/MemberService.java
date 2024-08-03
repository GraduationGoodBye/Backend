package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.auth.vo.Token;
import com.ggb.graduationgoodbye.domain.member.dto.MemberJoinRequest;
import com.ggb.graduationgoodbye.domain.member.vo.Member;

import java.util.Optional;

public interface MemberService {
    Token join(MemberJoinRequest request);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findById(Long id);
}
