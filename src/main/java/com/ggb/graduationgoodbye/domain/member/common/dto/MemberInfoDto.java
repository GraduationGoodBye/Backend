package com.ggb.graduationgoodbye.domain.member.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberInfoDto {

  @Schema(name = "MemberInfo_Response", description = "회원정보 응답 DTO")
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response {

    @Schema(description = "ID")
    private Long id;
    @Schema(description = "SNS 타입")
    private String snsType;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "프로필 이미지")
    private String profile;
    @Schema(description = "닉네임")
    private String nickname;
  }
}
