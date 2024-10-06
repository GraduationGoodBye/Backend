package com.ggb.graduationgoodbye.global.util.randomValue;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.util.StringUtils;

public class RandomValueGenerator {

  private static final Random random = new Random();

  public static String getRandomString(int size) {
    return getRandomString(size, "UTF-8");
  }

  public static String getRandomString(int size, String charsetStr) {
    byte[] array = getRandomByte(size);
    if (!StringUtils.hasText(charsetStr) || !isSupportedCharset(charsetStr)) {
      throw new RuntimeException("잘못된 형식의 charSet 입니다.");
    }
    Charset character = Charset.forName(charsetStr);
    String str = new String(array, character);
    return str.length() < size ? str : str.substring(0, size);
  }

  public static String getRandomEmail(String charset) {
    String email = getRandomString(10, charset);
    String domain = getRandomString(5, charset);
    String subDomain = getRandomString(3, charset);
    return email + "@" + domain + "." + subDomain;
  }

  public static int getRandomInt(int size) {
    return random.nextInt(size);
  }

  public static long getRandomLong(int size) {
    return random.nextLong();
  }

  public static boolean getRandomBoolean() {
    return random.nextBoolean();
  }

  public static double getRandomDouble(int size) {
    return random.nextDouble() * size;
  }

  public static LocalDateTime getRandomLocalDateTime() {
    return LocalDateTime.now().minusDays(random.nextInt(365)).withNano(0);
  }

  private static byte[] getRandomByte(int size) {
    byte[] array = new byte[size];
    for (int i = 0; i < size; i++) {
      array[i] = (byte) random.nextInt(32, 127);
    }
    return array;
  }

  public static <T> T getRandomEnum(Class<T> clazz) {
    T[] enumConstants = clazz.getEnumConstants();
    return enumConstants[random.nextInt(enumConstants.length)];
  }


  private static Charset getCharset() {
    Set<String> charset = Charset.availableCharsets().keySet();
    List<String> list = new ArrayList<>(charset);
    int randomIndex = random.nextInt(list.size());
    return Charset.forName(list.get(randomIndex));
  }

  private static boolean isSupportedCharset(String charset) {
    return Charset.isSupported(charset);
  }
}
