package com.ggb.graduationgoodbye.global.util.test2;
import java.time.LocalDateTime;
import static com.ggb.graduationgoodbye.global.util.test2.RandomValue.random;


/**
 * Int , Long , Boolean , Double , Enum , LocalDateTime
 * 위와 같은 값의 랜덤 값을 생성하는 로직이 작성되어있음.

 * 현재는 복잡한 로직이 필요없기에 String을 제외한 값들은 해당 클래스에 작성하였으나,
 * 이후 추가적인 작업이 필요할 경우 IntGenerator와 같이 분리하는 것을 추천
 */
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


  /**
   * H2에 값을 저장할 때, 시간이 나노초 까지 밖에 저장이 되지 않는 이슈로
   * 랜덤 값을 생성할 때 나노초 까지만 생성 하도록 임시 조치
   */

  public LocalDateTime getRandomLocalDateTime() {
    return LocalDateTime.now().minusDays(random.nextInt(365)).withNano(0);
  }

  public <T> T getRandomEnum(Class<T> clazz) {
    T[] enumConstants = clazz.getEnumConstants();
    return enumConstants[random.nextInt(enumConstants.length)];
  }

}
