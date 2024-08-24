package com.ggb.graduationgoodbye.domain.artist.common.entity;

import com.ggb.graduationgoodbye.domain.admin.entity.Admin;
import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 작가 Entity.
 */
@Alias("artist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Artist {

  private Long id;
  private Member memberId;
  private CommonCode universityId;
  private CommonCode majorId;
  private Admin adminId;
  private String name;
  private String createdId;
  private LocalDateTime createdAt;
  private String updatedId;
  private LocalDateTime updatedAt;
  private String certificateUrl;
  private LocalDateTime approvalDate;

  /**
   * 작가 Builder.
   */
  @Builder
  public Artist(
      Member memberId,
      CommonCode universityId,
      CommonCode majorId,
      Admin adminId,
      String name,
      String createdId,
      String certificateUrl,
      LocalDateTime approvalDate
  ) {
    this.memberId = memberId;
    this.universityId = universityId;
    this.majorId = majorId;
    this.adminId = adminId;
    this.name = name;
    this.createdId = createdId;
    this.createdAt = LocalDateTime.now();
    this.updatedId = createdId;
    this.updatedAt = LocalDateTime.now();
    this.certificateUrl = certificateUrl;
    this.approvalDate = approvalDate;
  }
}
