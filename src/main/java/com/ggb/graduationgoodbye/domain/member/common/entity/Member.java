package com.ggb.graduationgoodbye.domain.member.common.entity;

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
}
