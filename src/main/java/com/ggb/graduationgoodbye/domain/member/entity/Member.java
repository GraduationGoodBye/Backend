package com.ggb.graduationgoodbye.domain.member.entity;

import com.ggb.graduationgoodbye.domain.member.enums.Role;
import com.ggb.graduationgoodbye.domain.member.enums.SnsType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

  private Long id;
  private SnsType snsType;
  private String snsId;
  private String email;
  private String profile;
  private String nickname;
  private String address;
  private String gender;
  private Long age;
  private String phone;
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
      Long age,
      String phone
  ) {
    this.snsType = snsType;
    this.snsId = snsId;
    this.email = email;
    this.profile = profile;
    this.nickname = nickname;
    this.address = address;
    this.phone = phone;
    this.gender = gender;
    this.age = age;
    this.role = Role.USER;
  }
}
