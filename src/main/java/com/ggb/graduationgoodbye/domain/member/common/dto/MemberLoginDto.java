package com.ggb.graduationgoodbye.domain.member.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class MemberLoginDto {

  @Getter
  public static class Request {

    @NotBlank(message = "인가코드를 입력 바랍니다.")
    private String authCode;
  }

  @Getter
  @Builder
  public static class Response {

    private String accessToken;
    private String refreshToken;
  }
}
