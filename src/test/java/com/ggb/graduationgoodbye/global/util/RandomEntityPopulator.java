package com.ggb.graduationgoodbye.global.util;


import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class RandomEntityPopulator {

  public static Object setRandomValues(Class<?> clazz) {
    try {
      Object response = clazz.getDeclaredConstructor().newInstance();
      Field[] fields = clazz.getDeclaredFields();

      for (Field field : fields) {
        field.setAccessible(true);

        if (field.getType() == String.class) {
          field.set(response, RandomValueGenerator.getRandomString());
        } else if (field.getType() == Integer.class || field.getType() == int.class) {
          field.set(response, RandomValueGenerator.getRandomInt());
        } else if (field.getType() == Long.class || field.getType() == long.class) {
          field.set(response, RandomValueGenerator.getRandomLong());
        } else if (field.getType() == Double.class || field.getType() == double.class) {
          field.set(response, RandomValueGenerator.getRandomDouble());
        } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
          field.set(response, RandomValueGenerator.getRandomBoolean());
        } else if (field.getType() == LocalDateTime.class) {
          field.set(response, RandomValueGenerator.getRandomLocalDateTime());
        } else if (Enum.class.isAssignableFrom(field.getType())) {
          field.set(response, RandomValueGenerator.getRandomEnum(field.getType()));
        }
      }

      return response;

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

}
