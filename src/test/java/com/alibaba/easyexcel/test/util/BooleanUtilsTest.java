package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.BooleanUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class BooleanUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  // Test written by Diffblue Cover.
  @Test
  public void valueOfInputNotNullOutputFalse() {

    // Act and Assert result
    Assert.assertFalse(BooleanUtils.valueOf("foo"));
  }

  // Test written by Diffblue Cover.
  @Test
  public void valueOfInputNotNullOutputTrue() {

    // Act and Assert result
    Assert.assertTrue(BooleanUtils.valueOf("1"));
  }
}
