package com.alibaba.excel.metadata;

import org.junit.Assert;
import org.junit.Test;

public class IndexValueTest {

  @Test
  public void getV_indexOutputNotNull() {
	  Assert.assertEquals("/", new IndexValue("/",
			  "/").getV_index());
  }

  @Test
  public void getV_valueOutputNotNull() {
	  Assert.assertEquals("/", new IndexValue("/",
			  "/").getV_value());
  }

  @Test
  public void toStringOutputNotNull() {
	  Assert.assertEquals("IndexValue [v_index=/, v_value=/]",
			  new IndexValue("/", "/").toString());
  }
}
