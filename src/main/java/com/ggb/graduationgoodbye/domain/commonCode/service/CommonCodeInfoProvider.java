package com.ggb.graduationgoodbye.domain.commonCode.service;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.repository.CommonCodeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 공통 코드 InfoProvider.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CommonCodeInfoProvider {

  private final CommonCodeRepository commonCodeRepository;


  public List<CommonCode> findUniversityAll() {
    return commonCodeRepository.findUniversityAll();
  }

  public Optional<CommonCode> findByUniversity(String name) {
    return commonCodeRepository.findByUniversity(name);
  }

  public List<CommonCode> findMajorAll() {
    return commonCodeRepository.findMajorAll();
  }

  public Optional<CommonCode> findByMajor(String name) {
    return commonCodeRepository.findByMajor(name);
  }
}
