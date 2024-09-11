package com.ggb.graduationgoodbye.domain.commonCode.controller;

import com.ggb.graduationgoodbye.domain.commonCode.common.dto.FindUniversityDto;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "university", description = "the university API")
public interface UniversityApi {

  @Operation(summary = "전체 대학 조회", tags = {"university"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "전체 대학 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<List<FindUniversityDto.Response>> findUniversityAll();

  @Operation(summary = "단일 대학 조회", tags = {"university"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "단일 대학 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<FindUniversityDto.Response> findUniversity(
      @RequestBody FindUniversityDto.Request request);

}
