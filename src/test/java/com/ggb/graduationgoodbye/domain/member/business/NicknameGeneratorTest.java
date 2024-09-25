package com.ggb.graduationgoodbye.domain.member.business;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

@Slf4j
public class NicknameGeneratorTest extends ServiceTest {

  @Spy
  private NicknameGenerator nicknameGenerator;

  @Test
  void generateNickname_무작위생성확인() {
    // given
    int validCount = 100;
    int randomCount = 5;
    int allowableRange = 80;
    int hit = 0;
    for (int i = 0; i < validCount; i++) {
      Set<String> nicknames = new HashSet<>();
      for (int j = 0; j < randomCount; j++) {
        // when
        nicknames.add(nicknameGenerator.generate());
      }
      if (nicknames.size() == randomCount) {
        hit++;
      }
    }
    log.info("성공 횟수 : {}", hit);
    // then
    assertTrue(allowableRange <= hit);
  }
}
