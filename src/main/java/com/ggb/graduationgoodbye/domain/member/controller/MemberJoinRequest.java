package com.ggb.graduationgoodbye.domain.member.controller;

public record MemberJoinRequest(
    String accessToken,
    String nickname,
    String address,
    String phone,
    String gender
) {

}
