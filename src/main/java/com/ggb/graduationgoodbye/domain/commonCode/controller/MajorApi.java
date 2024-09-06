package com.ggb.graduationgoodbye.domain.commonCode.controller;

import com.ggb.graduationgoodbye.domain.commonCode.common.dto.FindMajorDto;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "major", description = "the major API")
public interface MajorApi {

  @Operation(summary = "전체 전공 조회", tags = {"major"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "전체 전공 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<List<FindMajorDto.Response>> findMajorAll();

  @Operation(summary = "단일 전공 조회", tags = {"major"})
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "단일 전공 조회 성공"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류")
  })
  ApiResponse<FindMajorDto.Response> findMajor(@RequestBody FindMajorDto.Request request);
}
