package com.ggb.graduationgoodbye.domain.member.utils;

import com.ggb.graduationgoodbye.domain.member.service.MemberReader;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomNicknameUtil {

  private final NicknameGenerator nicknameGenerator;
  private final MemberReader memberReader;

  public List<String> generateNicknames() {
    List<String> nicknames = new ArrayList<>();
    int needCount = 3;
    int count = 0;
    while (count < needCount) {
      String nickname = nicknameGenerator.generate();
      if (memberReader.findByNickname(nickname).isEmpty()) {
        nicknames.add(nickname);
        count++;
      }
    }

    /*
    3개를 만드는데, 1번씩 3번 요청
    -> 3개 다 중복이야 -> 3번 또 요청
    -> 또 중복 -> 중복 횟수만큼 요청

    List, Array로 IN 조건으로 1번만 요청
    3개 중복이면 -> 1번 요청
    또 3개 중복이면 -> 1번 요청
     */
    return nicknames;
  }
}
