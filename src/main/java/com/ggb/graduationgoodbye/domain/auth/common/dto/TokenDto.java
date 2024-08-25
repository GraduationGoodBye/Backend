package com.ggb.graduationgoodbye.domain.auth.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

  private String accessToken;
  private String refreshToken;
}
