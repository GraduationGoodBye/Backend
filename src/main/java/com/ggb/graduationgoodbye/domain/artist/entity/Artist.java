package com.ggb.graduationgoodbye.domain.artist.entity;

import com.ggb.graduationgoodbye.domain.admin.entity.Admin;
import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("artist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Artist {
    private Long id;
    private Member member_id;
    private CommonCode common_university_id;
    private CommonCode common_major_id;
    private Admin admin_id;
    private String name;
    private String created_id;
    private LocalDateTime created_at;
    private String updated_id;
    private LocalDateTime updated_at;
    private String certificate_url;
    private LocalDateTime approval_date;

    @Builder
    public Artist(
            Member member_id,
            CommonCode common_university_id,
            CommonCode common_major_id,
            Admin admin_id,
            String name,
            String created_id,
            LocalDateTime created_at,
            String updated_id,
            String certificate_url,
            LocalDateTime approval_date
    ) {
        this.member_id = member_id;
        this.common_university_id = common_university_id;
        this.common_major_id = common_major_id;
        this.admin_id = admin_id;
        this.name = name;
        this.created_id = created_id;
        this.created_at = created_at;
        this.updated_id = updated_id;
        this.updated_at = LocalDateTime.now();
        this.certificate_url = certificate_url;
        this.approval_date = approval_date;
    }
}
