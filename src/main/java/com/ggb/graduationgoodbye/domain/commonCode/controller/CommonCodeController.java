package com.ggb.graduationgoodbye.domain.commonCode.controller;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.service.CommonCodeService;
import com.ggb.graduationgoodbye.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 공통 코드 Controller.
 */
@RestController
@RequestMapping("/api/v1/commonCode")
@RequiredArgsConstructor
@Slf4j
public class CommonCodeController {

  private final CommonCodeService commonCodeService;

  @GetMapping("/findUniversityAll")
  public ApiResponse<List<CommonCode>> findUniversityAll() {
    return ApiResponse.ok(commonCodeService.findUniversityAll());
  }

  @GetMapping("/findMajorAll")
  public ApiResponse<List<CommonCode>> findMajorAll() {
    return ApiResponse.ok(commonCodeService.findMajorAll());
  }


  /* 테스트용 */
  @PostMapping("/findUniversity")
  public ApiResponse<CommonCode> findUniversity(@RequestBody String name) {
    return ApiResponse.ok(commonCodeService.findByUniversity(name));
  }

  @PostMapping("/findMajor")
  public ApiResponse<CommonCode> findMajor(@RequestBody String name) {
    return ApiResponse.ok(commonCodeService.findByMajor(name));
  }
}
