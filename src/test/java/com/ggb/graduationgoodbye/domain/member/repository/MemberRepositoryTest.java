package com.ggb.graduationgoodbye.domain.member.repository;


import static com.ggb.graduationgoodbye.global.util.CustomAssertions.customAssertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotFoundMemberException;
import com.ggb.graduationgoodbye.global.test.IntegrationTest;
import com.ggb.graduationgoodbye.global.util.randomValue.RandomEntityPopulator;
import java.util.Arrays;
import java.util.Random;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
class MemberRepositoryTest extends IntegrationTest {

  @Autowired
  private MemberRepository memberRepository;

  private final SnsType[] snsTypes = SnsType.values();
  private final String NotFoundMemberMessage = "존재하지 않는 회원입니다.";
  private final Random random = new Random();
  private final RandomEntityPopulator randomEntityPopulator;

  public MemberRepositoryTest(@Autowired SqlSessionFactory sqlSessionFactory) {
    this.randomEntityPopulator = new RandomEntityPopulator(sqlSessionFactory, "MEMBERS");
  }

  @BeforeEach
  public void setUp() {
    randomEntityPopulator.setValue("deletedAt",null);
  }

  public Member createMember() {
    return (Member) randomEntityPopulator
        .getPopulatedEntity(Member.class);
  }
  @Test()
  @DisplayName("save_올바른 값")
  void save() {
    // Given
    Member member = createMember();
    memberRepository.save(member);

    // When
    Member testMember = memberRepository.findById(member.getId())
        .orElseThrow(NotFoundMemberException::new);

    // Then
    customAssertEquals(member, testMember);

  }

  @Test
  @DisplayName("findById_올바른 값")
  void findById_success() {
    // Given
    Member testMember = createMember();
    memberRepository.save(testMember);

    // When
    Member findMember1 = memberRepository.findById(testMember.getId())
        .orElseThrow(NotFoundMemberException::new);

    // Then
    customAssertEquals(testMember , findMember1);

  }

  @Test
  @DisplayName("findById_올바르지 않은 값")
  void findById_fail() {
    // Given
    Member testMember = createMember();
    memberRepository.save(testMember);

    // When
    Long testMemberId = testMember.getId();
    Long id = testMemberId + random.nextInt(1, 9999);

    NotFoundMemberException thrown = assertThrows(NotFoundMemberException.class, () -> {
      memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
    });

    // Then
    assertEquals(NotFoundMemberMessage, thrown.getMessage());

  }


  @ParameterizedTest
  @EnumSource(SnsType.class)
  @DisplayName("findBySns_올바른 값")
  void findBySns_success(SnsType snsType) {

    // Given
    Member memberTest = (Member) randomEntityPopulator
        .setValue("SnsType", snsType)
        .getPopulatedEntity(Member.class);

    memberRepository.save(memberTest);

    SnsDto snsDto = new SnsDto(memberTest.getSnsType().toString(), memberTest.getSnsId());

    // When
    Member testMember = memberRepository.findBySns(snsDto)
        .orElseThrow(NotFoundMemberException::new);

    // Then
    customAssertEquals(testMember , memberTest);

  }


  @Test
  @DisplayName("findBySns_올바르지 않은 값")
  void findBySns_fail() {
    // Given
    Member memberTest = createMember();
    memberRepository.save(memberTest);

    String randomType = Arrays.stream(snsTypes)
        .filter(type -> !type.equals(memberTest.getSnsType()))
        .map(SnsType::toString)
        .findAny()
        .orElseThrow();

    SnsDto snsDto = new SnsDto(randomType, memberTest.getSnsId());

    // When
    NotFoundMemberException thrown = assertThrows(NotFoundMemberException.class, () -> {
      memberRepository.findBySns(snsDto).orElseThrow(NotFoundMemberException::new);
    });

    // Then
    assertEquals(NotFoundMemberMessage, thrown.getMessage());
  }

}
