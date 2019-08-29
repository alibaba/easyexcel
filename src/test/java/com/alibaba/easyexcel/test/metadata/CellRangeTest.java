package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

public class CellRangeTest {

  @Test
  public void getFirstColOutputPositive() {
    final CellRange cellRange = new CellRange(2, 2, 2, 475716);
    Assert.assertEquals(2, cellRange.getFirstCol());
  }

  @Test
  public void getFirstRowOutputPositive() {
    final CellRange cellRange = new CellRange(2, 2, 4, 475716);
    Assert.assertEquals(2, cellRange.getFirstRow());
  }

  @Test
  public void getLastColOutputPositive() {
    final CellRange cellRange = new CellRange(2, 2, 4, 2);
    Assert.assertEquals(2, cellRange.getLastCol());
  }

  @Test
  public void getLastRowOutputPositive() {
    final CellRange cellRange = new CellRange(2, 2, 345156, 2);
    Assert.assertEquals(2, cellRange.getLastRow());
  }

  @Test
  public void setFirstRowInputPositiveOutputVoid() {
    final CellRange cellRange = new CellRange(344656, 2, 2, 4);
    cellRange.setFirstRow(2);
    Assert.assertEquals(2, cellRange.getFirstRow());
  }

  @Test
  public void setLastColInputPositiveOutputVoid() {
    final CellRange cellRange = new CellRange(344656, 2, 2, 4);
    cellRange.setLastCol(2);
    Assert.assertEquals(2, cellRange.getLastCol());
  }
}
