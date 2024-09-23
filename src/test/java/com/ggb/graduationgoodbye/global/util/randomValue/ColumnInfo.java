package com.ggb.graduationgoodbye.global.util.randomValue;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnInfo {

  private final DatabaseMetaData metaData;
  private final String tableName;
  private final List<ColumnDetail> columnDetails = new ArrayList<>();

  public ColumnInfo(DatabaseMetaData metaData, String tableName) throws SQLException {
    this.metaData = metaData;
    this.tableName = tableName;
    loadColumnDetails();
  }

  private void loadColumnDetails() throws SQLException {
    try (ResultSet columns = metaData.getColumns(null, null, tableName, "%")) {
      while (columns.next()) {
        String columnName = columns.getString("COLUMN_NAME");
        int columnSize = columns.getInt("COLUMN_SIZE");
        boolean isNullable = isColumnNullable(columns);

        columnDetails.add(new ColumnDetail(columnName, columnSize, isNullable));
      }
    }
  }

  private boolean isColumnNullable(ResultSet columns) throws SQLException {
    int nullable = columns.getInt("NULLABLE");
    return nullable == DatabaseMetaData.columnNullable;
  }


  public Integer getColumnSize(String columnName) {
    return getColumnDetail(columnName).map(ColumnDetail::getColumnSize).orElse(0);
  }

  public Boolean isColumnNullable(String columnName) {
    return getColumnDetail(columnName).map(ColumnDetail::isNullable).orElse(false);
  }

  private java.util.Optional<ColumnDetail> getColumnDetail(String columnName) {
    return columnDetails.stream()
        .filter(detail -> detail.getName().equalsIgnoreCase(columnName))
        .findFirst();
  }
}
