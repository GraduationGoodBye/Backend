package com.ggb.graduationgoodbye.domain.member.business;

import com.ggb.graduationgoodbye.domain.member.common.exception.MaxNicknameCountExceededException;
import com.ggb.graduationgoodbye.domain.member.common.exception.NegativeNicknameCountException;
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
  static final int ALLOWABLE_RANGE = 10;
  static final int MAX_REQUEST_COUNT = 100;

  public Set<String> provideRandomNicknames(int count) {
    Set<String> nicknames = new HashSet<>();
    validateCount(count);
    int maxCount = count + ALLOWABLE_RANGE;
    for (int i = 0; i < maxCount && nicknames.size() < count; i++) {
      String nickname = nicknameGenerator.generate();
      if (!memberReader.existsByNickname(nickname)) {
        nicknames.add(nickname);
      }
    }

    if (nicknames.size() < count) {
      throw new NotExistsRemainNicknameException();
    }
    return nicknames;
  }

  private void validateCount(int count) {
    if (count > MAX_REQUEST_COUNT) {
      throw new MaxNicknameCountExceededException();
    } else if (count < 0) {
      throw new NegativeNicknameCountException();
    }
  }
}