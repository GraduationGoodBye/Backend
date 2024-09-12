package com.ggb.graduationgoodbye.global.utils;

import java.util.Objects;
import org.springframework.util.StringUtils;

public class StringConverter {

  public static String convertToMasking(String input, Character pattern) {
    if (Objects.isNull(pattern)) {
      throw new IllegalArgumentException("pattern is null");
    }
    return convertToMasking(input, String.valueOf(pattern));
  }

  public static String convertToMasking(String input, String pattern) {
    if (!StringUtils.hasText(input)) {
      return input;
    }
    if (!StringUtils.hasText(pattern)) {
      throw new IllegalArgumentException("pattern is null");
    }

    int inputLength = input.length();
    int patternLength = pattern.length();

    StringBuilder maskedStr = new StringBuilder(inputLength);
    maskedStr.append(pattern.repeat(inputLength / patternLength));
    maskedStr.append(pattern, 0, inputLength % patternLength);
    return maskedStr.toString();
  }
}