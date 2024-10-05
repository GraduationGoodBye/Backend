package com.ggb.graduationgoodbye.global.util.test2;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValue {

  protected static final ThreadLocalRandom random = ThreadLocalRandom.current();
  private static final RandomValueGenerator randomValueGenerator = new RandomValueGenerator();

  public static StringGenerator string() {
    return new StringGenerator();
  }

  public static StringGenerator string(int minSize, int maxSize) {
    return new StringGenerator(minSize, maxSize);
  }

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
