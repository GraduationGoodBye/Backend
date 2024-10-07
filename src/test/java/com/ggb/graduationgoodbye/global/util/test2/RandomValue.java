package com.ggb.graduationgoodbye.global.util.test2;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 랜덤 값을 생성해주는 유틸리티 클래스
 * 랜덤 값을 생성하는 로직은 StringGenerator, RandomValueGenerator에 존재하며,
 * 실제 사용시, 해당 클래스의 메서드를 호출하여 랜덤 값을 생성할 수 있음
 */
public class RandomValue {

  protected static final ThreadLocalRandom random = ThreadLocalRandom.current();
  private static final RandomValueGenerator randomValueGenerator = new RandomValueGenerator();

  /**
   * 랜덤 문자열을 생성할 수 있는 StringGenerator 인스턴스 반환.
   * StringGenerator 내부 .get() 메소드를 호출해야만 실제 값 생성 가능
   */
  public static StringGenerator string() {
    return new StringGenerator();
  }

  /**
   * 랜덤 문자열을 생성할 수 있는 StringGenerator 인스턴스 반환.
   * StringGenerator 내부에 선언 되어있는 minSize, maxSize를 지정하여
   * 문자열의 길이를 설정할 수 있음
   * StringGenerator 내부 .get() 메소드를 호출해야만 실제 값 생성 가능
   */
  public static StringGenerator string(int minSize, int maxSize) {
    return new StringGenerator(minSize, maxSize);
  }

  /**
   * 랜덤 문자열을 생성할 수 있는 StringGenerator 인스턴스 반환.
   * StringGenerator 내부에 선언 되어있는 maxSize를 지정하여
   * 문자열의 길이를 설정할 수 있음
   * StringGenerator 내부 .get() 메소드를 호출해야만 실제 값 생성 가능
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
