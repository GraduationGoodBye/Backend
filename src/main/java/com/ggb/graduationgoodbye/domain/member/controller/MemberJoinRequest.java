package com.ggb.graduationgoodbye.domain.member.controller;

public record MemberJoinRequest(
    String snsType,
    String accessToken,
    String nickname,
    String address,
    String gender,
    Integer age,
    String phone
) {

}
