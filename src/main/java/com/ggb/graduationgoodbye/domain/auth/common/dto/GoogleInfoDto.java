package com.ggb.graduationgoodbye.domain.auth.common.dto;

public record GoogleInfoDto(
    String sub,
    String email,
    String picture) {

}
