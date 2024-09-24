package com.ggb.graduationgoodbye.global.util.randomValue;

import lombok.Getter;

@Getter
public class ColumnDetail {

  private final String name;
  private final int columnSize;
  private final boolean isNullable;


  public ColumnDetail(String name, int columnSize, boolean isNullable) {
    this.name = formatColumnName(name);
    this.columnSize = columnSize;
    this.isNullable = isNullable;
  }

  private String formatColumnName(String columnName) {
    return columnName.toUpperCase().replace("_", "");
  }

}
