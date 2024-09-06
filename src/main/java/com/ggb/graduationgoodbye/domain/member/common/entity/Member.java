package com.ggb.graduationgoodbye.domain.member.common.entity;

import com.ggb.graduationgoodbye.domain.member.common.enums.Role;
import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Schema(name = "Member", description = "회원 Entity")
@Alias("member")
@Getter
@NoArgsConstructor
public class Member {

  @Schema(description = "회원 아이디", example = "1")
  private Long id;
  @Schema(description = "SNS 타입", example = "google")
  private SnsType snsType;
  @Schema(description = "SNS 아이디")
  private String snsId;
  @Schema(description = "이메일", example = "GGB@ggb.com")
  private String email;
  @Schema(description = "사진 URL")
  private String profile;
  @Schema(description = "닉네임", example = "amumu")
  private String nickname;
  @Schema(description = "주소", example = "경기도 ~")
  private String address;
  @Schema(description = "성별", example = "M/W")
  private String gender;
  @Schema(description = "나이", example = "29")
  private Integer age;
  @Schema(description = "연락처", example = "01012345678")
  private String phone;
  @Schema(description = "생성자", example = "SIGNUP")
  private String createdId;
  @Schema(description = "생성일자")
  private LocalDateTime createdAt;
  @Schema(description = "수정자", example = "SIGNUP")
  private String updatedId;
  @Schema(description = "수정일자")
  private LocalDateTime updatedAt;
  @Schema(description = "권한", example = "MEMBER")
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
