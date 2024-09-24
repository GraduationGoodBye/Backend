package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

public class WithdrawProcessorTest extends ServiceTest {

  @Spy
  private WithdrawProcessor withdrawProcessor;

  @Test
  void processToWithdraw_성공() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    String mockData = "12345";
    Member mockMember = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .email(mockData)
        .profile(mockData)
        .nickname(mockData)
        .build();

    withdrawProcessor.processToWithdraw(mockMember);

    assertEquals(snsType, mockMember.getSnsType());
    assertEquals(snsId, mockMember.getSnsId());
    assertNotEquals(mockData, mockMember.getEmail());
    assertNotEquals(mockData, mockMember.getProfile());
    assertNotEquals(mockData, mockMember.getNickname());
  }
}