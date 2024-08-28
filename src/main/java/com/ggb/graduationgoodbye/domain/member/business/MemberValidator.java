package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.member.common.enums.SnsType;
import com.ggb.graduationgoodbye.domain.member.common.exception.InvalidSnsTypeException;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {

  public void validateSnsType(String snsType) {
    if (Arrays.stream(SnsType.values())
        .noneMatch(type -> type.name().equals(snsType.toUpperCase()))) {
      throw new InvalidSnsTypeException();
    }
  }
}
