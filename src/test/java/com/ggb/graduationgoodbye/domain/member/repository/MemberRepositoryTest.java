package com.ggb.graduationgoodbye.domain.member.repository;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.global.test.MyBatisTest;
import com.ggb.graduationgoodbye.global.util.randomValue.RandomEntityPopulator;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;


@Import(MemberRepository.class)   
class MemberRepositoryTest extends MyBatisTest {

  @Autowired
  private MemberRepository memberRepository;

  private final SnsType[] snsTypes = SnsType.values();
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
    Member testMember = memberRepository.findById(member.getId()).orElseThrow(AssertionError::new);


    // Then
    assertThat(testMember)
        .usingRecursiveComparison()
        .isEqualTo(member);
  }

  @Test
  @DisplayName("findById_올바른 값")
  void findById_success() {
    // Given
    Member member = createMember();
    memberRepository.save(member);

    // When
    Member testMember = memberRepository.findById(member.getId()).orElseThrow(AssertionError::new);

    // Then
    assertThat(testMember)
        .usingRecursiveComparison()
        .isEqualTo(member);
  }

  @Test
  @DisplayName("findById_올바르지 않은 값")
  void findById_fail() {
    // Given
    Member member = createMember();
    memberRepository.save(member);

    // When
    Long testMemberId = member.getId();
    Long id = testMemberId + random.nextInt(1, 9999);

    Optional<Member> testMember = memberRepository.findById(id);

    // Then
    assertTrue(testMember.isEmpty());
  }


  @ParameterizedTest
  @EnumSource(SnsType.class)
  @DisplayName("findBySns_올바른 값")
  void findBySns_success(SnsType snsType) {

    // Given
    Member member = (Member) randomEntityPopulator
        .setValue("SnsType", snsType)
        .getPopulatedEntity(Member.class);

    memberRepository.save(member);

    String memberSnsType = member.getSnsType().toString();
    String memberSnsId = member.getSnsId();

    SnsDto snsDto = new SnsDto(memberSnsType, memberSnsId);

    // When
    Member testMember = memberRepository.findBySns(snsDto).orElseThrow(AssertionError::new);

    // Then
    assertThat(testMember)
        .usingRecursiveComparison()
        .isEqualTo(member);
  }


  @Test
  @DisplayName("findBySns_올바르지 않은 값")
  void findBySns_fail() {
    // Given
    Member member = createMember();
    memberRepository.save(member);

    String randomType = Arrays.stream(snsTypes)
        .filter(type -> !type.equals(member.getSnsType()))
        .map(SnsType::toString)
        .findAny()
        .orElseThrow();

    SnsDto snsDto = new SnsDto(randomType, member.getSnsId());

    // When
    Optional<Member> testMember = memberRepository.findBySns(snsDto);

    // Then
    assertTrue(testMember.isEmpty());
  }

}
