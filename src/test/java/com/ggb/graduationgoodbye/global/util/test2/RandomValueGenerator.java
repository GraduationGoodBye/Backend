package com.ggb.graduationgoodbye.global.util.test2;
import java.time.LocalDateTime;
import static com.ggb.graduationgoodbye.global.util.test2.RandomValue.random;


public class RandomValueGenerator {

  public int getRandomInt() { return random.nextInt();}
  public int getRandomInt(int max) { return random.nextInt(max);}
  public int getRandomInt(int min, int max) {
    return random.nextInt(min, max);
  }

  public long getRandomLong(int max) { return random.nextLong(max); }

  public long getRandomLong(int min, int max) {
    return random.nextLong(min, max);
  }

  public boolean getRandomBoolean() {
    return random.nextBoolean();
  }

  public <T> T getRandomBoolean(T a, T b) {
    return random.nextBoolean() ? a : b;
  }


  public double getRandomDouble(double max) {
    return random.nextDouble() * max;
  }

  public double getRandomDouble(double min, double max) {
    return min + (random.nextDouble() * (max - min));
  }


  public LocalDateTime getRandomLocalDateTime() {
    return LocalDateTime.now().minusDays(random.nextInt(365)).withNano(0);
  }

  public <T> T getRandomEnum(Class<T> clazz) {
    T[] enumConstants = clazz.getEnumConstants();
    return enumConstants[random.nextInt(enumConstants.length)];
  }

}
