package com.ggb.graduationgoodbye.domain.member.dto;

import org.apache.ibatis.type.Alias;

@Alias("sns")
public record SnsDto(
    String snsType,
    String snsId
) {

}
