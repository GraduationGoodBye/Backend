package com.ggb.graduationgoodbye.domain.commonCode.dto;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
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
  @Schema(name = "FindMajorDTO_Request")
  public static class Request {

    String name;
  }

  /**
   * 학과 찾기 반환.
   */
  @Getter
  @Builder
  public static class Response {

    Long id;
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
