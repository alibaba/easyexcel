package com.alibaba.excel.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author jipengfei */
public class Sheet {

  /** Number of rows to start reading */
  private int headLineNum;

  /** Starting from 1 */
  private int sheetNo;

  /** */
  private String sheetName;

  /** */
  private Class<? extends BaseRowModel> clazz;

  /** */
  private List<List<String>> head;

  /** */
  private TableStyle tableStyle;

  /** column with */
  private Map<Integer, Integer> columnWidthMap = new HashMap<Integer, Integer>();

  /** */
  private Boolean autoWidth = Boolean.FALSE;

  /** */
  private int startRow = 0;

  public Sheet(int sheetNo) {
    this.sheetNo = sheetNo;
  }

  /**
   * @param sheetNo which sheet
   * @param headLineNum number of rows to start reading
   */
  public Sheet(int sheetNo, int headLineNum) {
    this.sheetNo = sheetNo;
    this.headLineNum = headLineNum;
  }

  /**
   * @param sheetNo witch sheet
   * @param headLineNum number of rows to start reading
   * @param clazz model bean who extends {@link BaseRowModel}
   */
  public Sheet(int sheetNo, int headLineNum, Class<? extends BaseRowModel> clazz) {
    this.sheetNo = sheetNo;
    this.headLineNum = headLineNum;
    this.clazz = clazz;
  }

  public Sheet(
      int sheetNo,
      int headLineNum,
      Class<? extends BaseRowModel> clazz,
      String sheetName,
      List<List<String>> head) {
    this.sheetNo = sheetNo;
    this.clazz = clazz;
    this.headLineNum = headLineNum;
    this.sheetName = sheetName;
    this.head = head;
  }

  public List<List<String>> getHead() {
    return head;
  }

  public void setHead(List<List<String>> head) {
    this.head = head;
  }

  public Class<? extends BaseRowModel> getClazz() {
    return clazz;
  }

  public void setClazz(Class<? extends BaseRowModel> clazz) {
    this.clazz = clazz;
    if (headLineNum == 0) {
      this.headLineNum = 1;
    }
  }

  public int getHeadLineNum() {
    return headLineNum;
  }

  public void setHeadLineNum(int headLineNum) {
    this.headLineNum = headLineNum;
  }

  public int getSheetNo() {
    return sheetNo;
  }

  public void setSheetNo(int sheetNo) {
    this.sheetNo = sheetNo;
  }

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public TableStyle getTableStyle() {
    return tableStyle;
  }

  public void setTableStyle(TableStyle tableStyle) {
    this.tableStyle = tableStyle;
  }

  public Map<Integer, Integer> getColumnWidthMap() {
    return columnWidthMap;
  }

  public void setColumnWidthMap(Map<Integer, Integer> columnWidthMap) {
    this.columnWidthMap = columnWidthMap;
  }

  @Override
  public String toString() {
    return "Sheet{"
        + "headLineNum="
        + headLineNum
        + ", sheetNo="
        + sheetNo
        + ", sheetName='"
        + sheetName
        + '\''
        + ", clazz="
        + clazz
        + ", head="
        + head
        + ", tableStyle="
        + tableStyle
        + ", columnWidthMap="
        + columnWidthMap
        + '}';
  }

  public Boolean getAutoWidth() {
    return autoWidth;
  }

  public void setAutoWidth(Boolean autoWidth) {
    this.autoWidth = autoWidth;
  }

  public int getStartRow() {
    return startRow;
  }

  public void setStartRow(int startRow) {
    this.startRow = startRow;
  }
}
