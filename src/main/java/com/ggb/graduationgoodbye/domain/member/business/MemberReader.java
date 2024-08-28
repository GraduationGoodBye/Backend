package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.exception.DuplicateNicknameException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 멤버 Reader.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MemberReader {

  private final MemberRepository memberRepository;

  public Member getMemberOrAuthException(SnsDto dto, String oauthToken) {
    return memberRepository.findBySns(dto)
        .orElseThrow(() -> new AuthenticationException(oauthToken) {
        });
  }

  public Member findBySns(SnsDto dto) {
    return memberRepository.findBySns(dto).orElseThrow(NotFoundMemberException::new);
  }

  public Member findById(Long id) {
    return memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
  }

  public boolean existsByEmail(SnsDto dto) {
    return memberRepository.findBySns(dto).isPresent();
  }

  public boolean existsByNickname(String nickname) {
    return memberRepository.findByNickname(nickname).isPresent();
  }

  public void checkNicknameExists(String nickname) {
    memberRepository.findByNickname(nickname)
        .ifPresent(e -> {
          throw new DuplicateNicknameException();
        });
  }
}
