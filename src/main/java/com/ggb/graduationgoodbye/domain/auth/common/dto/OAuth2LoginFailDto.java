package com.ggb.graduationgoodbye.domain.auth.common.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2LoginFailDto {

  private String oauthToken;
  private List<String> nicknames;
}
