package com.ggb.graduationgoodbye.domain.commonCode.controller;

import com.ggb.graduationgoodbye.domain.commonCode.common.dto.FindUniversityDto;
import com.ggb.graduationgoodbye.domain.commonCode.common.dto.FindUniversityDto.Response;
import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.service.UniversityService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대학 Controller.
 */
@RestController
@RequestMapping("/api/v1/university")
@RequiredArgsConstructor
@Slf4j
public class UniversityController {

  private final UniversityService universityService;

  /**
   * 전체 대학 조회.
   */
  @GetMapping("/findUniversityAll")
  public ApiResponse<List<FindUniversityDto.Response>> findUniversityAll() {
    List<CommonCode> universityList = universityService.findUniversityAll();
    List<FindUniversityDto.Response> response = Response.fromList(universityList);
    return ApiResponse.ok(response);
  }


  /**
   * 단일 대학 조회.
   */
  @PostMapping("/findUniversity")
  public ApiResponse<FindUniversityDto.Response> findUniversity(
      @RequestBody FindUniversityDto.Request request) {
    CommonCode university = universityService.findByUniversity(request.getName());
    FindUniversityDto.Response response = Response.fromEntity(university);
    return ApiResponse.ok(response);
  }

}
