package com.ggb.graduationgoodbye.domain.member.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2FailDto {

  private String accessToken;
  private List<String> nicknames;
}
