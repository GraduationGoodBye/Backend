package com.ggb.graduationgoodbye.domain.member.entity;

import com.ggb.graduationgoodbye.domain.member.enums.Role;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

  private Long id;
  private String email;
  private String profile;
  private String name;
  private String nickname;
  private String address;
  private String phone;
  private String gender;
  private Role role;

  @Builder
  public Member(
      String email,
      String profile,
      String name,
      String nickname,
      String address,
      String phone,
      String gender
  ) {
    this.email = email;
    this.profile = profile;
    this.name = name;
    this.nickname = nickname;
    this.address = address;
    this.phone = phone;
    this.gender = gender;
    this.role = Role.USER;
  }
}