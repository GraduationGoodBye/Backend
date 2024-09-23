package com.ggb.graduationgoodbye.domain.member.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * MemberProvider.
 */
@Component
@Slf4j
public class MemberProvider {

  public Long getCurrentMemberId() {
    return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}