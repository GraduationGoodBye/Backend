package com.ggb.graduationgoodbye.domain.member.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MemberUpdaterTest extends ServiceTest {

  @InjectMocks
  private MemberUpdater memberUpdater;
  @Mock
  private MemberRepository memberRepository;

  @Test
  void updateToWithdraw_성공() {
    Member member = Member.builder()
        .nickname("*")
        .build();

    memberUpdater.updateToWithdraw(member);

    verify(memberRepository, times(1)).withdraw(any(Member.class));
  }
}