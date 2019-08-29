package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

public class TableStyleTest {

  @Test
  public void getTableContentBackGroundColorOutputNull() {
	  Assert.assertNull(new TableStyle().getTableContentBackGroundColor());
  }

  @Test
  public void getTableContentFontOutputNull() {
	  Assert.assertNull(new TableStyle().getTableContentFont());
  }

  @Test
  public void getTableHeadBackGroundColorOutputNull() {
	  Assert.assertNull(new TableStyle().getTableHeadBackGroundColor());
  }

  @Test
  public void getTableHeadFontOutputNull() {
	  Assert.assertNull(new TableStyle().getTableHeadFont());
  }
}
