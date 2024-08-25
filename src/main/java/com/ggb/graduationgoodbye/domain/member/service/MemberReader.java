package com.ggb.graduationgoodbye.domain.member.service;

import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("MemberReaderA")
@RequiredArgsConstructor
public class MemberReader {

  private final MemberRepository memberRepository;

  public Optional<Member> findByNickname(String nickname) {
    return memberRepository.findByNickname(nickname);
  }
}

