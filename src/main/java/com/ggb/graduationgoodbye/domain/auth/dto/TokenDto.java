package com.ggb.graduationgoodbye.domain.auth.dto;

import com.ggb.graduationgoodbye.domain.member.controller.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

  private String accessToken;
  private String refreshToken;

}
