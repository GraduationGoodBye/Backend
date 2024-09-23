package com.ggb.graduationgoodbye.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import com.ggb.graduationgoodbye.global.utils.StringConverter;
import org.junit.jupiter.api.Test;

public class StringConverterTest extends ServiceTest {

  @Test
  void convertToMasking_문자_성공() {
    String input = "MEMBER";
    Character maskChar = '*';

    String maskedByChar = StringConverter.convertToMasking(input, maskChar);

    assertEquals(maskedByChar, "******");
  }

  @Test
  void convertToMasking_문자_실패_null() {
    String input = "MEMBER";
    Character maskChar = null;

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskChar));
  }

  @Test
  void convertToMasking_문자열_성공() {
    String input = "Member";
    String maskStr = "@!";

    String maskedByStr = StringConverter.convertToMasking(input, maskStr);

    assertEquals(maskedByStr, "@!@!@!");
  }

  @Test
  void convertToMasking_문자열_실패_null() {
    String input = "Member";
    String maskStr = null;

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  void convertToMasking_문자열_실패_빈값() {
    String input = "Member";
    String maskStr = "";

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  void convertToMasking_문자열_실패_공백() {
    String input = "Member";
    String maskStr = " ";

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }
}
