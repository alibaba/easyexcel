package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

public class FontTest {

  @Test
  public void getFontHeightInPointsOutputZero() {
  	Assert.assertEquals((short)0, new Font().getFontHeightInPoints());
  }

  @Test
  public void getFontNameOutputNull() {
  	Assert.assertNull(new Font().getFontName());
  }

  @Test
  public void isBoldOutputFalse() {
  	Assert.assertFalse(new Font().isBold());
  }
}
