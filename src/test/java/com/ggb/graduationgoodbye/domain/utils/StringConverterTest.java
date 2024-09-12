package com.ggb.graduationgoodbye.domain.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ggb.graduationgoodbye.global.test.ServiceTest;
import com.ggb.graduationgoodbye.global.utils.StringConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringConverterTest extends ServiceTest {

  @Test
  @DisplayName("convertToMasking_문자 패턴_성공")
  void convertToMasking_character_success() {
    String input = "MEMBER";
    Character maskChar = '*';

    String maskedByChar = StringConverter.convertToMasking(input, maskChar);

    assertEquals(maskedByChar, "******");
  }

  @Test
  @DisplayName("convertToMasking_문자 패턴_실패_null")
  void convertToMasking_character_fail_null() {
    String input = "MEMBER";
    Character maskChar = null;

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskChar));
  }

  @Test
  @DisplayName("convertToMasking_문자열 패턴_성공")
  void convertToMasking_String_success() {
    String input = "Member";
    String maskStr = "@!";

    String maskedByStr = StringConverter.convertToMasking(input, maskStr);

    assertEquals(maskedByStr, "@!@!@!");
  }

  @Test
  @DisplayName("convertToMasking_문자열 패턴_실패_null")
  void convertToMasking_String_fail_null() {
    String input = "Member";
    String maskStr = null;

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  @DisplayName("convertToMasking_문자열 패턴_실패_빈값")
  void convertToMasking_String_fail_blank() {
    String input = "Member";
    String maskStr = "";

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }

  @Test
  @DisplayName("convertToMasking_문자열 패턴_실패_공백")
  void convertToMasking_String_fail_space() {
    String input = "Member";
    String maskStr = " ";

    assertThrows(IllegalArgumentException.class,
        () -> StringConverter.convertToMasking(input, maskStr));
  }
}
