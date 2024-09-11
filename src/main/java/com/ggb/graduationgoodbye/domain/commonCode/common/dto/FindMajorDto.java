package com.ggb.graduationgoodbye.domain.commonCode.common.dto;

import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 학과 찾기 DTO.
 */
public class FindMajorDto {

  /**
   * 단일 학과 찾기 요청.
   */
  @Getter
  @Schema(name = "FindMajor_Request")
  public static class Request {

    @Schema(description = "전공명")
    String name;
  }

  /**
   * 학과 찾기 반환.
   */
  @Schema(name = "FindMajor_Response")
  @Getter
  @Builder
  public static class Response {

    @Schema(description = "전공 아이디")
    Long id;
    @Schema(description = "전공명")
    String major;

    /**
     * Entity -> DTO 변환.
     */
    public static FindMajorDto.Response fromEntity(CommonCode commonCode) {
      return Response.builder()
          .id(commonCode.getId())
          .major(commonCode.getName())
          .build();
    }

    /**
     * List -> DTO 변환.
     */
    public static List<FindMajorDto.Response> fromList(List<CommonCode> commonCode) {
      List<FindMajorDto.Response> response = new ArrayList<>();

      for (CommonCode common : commonCode) {
        FindMajorDto.Response dto = Response.builder()
            .id(common.getId())
            .major(common.getName())
            .build();

        response.add(dto);
      }

      return response;
    }
  }

}
