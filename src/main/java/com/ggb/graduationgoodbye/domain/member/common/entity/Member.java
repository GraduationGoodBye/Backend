package com.ggb.graduationgoodbye.domain.member.common.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import com.ggb.graduationgoodbye.domain.member.common.enums.Role;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("member")
@Getter
@Setter
@NoArgsConstructor
public class Member {

  @NotNull
  private Long id;
  @NotNull
  private SnsType snsType;
  @NotNull @Size(max = 100)
  private String snsId;
  @NotNull @Size(max = 100)
  private String email;
  @Size(max = 255)
  private String profile;
  @NotNull @Size(max = 50)
  private String nickname;
  @Size(max = 255)
  private String address;
  @Size(max = 1)
  private String gender;
  @Min(1) @Max(100)
  private Integer age;
  @Size(max = 13)
  private String phone;
  @NotNull @Size(max = 255)
  private String createdId;
  @NotNull @PastOrPresent
  private Date createdAt;
  @Size(max = 255)
  private String updatedId;
  @PastOrPresent
  private Date updatedAt;
  @PastOrPresent
  private Date deletedAt;
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
    Date now = new Date();

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
