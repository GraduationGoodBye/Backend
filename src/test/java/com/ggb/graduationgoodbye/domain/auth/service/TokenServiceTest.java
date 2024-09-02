package com.ggb.graduationgoodbye.domain.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ggb.graduationgoodbye.domain.auth.business.TokenProvider;
import com.ggb.graduationgoodbye.domain.auth.common.dto.TokenDto;
import com.ggb.graduationgoodbye.domain.auth.common.entity.Token;
import com.ggb.graduationgoodbye.domain.auth.repository.TokenRepository;
import com.ggb.graduationgoodbye.domain.member.common.enums.Role;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

class TokenServiceTest extends ServiceTest {

  @InjectMocks
  private TokenService tokenService;
  @Mock
  private TokenProvider tokenProvider;
  @Mock
  private TokenRepository tokenRepository;

  @Test
  @DisplayName("토큰 생성 시 신규 토큰이면 새로 저장한다.")
  void getToken() {
    //given
    TokenDto tokenDto = tokenDto();
    when(tokenProvider.createAccessToken(any(Authentication.class))).thenReturn(tokenDto.getAccessToken());
    when(tokenProvider.createRefreshToken(any(Authentication.class))).thenReturn(tokenDto.getRefreshToken());
    when(tokenRepository.findByUserId(any(String.class))).thenReturn(Optional.empty());
    doNothing().when(tokenRepository).save(any(Token.class));
    Authentication authentication = authentication();

    //when
    TokenDto expected = tokenService.getToken(authentication);

    //then
    assertNotNull(expected);
    assertEquals(expected.getAccessToken(), tokenDto.getAccessToken());
    assertEquals(expected.getRefreshToken(), tokenDto.getRefreshToken());
    verify(tokenRepository, times(1)).save(any(Token.class));
  }

  //TODO: Authentication 테스트용 static 메서드 추가 고려
  private Authentication authentication() {
    List<SimpleGrantedAuthority> authorities = getAuthorities();
    User principal = new User("TEST", "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
  }

  private List<SimpleGrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.name()));
  }

  //TODO: TokenDto 테스트용 static 메서드 추가 고려
  private TokenDto tokenDto() {
    return TokenDto.builder()
        .accessToken("accessToken1234")
        .refreshToken("refreshToken1234")
        .build();
  }

}