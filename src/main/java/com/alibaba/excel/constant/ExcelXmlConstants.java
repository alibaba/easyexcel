package com.alibaba.excel.constant;

/**
 * All rights Reserved, Designed By DSL
 *
 * @author DSL
 * @version V1.0
 * @package com.alibaba.excel.constant
 * @className ExcelXmlConstants
 * @date 2019/4/2 15:54
 * @description Constant ENUM
 */
public enum ExcelXmlConstants {
  DIMENSION("dimension"),
  DIMENSION_REF("ref"),
  POSITION("r"),
  ROW_TAG("row"),
  CELL_TAG("c"),
  CELL_VALUE_TAG("v"),
  CELL_VALUE_TAG_1("t");

  private String value;

  ExcelXmlConstants(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
