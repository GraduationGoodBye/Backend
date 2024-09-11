package com.ggb.graduationgoodbye.global.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

public class CustomAssertions {

  public static void assertDateTimeEquals(LocalDateTime a, LocalDateTime b) {
    LocalDateTime localDateTimeA = a.withNano(0);
    LocalDateTime localDateTimeB = b.withNano(0);
    assertEquals(localDateTimeA, localDateTimeB);
  }


}
