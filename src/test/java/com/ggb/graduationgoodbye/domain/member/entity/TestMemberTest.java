package com.ggb.graduationgoodbye.domain.member.entity;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import org.junit.jupiter.api.Test;

public class TestMemberTest {

  @Test
  void print() {
    Member member = TestMember.testMember();
    System.out.println(TestMember.print(member));
  }
}
