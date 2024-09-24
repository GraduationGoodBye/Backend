package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthenticationNameNullException;
import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthenticationNullException;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotParsedValueException;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberProviderTest extends ServiceTest {

  @Spy
  private MemberProvider memberProvider;

  @Test
  void getCurrentMemberId_성공() {
    Long memberId = 123456L;
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(String.valueOf(memberId));
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    Long currentMemberId = memberProvider.getCurrentMemberId();

    assertEquals(memberId, currentMemberId);
  }

  @Test
  void getCurrentMemberId_실패_id_null() {
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(null);
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    assertThrows(AuthenticationNameNullException.class, memberProvider::getCurrentMemberId);
  }

  @Test
  void getCurrentMemberId_실패_id_빈값() {
    String memberId = "";
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(memberId);
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    assertThrows(AuthenticationNameNullException.class, memberProvider::getCurrentMemberId);
  }

  @Test
  void getCurrentMemberId_실패_id_공백() {
    String memberId = " ";
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(memberId);
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    assertThrows(AuthenticationNameNullException.class, memberProvider::getCurrentMemberId);
  }

  @Test
  void getCurrentMemberId_실패_id_숫자가아닌값() {
    String memberId = "memberId";
    Authentication authentication = Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(memberId);
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(context);

    assertThrows(NotParsedValueException.class, memberProvider::getCurrentMemberId);
  }

  @Test
  void getCurrentMemberId_실패_authentication_null() {
    SecurityContext context = Mockito.mock(SecurityContext.class);
    Mockito.when(context.getAuthentication()).thenReturn(null);
    SecurityContextHolder.setContext(context);

    assertThrows(AuthenticationNullException.class, memberProvider::getCurrentMemberId);
  }
}
