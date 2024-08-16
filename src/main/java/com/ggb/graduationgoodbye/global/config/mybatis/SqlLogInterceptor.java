package com.ggb.graduationgoodbye.global.config.mybatis;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Intercepts(value = {
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
        Object.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class})}
)
public class SqlLogInterceptor implements Interceptor {

  private static final String LOG_FORMAT = "\n\t\t";

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
    Object paramObj = invocation.getArgs()[1];
    BoundSql boundSql = statement.getBoundSql(paramObj);
    String paramSql = getParamBindSQL(boundSql);

    log.debug(LOG_FORMAT + "mapper: {}"
        + LOG_FORMAT + "method: {}"
        + LOG_FORMAT + "SQL: {}", statement.getResource(), statement.getId(), paramSql);

    return invocation.proceed();
  }

  private String getParamBindSQL(BoundSql boundSql) {
    Object parameterObject = boundSql.getParameterObject();
    StringBuilder sqlStringBuilder = new StringBuilder(boundSql.getSql());

    // stringBuilder 파라미터 replace 처리
    BiConsumer<StringBuilder, Object> sqlObjectReplace = (sqlSb, value) -> {

      int questionIdx = sqlSb.indexOf("?");

      if (questionIdx == -1) {
        return;
      }

      if (value == null) {
        sqlSb.replace(questionIdx, questionIdx + 1, "null");
      } else if (value instanceof String || value instanceof LocalDate
          || value instanceof LocalDateTime || value instanceof Enum<?>) {
        sqlSb.replace(questionIdx, questionIdx + 1,
            "'" + value.toString() + "'");
      } else {
        sqlSb.replace(questionIdx, questionIdx + 1, value.toString());
      }
    };

    if (parameterObject == null) {
      sqlObjectReplace.accept(sqlStringBuilder, null);
    } else {
      if (parameterObject instanceof Integer || parameterObject instanceof Long
          || parameterObject instanceof Float || parameterObject instanceof Double
          || parameterObject instanceof String) {
        sqlObjectReplace.accept(sqlStringBuilder, parameterObject);
      } else if (parameterObject instanceof Map) {
        Map paramterObjectMap = (Map) parameterObject;
        List<ParameterMapping> paramMappings = boundSql.getParameterMappings();

        for (ParameterMapping parameterMapping : paramMappings) {
          String propertyKey = parameterMapping.getProperty();
          try {
            Object paramValue = null;
            if (boundSql.hasAdditionalParameter(propertyKey)) {
              // 동적 SQL로 인해 __frch_item_0 같은 파라미터가 생성되어 적재됨, additionalParameter로 획득
              paramValue = boundSql.getAdditionalParameter(propertyKey);
            } else {
              paramValue = paramterObjectMap.get(propertyKey);
            }

            sqlObjectReplace.accept(sqlStringBuilder, paramValue);
          } catch (Exception e) {
            sqlObjectReplace.accept(sqlStringBuilder, "[cannot binding : " + propertyKey + "]");
          }

        }
      } else {
        List<ParameterMapping> paramMappings = boundSql.getParameterMappings();
        Class<? extends Object> paramClass = parameterObject.getClass();

        for (ParameterMapping parameterMapping : paramMappings) {
          String propertyKey = parameterMapping.getProperty();

          try {
            Object paramValue = null;
            if (boundSql.hasAdditionalParameter(propertyKey)) {
              // 동적 SQL로 인해 __frch_item_0 같은 파라미터가 생성되어 적재됨, additionalParameter로 획득
              paramValue = boundSql.getAdditionalParameter(propertyKey);
            } else {
              Field field = ReflectionUtils.findField(paramClass, propertyKey);
              field.setAccessible(true);
              paramValue = field.get(parameterObject);
            }

            sqlObjectReplace.accept(sqlStringBuilder, paramValue);
          } catch (Exception e) {
            sqlObjectReplace.accept(sqlStringBuilder, "[cannot binding : " + propertyKey + "]");
          }
        }
      }
    }
    return sqlStringBuilder.toString().replaceAll("([\\r\\n\\s]){2,}([\\r\\n])+", "\n");
  }

  @Override
  public Object plugin(Object target) {
    return Interceptor.super.plugin(target);
  }

  @Override
  public void setProperties(Properties properties) {
    Interceptor.super.setProperties(properties);
  }
}
