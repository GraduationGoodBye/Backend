package com.ggb.graduationgoodbye.domain.commonCode.common.dto;

import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 대학 찾기 DTO.
 */
public class FindUniversityDto {

  /**
   * 대학 찾기 요청.
   */
  @Getter
  @Schema(name = "FindUniversityDto_Request")
  public static class Request {

    String name;
  }

  /**
   * 대학 찾기 반환.
   */
  @Getter
  @Builder
  @Schema(name = "FindUniversityDto_Response")
  public static class Response {

    Long id;
    String university;

    /**
     * Entity -> DTO 변환.
     */
    public static Response fromEntity(CommonCode commonCode) {
      return Response.builder()
          .id(commonCode.getId())
          .university(commonCode.getName())
          .build();
    }

    /**
     * List -> DTO 변환.
     */
    public static List<Response> fromList(List<CommonCode> commonCode) {
      List<FindUniversityDto.Response> response = new ArrayList<>();

      for (CommonCode common : commonCode) {
        FindUniversityDto.Response dto = Response.builder()
            .id(common.getId())
            .university(common.getName())
            .build();

        response.add(dto);
      }

      return response;
    }
  }

}