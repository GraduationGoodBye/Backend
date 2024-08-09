package com.ggb.graduationgoodbye.domain.member.controller;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {

}
