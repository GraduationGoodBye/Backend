package com.ggb.graduationgoodbye.domain.commonCode.business;

import com.ggb.graduationgoodbye.domain.commonCode.common.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.common.exception.NotFoundMajorException;
import com.ggb.graduationgoodbye.domain.commonCode.repository.CommonCodeRepository;
import java.util.List;
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

  public CommonCode findByMajor(String name) {
    return commonCodeRepository.findCommonCode("major", name)
        .orElseThrow(NotFoundMajorException::new);
  }
}
