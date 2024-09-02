package com.ggb.graduationgoodbye.domain.member.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ggb.graduationgoodbye.domain.member.dto.SnsDto;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.exception.NotFoundMemberException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  private final SnsType[] snsTypes = SnsType.values();
  private final String NotFoundMemberMessage = "존재하지 않는 회원입니다.";
  private final Random random = new Random();

  public Member createMember(
      SnsType snsType,
      String snsId,
      String email,
      String address,
      String profile,
      int age,
      String gender,
      String nickname,
      String phone
  ) {
    Member member = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .email(email)
        .address(address)
        .profile(profile)
        .age(age)
        .gender(gender)
        .nickname(nickname)
        .phone(phone)
        .build();

    printMember(member);
    return member;
  }


  public Member createMember() {
    Member member = Member.builder()
        .snsType(randomSnsType())
        .snsId(randomSnsId())
        .email(randomEmail())
        .address(randomString())
        .profile(randomProfile())
        .age(randomAge())
        .gender(randomGender())
        .nickname(randomString())
        .phone(randomPhone())
        .build();

    printMember(member);
    return member;
  }

  private SnsType randomSnsType() {
    return snsTypes[random.nextInt(snsTypes.length)];
  }

  private String randomSnsId() {
    return UUID.randomUUID().toString();
  }

  private String randomEmail() {
    List<String> emails = Arrays.asList("gmail", "naver", "kakao");
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();
    int nicknameLength = random.nextInt(1,20);
    return uuidString.substring
        (random.nextInt(nicknameLength))
        + "@"
        + emails.get(random.nextInt(emails.size()))
        + ".com";
  }

  private String randomProfile() {
    return "https://www.ggb.com/profile/" + UUID.randomUUID();
  }

  private Integer randomAge() {
    return random.nextInt(1,100);
  }

  private String randomGender() {
    return random.nextBoolean() ? "남" : "여";
  }


  private String randomPhone() {
    return String.format("010-%04d-%04d", random.nextInt(10000), random.nextInt(10000));
  }

  private String randomString(){
    String address = new BigInteger(32, random).toString(32);

    int addressLength = address.length();

    return address.substring(0, random.nextInt(1,addressLength));
  }

  private void printMember(Member member){
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
    System.out.println("-------------");
  }

  @Test
  @DisplayName("save_올바른 값")
  void save() {
    // Given
    Member member = createMember();

    memberRepository.save(member);

    // When
    Member testMember = memberRepository.findById(member.getId())
        .orElseThrow(NotFoundMemberException::new);

    // Then
    assertEquals(member.getSnsType(), testMember.getSnsType());
    assertEquals(member.getEmail() , testMember.getEmail());
    assertEquals(member.getAddress() , testMember.getAddress());
    assertEquals(member.getProfile() , testMember.getProfile());
    assertEquals(member.getAge() , testMember.getAge());
    assertEquals(member.getGender() , testMember.getGender());
    assertEquals(member.getNickname() , testMember.getNickname());
    assertEquals(member.getPhone() , testMember.getPhone());
  }


  // member table sql 최신화 전까지 테스트 불가 ( DB 내부 Not Null 지정 필요 )
//  @Test
//  @DisplayName("save_snsType 미입력")
//  void save_null_snsType() {
//    Member member = createMember();
//    memberRepository.save(member);
//
//  }

  @Test
  @DisplayName("findById_올바른 값")
  void findById_success() {
    // Given
    Member testMember1 = createMember();
    memberRepository.save(testMember1);
    Member testMember2 = createMember();
    memberRepository.save(testMember2);

    // When
    Member findMember1 = memberRepository.findById(testMember1.getId())
        .orElseThrow(NotFoundMemberException::new);
    Member findMember2 = memberRepository.findById(testMember2.getId())
        .orElseThrow(NotFoundMemberException::new);

    // Then
    assertEquals(testMember1.getId(), findMember1.getId());
    assertEquals(testMember2.getId(), findMember2.getId());

  }

  @Test
  @DisplayName("findById_올바르지 않은 값")
  void findById_fail() {
    // Given
    Member testMember = createMember();
    memberRepository.save(testMember);

    // When
    Long id = -1L;
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

    Member memberTest = createMember(
        snsType,
        randomSnsId(),
        randomEmail(),
        randomString(),
        randomProfile(),
        randomAge(),
        randomGender(),
        randomString(),
        randomPhone()
    );

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

    List<SnsType> snsTypeList = new ArrayList<>(Arrays.asList(snsTypes));
    snsTypeList.remove(memberTest.getSnsType());

    int randomIdx = random.nextInt(snsTypeList.size());
    SnsType randomType = snsTypeList.get(randomIdx);

    String snsType = String.valueOf(randomType);


    SnsDto snsDto = new SnsDto(String.valueOf(snsType) , memberTest.getSnsId());

    // When
    NotFoundMemberException thrown = assertThrows(NotFoundMemberException.class, () -> {
      memberRepository.findBySns(snsDto).orElseThrow(NotFoundMemberException::new);
    });

    // Then
    assertEquals(NotFoundMemberMessage, thrown.getMessage());
  }

}
