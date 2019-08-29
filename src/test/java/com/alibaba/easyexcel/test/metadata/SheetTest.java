package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class SheetTest {

  @Test
  public void constructorInputPositivePositiveOutputVoid() {
  	final HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    Assert.assertEquals(hashMap, new Sheet(2, 2).getColumnWidthMap());
    Assert.assertEquals(2, new Sheet(2, 2).getHeadLineMun());
  }

  @Test
  public void constructorInputZeroZeroNullNullNullOutputVoid() {
  	final Sheet sheet = new Sheet(0, 0, null, null, null);
  	Assert.assertEquals(new HashMap<Integer, Integer>(), sheet.getColumnWidthMap());
    Assert.assertFalse(sheet.getAutoWidth());
  }

  @Test
  public void getAutoWidthOutputFalse() {
  	Assert.assertFalse(new Sheet(2).getAutoWidth());
  }

  @Test
  public void getColumnWidthMapOutput0() {
  	final HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    Assert.assertEquals(hashMap, new Sheet(2).getColumnWidthMap());
  }

  @Test
  public void getHeadOutputNull() {
  	Assert.assertNull(new Sheet(2).getHead());
  }

  @Test
  public void getSheetNameOutputNull() {
  	Assert.assertNull(new Sheet(2).getSheetName());
  }

  @Test
  public void getStartRowOutputZero() {
  	Assert.assertEquals(0, new Sheet(2).getStartRow());
  }

  @Test
  public void getTableStyleOutputNull() {
  	Assert.assertNull(new Sheet(2).getTableStyle());
  }

  @Test
  public void setColumnWidthMapInputNotNullOutputVoid() {
  	new Sheet(2).setColumnWidthMap(new HashMap<Integer, Integer>());
    final HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    Assert.assertEquals(hashMap, new Sheet(2).getColumnWidthMap());
  }
}
