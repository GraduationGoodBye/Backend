package com.ggb.graduationgoodbye.domain.member.dto;

import lombok.Builder;

@Builder
public record GoogleInfoDto(
    String sub,
    String email,
    String picture) {

}
