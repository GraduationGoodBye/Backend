package com.ggb.graduationgoodbye.domain.member.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenReissueDto {

  @Schema(name = "TokenReissue_Request", description = "액세스 토큰 재발급 요청 DTO")
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Request {

    @Schema(description = "리프레쉬 토큰")
    @NotBlank(message = "RefreshToken 을 함께 요청 바랍니다.")
    private String refreshToken;
  }

  @Schema(name = "TokenReissue_Response", description = "액세스 토큰 재발급 응답 DTO")
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Response {

    @Schema(description = "액세스토큰")
    private String accessToken;
    @Schema(description = "리프레쉬토큰")
    private String refreshToken;
  }
}
