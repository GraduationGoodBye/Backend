package com.ggb.graduationgoodbye.domain.member.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ggb.graduationgoodbye.domain.member.common.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.NotFoundMemberException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
//@TestPropertySource(properties = "logging.config=classpath:logback-spring-test.xml")
@ActiveProfiles("test")
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  private final SnsType[] snsTypes = SnsType.values();
  private final String NotFoundMemberMessage = "존재하지 않는 회원입니다.";
  private final Random random = new Random();

  FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
      .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
      .plugin(new JakartaValidationPlugin())
      .build();


  public void validateDate(LocalDateTime a, LocalDateTime b){
    if (a != null && b != null) {
      LocalDateTime localDateTimeA = a.withNano(0);
      LocalDateTime localDateTimeB = b.withNano(0);
      assertEquals(localDateTimeA, localDateTimeB);
    }
  }
  public Member createMember() {
    Member member = fixtureMonkey.giveMeOne(Member.class);
    printMember(member);
    return member;
  }

  private void printMember(Member member) {
    System.out.println("-------------");
    System.out.println("SNS Type: " + member.getSnsType());
    System.out.println("SNS ID: " + member.getSnsId());
    System.out.println("Email: " + member.getEmail());
    System.out.println("Address: " + member.getAddress());
    System.out.println("Profile: " + member.getProfile());
    System.out.println("Age: " + member.getAge());
    System.out.println("Gender: " + member.getGender());
    System.out.println("Nickname: " + member.getNickname());
    System.out.println("Phone: " + member.getPhone());
    System.out.println("ROLE: " + member.getRole());
    System.out.println("CreatedAt: " + member.getCreatedAt());
    System.out.println("CreatedId: " + member.getCreatedId());
    System.out.println("UpdatedAt: " + member.getUpdatedAt());
    System.out.println("UpdatedId: " + member.getUpdatedId());
    System.out.println("DeletedAt: " + member.getDeletedAt());
    System.out.println("-------------");
  }

  @Test
  @DisplayName("save_올바른 값")
  void save() {
    // Given
    Member member = createMember();
    member.setDeletedAt(null);
    memberRepository.save(member);

    // When
    Member testMember = memberRepository.findById(member.getId())
        .orElseThrow(NotFoundMemberException::new);

    // Then
    assertEquals(member.getSnsType(), testMember.getSnsType());
    assertEquals(member.getEmail(), testMember.getEmail());
    assertEquals(member.getAddress(), testMember.getAddress());
    assertEquals(member.getProfile(), testMember.getProfile());
    assertEquals(member.getAge(), testMember.getAge());
    assertEquals(member.getGender(), testMember.getGender());
    assertEquals(member.getNickname(), testMember.getNickname());
    assertEquals(member.getPhone(), testMember.getPhone());
    validateDate(member.getCreatedAt(), testMember.getCreatedAt());
    assertEquals(member.getCreatedId() , testMember.getCreatedId());
    assertEquals(member.getUpdatedId() , testMember.getUpdatedId());
    validateDate(member.getUpdatedAt(), testMember.getUpdatedAt());

  }

//  @Test
//  @DisplayName("save_snsType 미입력")
//  void save_null_snsType() {
//    Member member = createMember();
//    member.setSnsType(null);
//
//    memberRepository.save(member);
//
//  }

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
    assertEquals(testMember.getId(), findMember1.getId());

  }

  @Test
  @DisplayName("findById_올바르지 않은 값")
  void findById_fail() {
    // Given
    Member testMember = createMember();
    memberRepository.save(testMember);

    // When
    Long testMemberId = testMember.getId();
    Long id =  testMemberId + random.nextInt(1 , 9999);

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
    Member memberTest = createMember();
    memberTest.setSnsType(snsType);

    memberRepository.save(memberTest);

    SnsDto snsDto = new SnsDto(memberTest.getSnsType().toString(), memberTest.getSnsId());

    // When
    Member testMember = memberRepository.findBySns(snsDto)
        .orElseThrow(NotFoundMemberException::new);

    // Then
    assertEquals(testMember.getId(), memberTest.getId());
    assertEquals(testMember.getSnsType(), snsType);

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
