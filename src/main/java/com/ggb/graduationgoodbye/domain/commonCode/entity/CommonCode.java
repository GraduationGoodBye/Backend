package com.ggb.graduationgoodbye.domain.commonCode.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 공통 코드 Entity.
 */
@Alias("commonCode")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonCode {

  private Long id;
  private String type;
  private String code;
  private String name;
  private String createdId;
  private LocalDateTime createdAt;
  private String updatedId;
  private LocalDateTime updatedAt;

  /**
   * 공통 코드 Builder.
   */
  @Builder
  public CommonCode(
      String type,
      String code,
      String name,
      String createdId,
      LocalDateTime createdAt,
      String updatedId
  ) {
    this.type = type;
    this.code = code;
    this.name = name;
    this.createdId = createdId;
    this.createdAt = createdAt;
    this.updatedId = updatedId;
    this.updatedAt = LocalDateTime.now();
  }
}
