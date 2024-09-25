package com.ggb.graduationgoodbye.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import com.ggb.graduationgoodbye.global.utils.StringConverter;
import org.junit.jupiter.api.Test;

public class StringConverterTest extends ServiceTest {

  @Test
  void convertToMasking_문자_성공() {
    // given
    String input = "MEMBER";
    Character maskChar = '*';

    // when
    String maskedByChar = StringConverter.convertToMasking(input, maskChar);

    // then
    assertEquals(maskedByChar, "******");
  }

  @Test
  void convertToMasking_문자_실패_null() {
    // given
    String input = "MEMBER";
    Character maskChar = null;

    // when then
    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskChar));
  }

  @Test
  void convertToMasking_문자열_성공() {
    // given
    String input = "Member";
    String maskStr = "@!";

    // when
    String maskedByStr = StringConverter.convertToMasking(input, maskStr);

    // then
    assertEquals(maskedByStr, "@!@!@!");
  }

  @Test
  void convertToMasking_문자열_실패_null() {
    // given
    String input = "Member";
    String maskStr = null;

    // when then
    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  void convertToMasking_문자열_실패_빈값() {
    // given
    String input = "Member";
    String maskStr = "";

    // when then
    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  void convertToMasking_문자열_실패_공백() {
    // given
    String input = "Member";
    String maskStr = " ";

    // when then
    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }
}
