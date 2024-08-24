package com.ggb.graduationgoodbye.domain.commonCode.business;

import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.repository.CommonCodeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 대학 Reader.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UniversityReader {

  private final CommonCodeRepository commonCodeRepository;

  public List<CommonCode> findUniversityAll() {
    return commonCodeRepository.findCommonCode("university");
  }

  public Optional<CommonCode> findUniversity(String name) {
    return commonCodeRepository.findCommonCode("university", name);
  }
}
