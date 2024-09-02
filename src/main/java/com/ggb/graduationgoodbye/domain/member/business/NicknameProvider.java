package com.ggb.graduationgoodbye.domain.member.business;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NicknameProvider {

  private final MemberReader memberReader;
  private final NicknameGenerator nicknameGenerator;

  public List<String> provideRandomNicknames() {
    ArrayList<String> nicknames = new ArrayList<>();
    int count = 1;
    while (nicknames.size() < count) {
      String nickname = nicknameGenerator.generate();
      if (!memberReader.existsByNickname(nickname)) {
        nicknames.add(nickname);
      }
    }
    return nicknames;
  }
}
