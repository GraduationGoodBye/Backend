package com.ggb.graduationgoodbye.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import com.ggb.graduationgoodbye.global.utils.StringConverter;
import org.junit.jupiter.api.Test;

public class StringConverterTest extends ServiceTest {

  @Test
  void convertToMasking_success() {
    String input = "MEMBER";
    Character maskChar = '*';
    String maskStr1 = "@!";
    String maskStr2 = "null";

    String maskedByChar = StringConverter.convertToMasking(input, maskChar);
    String maskedByStr1 = StringConverter.convertToMasking(input, maskStr1);
    String maskedByStr2 = StringConverter.convertToMasking(input, maskStr2);

    assertEquals(maskedByChar, "******");
    assertEquals(maskedByStr1, "@!@!@!");
    assertEquals(maskedByStr2, "nullnu");
  }

  @Test
  void convertToMasking_fail() {
    String input = "MEMBER";
    Character maskChar = null;
    String maskStr = null;

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskChar));
    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }
}
