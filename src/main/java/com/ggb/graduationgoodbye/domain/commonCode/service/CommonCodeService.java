package com.ggb.graduationgoodbye.domain.commonCode.service;

import com.ggb.graduationgoodbye.domain.commonCode.entity.CommonCode;
import com.ggb.graduationgoodbye.domain.commonCode.exception.NotFoundMajorException;
import com.ggb.graduationgoodbye.domain.commonCode.exception.NotFoundUniversityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공통 코드 Service.
 */
@Service
@RequiredArgsConstructor
public class CommonCodeService {

  private final CommonCodeInfoProvider commonCodeInfoProvider;

  public List<CommonCode> findUniversityAll() {
    return commonCodeInfoProvider.findUniversityAll();
  }

  public CommonCode findByUniversity(String name) {
    return commonCodeInfoProvider.findByUniversity(name)
        .orElseThrow(NotFoundUniversityException::new);
  }

  public List<CommonCode> findMajorAll() {
    return commonCodeInfoProvider.findMajorAll();
  }

  public CommonCode findByMajor(String name) {
    return commonCodeInfoProvider.findByMajor(name)
        .orElseThrow(NotFoundMajorException::new);
  }
}
