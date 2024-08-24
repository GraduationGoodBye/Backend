package com.ggb.graduationgoodbye.domain.auth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * SecurityUtil.
 */
@Component
@Slf4j
public class AuthUtil {

  public Long getCurrentMemberId() {
    return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
  }

}
