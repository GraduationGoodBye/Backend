package com.ggb.graduationgoodbye.domain.member.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

public class TokenReissueDto {

  @Getter
  public static class Request {

    @NotBlank(message = "RefreshToken 을 함께 요청 바랍니다.")
    private String refreshToken;
  }

  @Builder
  public static class Response {

    private String accessToken;
    private String refreshToken;
  }
}
