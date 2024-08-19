package com.ggb.graduationgoodbye.domain.commonCode.service;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.repository.CommonCodeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 학과 Reader.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MajorReader {

  private final CommonCodeRepository commonCodeRepository;

  public List<CommonCode> findMajorAll() {
    return commonCodeRepository.findCommonCode("major");
  }

  public Optional<CommonCode> findByMajor(String name) {
    return commonCodeRepository.findCommonCode("major", name);
  }

}
