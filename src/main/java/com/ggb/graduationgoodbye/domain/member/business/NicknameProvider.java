package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.member.common.exception.NotExistsRemainNicknameException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NicknameProvider {

  private final MemberReader memberReader;
  private final NicknameGenerator nicknameGenerator;

  public Set<String> provideRandomNicknames(int count) {
    Set<String> nicknames = new HashSet<>();
    int maxCount = count + 10;
    while (nicknames.size() < count) {
      if (maxCount-- < 1) {
        throw new NotExistsRemainNicknameException();
      }
      String nickname = nicknameGenerator.generate();
      if (!memberReader.existsByNickname(nickname)) {
        nicknames.add(nickname);
      }
    }
    return nicknames;
  }
}
