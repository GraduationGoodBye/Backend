package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WithdrawProcessor {

  public Member processToWithdraw(Member member) {
    member.withdraw();
    return member;
  }
}
