package com.ggb.graduationgoodbye.global.util.randomValue;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class RandomValueGenerator {

  private static final Random random = new Random();

  public String getRandomString(int size) {
    byte[] array = getRandomByte();
    Charset charset = getCharset();
    String str = new String(array, charset);
    return str.length() < size ? str : str.substring(0, size);
  }

  public int getRandomInt(int size) {
    return random.nextInt(size);
  }

  public long getRandomLong(int size) {
    return random.nextLong();
  }

  public boolean getRandomBoolean() {
    return random.nextBoolean();
  }

  public double getRandomDouble(int size) {
    return random.nextDouble() * size;
    }

  public LocalDateTime getRandomLocalDateTime() {
    return LocalDateTime.now().minusDays(random.nextInt(365)).withNano(0);
  }

  private byte[] getRandomByte() {
    int randomByteLength = random.nextInt(0, 200);
    byte[] array = new byte[randomByteLength];
    random.nextBytes(array);
    return array;
  }

  public <T> T getRandomEnum(Class<T> clazz) {
    T[] enumConstants = clazz.getEnumConstants();
    return enumConstants[random.nextInt(enumConstants.length)];
  }




  private static Charset getCharset() {
    Set<String> charset = Charset.availableCharsets().keySet();
    List<String> list = new ArrayList<>(charset);
    int randomIndex = random.nextInt(list.size());
    return Charset.forName(list.get(randomIndex));
  }

}
