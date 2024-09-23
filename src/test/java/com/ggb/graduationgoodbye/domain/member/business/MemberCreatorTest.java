package com.ggb.graduationgoodbye.domain.member.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MemberCreatorTest extends ServiceTest {

  @InjectMocks
  private MemberCreator memberCreator;
  @Mock
  private MemberRepository memberRepository;

  @Test
  void save_성공() {
    Member member = Member.builder()
        .snsId("test1")
        .snsType(SnsType.GOOGLE)
        .email("test@email.com")
        .profile("이미지 링크")
        .nickname("nick")
        .build();

    memberCreator.save(member);

    // verify() : 모의 객체의 동작을 검증.
    // save() : memberRepository.save 메서드가 정확히 1번 호출되었는지 확인.
    // any() : 호출 시 인자로 Member 클래스의 어떤 인스턴스라도 받아들였는지 확인합니다.
    verify(memberRepository, times(1)).save(any(Member.class));
  }
}