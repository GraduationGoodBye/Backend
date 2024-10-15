package com.ggb.graduationgoodbye.global.util.test2;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 랜덤 값을 생성하는 로직은 StringGenerator, RandomValueGenerator에 존재하며,
 * 실제 사용시, 해당 클래스의 메서드를 호출하여 랜덤 값을 생성할 수 있음
 *
 * 랜덤 String 값 생성시, 필수적으로 StringGenerator 내부의 .get() 메소드를 호출해야만 함
 * 이외 값은 별도의 처리 없이 해당 클래스의 메소드를 호출하면 생성됨
 */
public class RandomValue {

  protected static final ThreadLocalRandom random = ThreadLocalRandom.current();
  private static final RandomValueGenerator randomValueGenerator = new RandomValueGenerator();

  /**
   * 별도의 길이 설정 없이 랜덤한 문자열을 생성하고 싶을 경우 사용
   * 해당 메소드는 StringGenerator을 생성하기 위한 메소드로,
   * StringGenerator 내부 .get() 메소드를 호출해야만 실제 값 생성 가능
   */
  public static StringGenerator string() {
    return new StringGenerator();
  }

  /**
   * 최소, 최대 길이를 지정하여 문자열을 생성할 경우 사용
   * 해당 메소드는 최소,최대 길이 설정만을 위한 메소드로, 실제 값 생성은
   * StringGenerator 내부 .get() 메소드를 호출해야만 생성 됨
   */
  public static StringGenerator string(int minSize, int maxSize) {
    return new StringGenerator(minSize, maxSize);
  }

  /**
   * 최대 길이를 지정하여 문자열을 생성할 경우 사용
   * 해당 메소드는 최대 길이 설정만을 위한 메소드로, 실제 값 생성은
   * StringGenerator 내부 .get() 메소드를 호출해야만 생성 됨
   */

  public static StringGenerator string(int maxSize) {
    return new StringGenerator(maxSize);
  }

  public static int getInt() {
    return randomValueGenerator.getRandomInt();
  }

  public static int getInt(int max) {
    return randomValueGenerator.getRandomInt(max);
  }

  public static int getInt(int min, int max) {
    return randomValueGenerator.getRandomInt(min, max);
  }


  public static long getRandomLong(int max) {
    return randomValueGenerator.getRandomLong(max); }

  public static long getRandomLong(int min, int max) {
    return randomValueGenerator.getRandomLong(min, max);
  }

  public static boolean getRandomBoolean() {

    return randomValueGenerator.getRandomBoolean();
  }

  public static <T> T getRandomBoolean(T a, T b) {
    return randomValueGenerator.getRandomBoolean(a,b);
  }


  public static double getRandomDouble(double max) {
    return randomValueGenerator.getRandomDouble(max);
  }

  public static double getRandomDouble(double min, double max) {
    return randomValueGenerator.getRandomDouble(min, max);
  }


  public static LocalDateTime getRandomLocalDateTime() {
    return randomValueGenerator.getRandomLocalDateTime();
  }

  public static <T> T getRandomEnum(Class<T> clazz) {
    return randomValueGenerator.getRandomEnum(clazz);
  }

}
