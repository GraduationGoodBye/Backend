package com.ggb.graduationgoodbye.domain.member.common.entity;

import static com.ggb.graduationgoodbye.global.utils.StringConverter.convertToMasking;

import com.ggb.graduationgoodbye.domain.member.common.enums.Role;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("member")
@Getter
@NoArgsConstructor
public class Member {

  private Long id;
  private SnsType snsType;
  private String snsId;
  private String email;
  private String profile;
  private String nickname;
  private String address;
  private String gender;
  private Integer age;
  private String phone;
  private String createdId;
  private LocalDateTime createdAt;
  private String updatedId;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private Role role;

  @Builder
  public Member(
      SnsType snsType,
      String snsId,
      String email,
      String profile,
      String nickname,
      String address,
      String gender,
      Integer age,
      String phone
  ) {
    LocalDateTime now = LocalDateTime.now();

    this.snsType = snsType;
    this.snsId = snsId;
    this.email = email;
    this.profile = profile;
    this.nickname = nickname;
    this.address = address;
    this.phone = phone;
    this.gender = gender;
    this.age = age;
    this.createdId = "SIGNUP";
    this.createdAt = now;
    this.updatedId = "SIGNUP";
    this.updatedAt = now;
    this.role = Role.MEMBER;
  }

  public void withdraw() {
    this.email = convertToMasking(this.email, "*");
    this.profile = convertToMasking(this.profile, "*");
    this.nickname = convertToMasking(this.nickname, "*");
    this.address = convertToMasking(this.address, "*");
    this.gender = convertToMasking(this.gender, "*");
    this.age = null;
    this.phone = convertToMasking(this.phone, "*");
    this.deletedAt = LocalDateTime.now();
  }
}
