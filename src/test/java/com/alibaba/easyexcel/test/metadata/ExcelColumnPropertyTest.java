package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

public class ExcelColumnPropertyTest {

  @Test
  public void compareToInputNotNullOutputZero() {
	  Assert.assertEquals(0,
			  new ExcelColumnProperty().compareTo(new ExcelColumnProperty()));
  }
}
