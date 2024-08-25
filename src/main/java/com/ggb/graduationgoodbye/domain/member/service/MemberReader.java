package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 멤버 Reader.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberReader {

  private final MemberRepository memberRepository;

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);
  }

  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
  }

  public boolean existsByEmail(String email) {
    return memberRepository.findByEmail(email).isPresent();
  }

}
