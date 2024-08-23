package com.ggb.graduationgoodbye.domain.commonCode.controller;

import com.ggb.graduationgoodbye.domain.commonCode.dto.FindMajorDto;
import com.ggb.graduationgoodbye.domain.commonCode.dto.FindMajorDto.Response;
import com.ggb.graduationgoodbye.domain.commonCode.dto.FindUniversityDto;
import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.service.MajorService;
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
 * 학과 Controller.
 */
@RestController
@RequestMapping("/api/v1/major")
@RequiredArgsConstructor
@Slf4j
public class MajorController {

  private final MajorService majorService;


  /**
   * 전체 학과 조회.
   */
  @GetMapping("/findMajorAll")
  public ApiResponse<List<FindMajorDto.Response>> findMajorAll() {
    List<CommonCode> majorList = majorService.findMajorAll();
    List<FindMajorDto.Response> response = FindMajorDto.Response.fromList(majorList);
    return ApiResponse.ok(response);
  }


  /**
   * 특정 학과 조회.
   */
  @PostMapping("/findMajor")
  public ApiResponse<FindMajorDto.Response> findMajor(@RequestBody FindMajorDto.Request request) {
    CommonCode major = majorService.findByMajor(request.getName());
    FindMajorDto.Response response = FindMajorDto.Response.fromEntity(major);
    return ApiResponse.ok(response);  }

}
