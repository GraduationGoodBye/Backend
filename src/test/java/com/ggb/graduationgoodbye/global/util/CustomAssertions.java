package com.ggb.graduationgoodbye.global.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.lang.reflect.Field;
import org.apache.coyote.BadRequestException;

public class CustomAssertions {

  private static void assertSameClass(Object obj1, Object obj2) {
    if (!obj1.getClass().equals(obj2.getClass())) {
      throw new AssertionError();
    }
  }

  private static void assertFieldsEqual(Field field, Object obj1, Object obj2) throws IllegalAccessException {
    field.setAccessible(true);
    Object value1 = field.get(obj1);
    Object value2 = field.get(obj2);

    assertEquals(value1, value2);
  }

  private static void printFieldComparison(String fieldName, Object value, Object value2) {
    StringBuilder sb = new StringBuilder();
    sb.append("Field: ").append(fieldName).append("\n")
        .append("실제 : ").append(value).append("\n")
        .append("테스트 : ").append(value2).append("\n")
        .append("-------------");

    System.out.println(sb);
  }



  public static void customAssertEquals(Object obj1, Object obj2) {
    try {

      assertSameClass(obj1, obj2);

      Field[] fields = obj1.getClass().getDeclaredFields();
      for (Field field : fields) {
        assertFieldsEqual(field, obj1, obj2);
        printFieldComparison(field.getName() , field.get(obj1), field.get(obj2));
      }


    } catch (Exception e) {
      throw new AssertionError("customAssertEquals failed: " + e.getMessage());
    }
  }
}
