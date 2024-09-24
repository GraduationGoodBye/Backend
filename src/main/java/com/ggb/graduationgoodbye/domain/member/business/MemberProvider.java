package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthErrorType;
import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthenticationNameNullException;
import com.ggb.graduationgoodbye.domain.auth.common.exception.AuthenticationNullException;
import com.ggb.graduationgoodbye.domain.auth.common.exception.NotParsedValueException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * MemberProvider.
 */
@Component
@Slf4j
public class MemberProvider {

  public Long getCurrentMemberId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      throw new AuthenticationNullException();
    }
    String name = authentication.getName();
    if (!StringUtils.hasText(name)) {
      throw new AuthenticationNameNullException();
    }
    try {
      return Long.valueOf(name);
    } catch (NumberFormatException e) {
      throw new NotParsedValueException(
          AuthErrorType.NOT_PARSED_VALUE_ERROR.getMessage() + " : " + name);
    }
  }
}