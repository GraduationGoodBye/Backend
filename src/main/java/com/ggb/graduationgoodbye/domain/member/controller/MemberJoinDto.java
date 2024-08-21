package com.ggb.graduationgoodbye.domain.member.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class MemberJoinDto {

  @Getter
  public static class Request {

    @NotBlank(message = "SNS 타입을 입력 바랍니다.")
    private String snsType;
    @NotBlank(message = "액세스 토큰을 함께 요청 바랍니다.")
    private String accessToken;
    @NotBlank(message = "닉네임을 입력 바랍니다.")
    private String nickname;
    private String address;
    private String gender;
    private Integer age;
    private String phone;
  }

  @Getter
  @Builder
  public static class Response {

    private String accessToken;
    private String refreshToken;
  }
}
