package com.ggb.graduationgoodbye.global.util.randomValue;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnInfo {

  private final DatabaseMetaData metaData;
  private final String tableName;
  private final String columnName;

  private ResultSet getColumnResultSet() {
    try {
      return metaData.getColumns(null, null, tableName, columnName);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Integer getColumnSize() throws SQLException {
    try (ResultSet columns = getColumnResultSet()) {
      if (columns != null && columns.next()) {
        return columns.getInt("COLUMN_SIZE");
      }
    }
    return 0;
  }

  public boolean isColumnNullable() throws SQLException {
    try (ResultSet columns = getColumnResultSet()) {
      if (columns != null && columns.next()) {
        int nullable = columns.getInt("NULLABLE");
        return nullable == DatabaseMetaData.columnNullable;
      }
    }
    return false;
  }
}
