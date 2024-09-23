package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

// NOTE : 어떻게 테스트 하지... 찾아보자!
public class MemberProviderTest extends ServiceTest {

  private final MemberProvider memberProvider = new MemberProvider();

  @Test
  void getCurrentMemberId_성공() {
    Long memberId = 1L;
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        memberId, null);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Long currentMemberId = memberProvider.getCurrentMemberId();

    SecurityContextHolder.clearContext();

    assertEquals(memberId, currentMemberId);
  }
}