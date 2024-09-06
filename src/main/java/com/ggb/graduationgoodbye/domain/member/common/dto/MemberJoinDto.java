package com.ggb.graduationgoodbye.domain.member.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class MemberJoinDto {

  @Schema(name = "MemberJoin_Request", description = "회원가입 요청 DTO")
  @Getter
  public static class Request {

    @Schema(description = "소셜토큰")
    @NotBlank(message = "oauth 토큰을 함께 요청 바랍니다.")
    private String oauthToken;
    @Schema(description = "닉네임")
    @NotBlank(message = "닉네임을 입력 바랍니다.")
    private String nickname;
    @Schema(description = "주소", nullable = true)
    private String address;
    @Schema(description = "성별", nullable = true)
    private String gender;
    @Schema(description = "나이", nullable = true)
    private Integer age;
    @Schema(description = "연락처", nullable = true)
    private String phone;
  }

  @Schema(name = "MemberJoin_Response", description = "회원가입 응답 DTO")
  @Getter
  @Builder
  public static class Response {

    @Schema(description = "액세스토큰")
    private String accessToken;
    @Schema(description = "리프레쉬토큰")
    private String refreshToken;
  }
}
