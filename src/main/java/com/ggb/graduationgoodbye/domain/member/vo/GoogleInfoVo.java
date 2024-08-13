package com.ggb.graduationgoodbye.domain.member.vo;

import java.util.Objects;
import lombok.Builder;

@Builder
public record GoogleInfoVo(String sub, String email, String picture) {

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof GoogleInfoVo googleInfoVo)) {
      return false;
    }
    return Objects.equals(googleInfoVo.sub(), this.sub());
  }

  @Override
  public int hashCode() {
    return Objects.hash(sub);
  }
}
