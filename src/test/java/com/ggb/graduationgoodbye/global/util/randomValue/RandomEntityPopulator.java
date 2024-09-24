package com.ggb.graduationgoodbye.global.util.randomValue;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class RandomEntityPopulator {

  private final SqlSessionFactory sqlSessionFactory;
  private final Map<String, Object> customValues = new HashMap<>();

  private final ColumnInfo columnInfo;


  Random random = new Random();

  public RandomEntityPopulator(SqlSessionFactory sqlSessionFactory, String tableName) {
    this.sqlSessionFactory = sqlSessionFactory;

    try (SqlSession session = sqlSessionFactory.openSession()) {
      Connection connection = session.getConnection();
      DatabaseMetaData metaData = connection.getMetaData();
      columnInfo = new ColumnInfo(metaData, tableName);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize ColumnInfo", e);
    }
  }

  public Object getPopulatedEntity(Class<?> clazz) {
    try {

      Object response = clazz.getDeclaredConstructor().newInstance();
      populateFields(response, clazz.getDeclaredFields(), columnInfo);

      return response;
    } catch (Exception e) {
      throw new RuntimeException("getPopulatedEntity ", e);
    }
  }

  public RandomEntityPopulator setValue(String fieldName, Object value) {
    customValues.put(fieldName.toUpperCase(), value);
    return this;
  }

  private void populateFields(Object entity, Field[] fields
      , ColumnInfo columnInfo) throws Exception {

    for (Field field : fields) {
      field.setAccessible(true);
      String columnName = field.getName().toUpperCase();

      if (customValues.containsKey(columnName)) {
        field.set(entity, customValues.get(columnName));
      } else {
        Object value = generateRandomValue(field, columnInfo, columnName);
        field.set(entity, value);
      }
    }
  }


  public Object generateRandomValue(Field field, ColumnInfo columnInfo
      , String columnName) {

    int columnSize = columnInfo.getColumnSize(columnName);
    int size = columnSize > 0 ? random.nextInt(columnSize) : 0;
    boolean nullable = columnInfo.isColumnNullable(columnName);

    if (size == 0 && nullable && !Enum.class.isAssignableFrom(field.getType())) {
      return null;
    }

    return generateValueForType(field.getType(), size);
  }


  private Object generateValueForType(Class<?> fieldType, int size) {
    if (String.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomString(size);
    } else if (Integer.class.isAssignableFrom(fieldType)
        || int.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomInt(size);
    } else if (Long.class.isAssignableFrom(fieldType)
        || long.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomLong(size);
    } else if (Double.class.isAssignableFrom(fieldType)
        || double.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomDouble(size);
    } else if (Boolean.class.isAssignableFrom(fieldType)
        || boolean.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomBoolean();
    } else if (LocalDateTime.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomLocalDateTime();
    } else if (Enum.class.isAssignableFrom(fieldType)) {
      return RandomValueGenerator.getRandomEnum(fieldType);
    } else {
      return null;
    }
  }


}
