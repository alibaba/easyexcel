package com.alibaba.excel.util;

import com.alibaba.excel.metadata.IndexValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class IndexValueConverterTest {

  @Test
  public void converterInputNotNullOutput0() {
	  Assert.assertEquals(new ArrayList<String>(),
			  IndexValueConverter.converter(new ArrayList<IndexValue>()));
  }
}
