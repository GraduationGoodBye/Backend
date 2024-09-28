package com.ggb.graduationgoodbye.domain.member.entity;

import com.ggb.graduationgoodbye.domain.member.common.entity.Member;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.global.util.randomValue.RandomValueGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestMember extends Member {

  private static final int SNS_ID_LENGTH = 255;
  private static final int EMAIL_LENGTH = 255;
  private static final int PROFILE_LENGTH = 255;
  private static final int NICKNAME_LENGTH = 255;
  private static final int ADDRESS_LENGTH = 255;
  private static final int GENDER_LENGTH = 1;
  private static final int AGE_RANGE = 120;
  private static final int PHONE_LENGTH = 13;

  public static Member testMember() {
    SnsType snsType = RandomValueGenerator.getRandomEnum(SnsType.class);
    String snsId = RandomValueGenerator.getRandomString(SNS_ID_LENGTH, "UTF-8");
    String email = RandomValueGenerator.getRandomString(EMAIL_LENGTH, "UTF-8");
    String profile = RandomValueGenerator.getRandomString(PROFILE_LENGTH, "UTF-8");
    String nickname = RandomValueGenerator.getRandomString(NICKNAME_LENGTH, "UTF-8");
    String address = RandomValueGenerator.getRandomString(ADDRESS_LENGTH, "UTF-8");
    String gender = RandomValueGenerator.getRandomString(GENDER_LENGTH, "UTF-8");
    int age = RandomValueGenerator.getRandomInt(AGE_RANGE);
    String phone = RandomValueGenerator.getRandomString(PHONE_LENGTH, "UTF-8");

    Member member = Member.builder()
        .snsType(snsType)
        .snsId(snsId)
        .email(email)
        .profile(profile)
        .nickname(nickname)
        .address(address)
        .gender(gender)
        .age(age)
        .phone(phone)
        .build();

    return member;
  }


  public static String print(Member member) {
    return "snsType = \"" + member.getSnsType() + "\" \n"
        + "snsId = \"" + member.getSnsId() + "\" \n"
        + "email = \"" + member.getEmail() + "\" \n"
        + "profile = \"" + member.getProfile() + "\" \n"
        + "nickname = \"" + member.getNickname() + "\" \n"
        + "address = \"" + member.getAddress() + "\" \n"
        + "gender = \"" + member.getGender() + "\" \n"
        + "age = " + member.getAge() + "\n"
        + "phone = \"" + member.getPhone() + "\" \n"
        + "createdId = " + member.getCreatedId() + "\n"
        + "createdAt = " + member.getCreatedAt() + "\n"
        + "updatedId = " + member.getUpdatedId() + "\n"
        + "updatedAt = " + member.getUpdatedAt() + "\n"
        + "deletedAt = " + member.getDeletedAt() + "\n"
        + "role = " + member.getRole() + "\n";
  }
}