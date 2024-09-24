package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.DuplicateNicknameException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotSignUpException;
import com.ggb.graduationgoodbye.domain.member.entity.TestMember;
import com.ggb.graduationgoodbye.domain.member.repository.MemberRepository;
import com.ggb.graduationgoodbye.global.test.ServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MemberReaderTest extends ServiceTest {

  @InjectMocks
  private MemberReader memberReader;
  @Mock
  private MemberRepository memberRepository;

  @Test
  void getMemberOrAuthException_성공() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    Member mockMember = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .build();
    when(memberRepository.findBySns(any(SnsDto.class))).thenReturn(Optional.ofNullable(mockMember));

    Member member = memberReader.getMemberOrAuthException(snsType.name(), snsId, "");

    assertNotNull(member);
    assertEquals(snsId, member.getSnsId());
    assertEquals(snsType, member.getSnsType());
    verify(memberRepository, times(1)).findBySns(any(SnsDto.class));
  }

  @Test
  void getMemberOrAuthException_실패() {
    String oauthToken = "testOAuthToken";
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    when(memberRepository.findBySns(any(SnsDto.class))).thenReturn(Optional.empty());

    NotSignUpException e = assertThrows(NotSignUpException.class,
        () -> memberReader.getMemberOrAuthException(snsType.name(), snsId, oauthToken));

    assertEquals(oauthToken, e.getMessage());
    verify(memberRepository, times(1)).findBySns(any(SnsDto.class));
  }

  @Test
  void findBySns_성공() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    SnsDto snsDto = new SnsDto(snsType.name(), snsId);
    Member mockMember = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .build();
    when(memberRepository.findBySns(any(SnsDto.class))).thenReturn(Optional.ofNullable(mockMember));

    Member member = memberReader.findBySns(snsDto);

    assertNotNull(member);
    assertEquals(snsId, member.getSnsId());
    assertEquals(snsType, member.getSnsType());
    verify(memberRepository, times(1)).findBySns(any(SnsDto.class));
  }

  @Test
  void findBySns_실패() {
    SnsType snsType = SnsType.GOOGLE;
    String snsId = "testSnsId";
    SnsDto snsDto = new SnsDto(snsType.name(), snsId);
    when(memberRepository.findBySns(any(SnsDto.class))).thenReturn(Optional.empty());

    assertThrows(NotFoundMemberException.class, () -> memberReader.findBySns(snsDto));
    verify(memberRepository, times(1)).findBySns(any(SnsDto.class));
  }

  @Test
  void findById_성공() {
    Long id = 1L;
    TestMember mockMember = new TestMember();
    mockMember.setId(id);
    when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(mockMember));

    Member member = memberReader.findById(id);

    assertNotNull(member);
    assertEquals(mockMember.getId(), member.getId());
    verify(memberRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void findById_실패() {
    Long id = 1L;
    when(memberRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThrows(NotFoundMemberException.class, () -> memberReader.findById(id));
    verify(memberRepository, times(1)).findById(any(Long.class));
  }

  @Test
  void existsByNickname_성공() {
    String nickname = "nick";
    Member mockMember = Member.builder()
        .nickname(nickname)
        .build();
    when(memberRepository.findByNickname(any(String.class))).thenReturn(Optional.of(mockMember));

    assertTrue(memberReader.existsByNickname(nickname));
    verify(memberRepository, times(1)).findByNickname(any(String.class));
  }

  @Test
  void existsByNickname_실패() {
    String nickname = "nick";
    when(memberRepository.findByNickname(any(String.class))).thenReturn(Optional.empty());

    assertFalse(memberReader.existsByNickname(nickname));
    verify(memberRepository, times(1)).findByNickname(any(String.class));
  }

  @Test
  void checkNicknameExists_성공() {
    String nickname = "nick";
    when(memberRepository.findByNickname(any(String.class))).thenReturn(Optional.empty());

    memberReader.checkNicknameExists(nickname);

    verify(memberRepository, times(1)).findByNickname(any(String.class));
  }

  @Test
  void checkNicknameExists_실패() {
    String nickname = "nick";
    Member mockMember = Member.builder()
        .nickname(nickname)
        .build();
    when(memberRepository.findByNickname(any(String.class))).thenReturn(
        Optional.ofNullable(mockMember));

    assertThrows(DuplicateNicknameException.class,
        () -> memberReader.checkNicknameExists(nickname));
    verify(memberRepository, times(1)).findByNickname(any(String.class));
  }
}