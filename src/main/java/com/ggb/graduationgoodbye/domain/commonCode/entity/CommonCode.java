package com.ggb.graduationgoodbye.domain.commonCode.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("commonCode")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonCode {
    private Long id;
    private String type;
    private String code;
    private String name;
    private String created_id;
    private LocalDateTime created_at;
    private String updated_id;
    private LocalDateTime updated_at;

    @Builder
    public CommonCode(
            String type,
            String code,
            String name,
            String created_id,
            LocalDateTime created_at,
            String updated_id
    ) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.created_id = created_id;
        this.created_at = created_at;
        this.updated_id = updated_id;
        this.updated_at = LocalDateTime.now();
    }
}
