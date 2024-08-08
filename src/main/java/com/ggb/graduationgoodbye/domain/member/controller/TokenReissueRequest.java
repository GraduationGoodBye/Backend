package com.ggb.graduationgoodbye.domain.member.controller;

import jakarta.validation.constraints.NotBlank;

public record TokenReissueRequest(
    @NotBlank
    String refreshToken
) {

}
