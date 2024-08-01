package com.ggb.graduationgoodbye.domain.user.dto;

public record UserJoinRequest (
    String accessToken,
    String nickname,
    String address,
    String phone,
    String gender
) {
}
