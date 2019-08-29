package com.alibaba.excel.metadata;

import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BaseRowModelTest {

  @Test
  public void getCellStyleMapOutput0() {
    final BaseRowModel baseRowModel = new BaseRowModel();
    final Map<Integer, CellStyle> actual = baseRowModel.getCellStyleMap();
    final HashMap<Integer, CellStyle> hashMap = new HashMap<Integer, CellStyle>();
    Assert.assertEquals(hashMap, actual);
  }

  @Test
  public void getStyleInputNegativeOutputNull() {
  	final HashMap<Integer, CellStyle> hashMap = new HashMap<Integer, CellStyle>();
    hashMap.put(null, null);
    new BaseRowModel().setCellStyleMap(hashMap);
    Assert.assertNull(new BaseRowModel().getStyle(-999998));
  }

  @Test
  public void setCellStyleMapInputNotNullOutputVoid() {
  	final HashMap<Integer, CellStyle> cellStyleMap = new HashMap<Integer, CellStyle>();
    new BaseRowModel().setCellStyleMap(cellStyleMap);
    final HashMap<Integer, CellStyle> hashMap = new HashMap<Integer, CellStyle>();
    Assert.assertEquals(hashMap, new BaseRowModel().getCellStyleMap());
  }
}
