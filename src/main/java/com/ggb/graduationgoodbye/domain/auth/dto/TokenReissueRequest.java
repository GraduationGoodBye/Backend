package com.ggb.graduationgoodbye.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenReissueRequest(
    @NotBlank
    String refreshToken,
    @NotBlank
    String accessToken
) {

}
