package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import java.util.Optional;
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

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }

  public Optional<Member> findById(Long id) {
    return memberRepository.findById(id);
  }

  public boolean existsByEmail(String email) {
    return memberRepository.findByEmail(email).isPresent();
  }

}
