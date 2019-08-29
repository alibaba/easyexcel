package com.alibaba.excel.util;

import org.junit.Assert;
import org.junit.Test;

public class TypeUtilTest {

  @Test
  public void formatFloat0InputNotNullPositiveOutputNotNull() {
    Assert.assertEquals("/", TypeUtil.formatFloat0("/", 2));
  }

  @Test
  public void getSimpleDateFormatDateInputNullNotNullOutputNull() {
    Assert.assertNull(TypeUtil.getSimpleDateFormatDate(null, "?"));
  }

  @Test
  public void isNumInputNegativeOutputTrue() {
    Assert.assertTrue(TypeUtil.isNum(-2.0f));
  }

  @Test
  public void isNumInputNullOutputFalse() {
    Assert.assertFalse(TypeUtil.isNum(null));
  }

  @Test
  public void isNumInputNullOutputFalse2() {
    Assert.assertFalse(TypeUtil.isNum(null));
  }
}
