package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 멤버 Create.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberCreate {

  private final MemberRepository memberRepository;

  public void save(Member member) {
    memberRepository.save(member);
  }

}
