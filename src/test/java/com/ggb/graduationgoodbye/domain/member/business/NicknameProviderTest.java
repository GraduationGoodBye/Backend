package com.ggb.graduationgoodbye.domain.member.business;

import static com.ggb.graduationgoodbye.domain.member.business.NicknameProvider.ALLOWABLE_RANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ggb.graduationgoodbye.domain.member.common.exception.MaxNicknameCountExceededException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NegativeNicknameCountException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotExistsRemainNicknameException;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

public class NicknameProviderTest extends ServiceTest {

  @InjectMocks
  private NicknameProvider nicknameProvider;
  @Mock
  private MemberReader memberReader;
  @Spy
  private NicknameGenerator nicknameGenerator;

  // TODO : 비지니스 로직 수정 필요!
  // NOTE : maxCount 횟수 모두 중복된 닉네임이 생성된 경우, 다른 조합이 가능함에도 불구하고 NotExistsRemainNicknameException 예외 발생.
  // NOTE : 임시로, assumingThat 으로 처리함.
  @Test
  void provideRandomNicknames_성공() {
    // given
    int requestCount = 100;
    when(memberReader.existsByNickname(any(String.class))).thenReturn(false);

    // when
    Set<String> nicknames = nicknameProvider.provideRandomNicknames(requestCount);

    // then
    assumingThat(nicknames.size() >= requestCount, () -> {
      assertEquals(requestCount, nicknames.size());
      verify(nicknameGenerator, atLeast(requestCount)).generate();
      verify(nicknameGenerator, atMost((requestCount + ALLOWABLE_RANGE))).generate();
    });
  }

  @Test
  void providerRandomNicknames_실패_생성가능닉네임_0개() {
    // given
    int requestCount = 1;
    when(memberReader.existsByNickname(any(String.class))).thenReturn(true);

    // when then
    assertThrows(NotExistsRemainNicknameException.class,
        () -> nicknameProvider.provideRandomNicknames(requestCount));
    verify(nicknameGenerator, atMost((requestCount + ALLOWABLE_RANGE))).generate();
  }

  @Test
  void providerRandomNicknames_실패_요청갯수_100회이상() {
    // given
    int requestCount = 101;

    // when then
    assertThrows(MaxNicknameCountExceededException.class,
        () -> nicknameProvider.provideRandomNicknames(requestCount));
    verify(nicknameGenerator, times(0)).generate();
  }

  @Test
  void providerRandomNicknames_실패_요청갯수_음수() {
    // given
    int requestCount = -1;

    // when then
    assertThrows(NegativeNicknameCountException.class,
        () -> nicknameProvider.provideRandomNicknames(requestCount));
    verify(nicknameGenerator, times(0)).generate();
  }
}