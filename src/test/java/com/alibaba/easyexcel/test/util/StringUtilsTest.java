package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.StringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class StringUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  // Test written by Diffblue Cover
  @Test
  public void isEmptyInputNotNullOutputTrue() {

    // Arrange
    final Object arg0 = "";

    // Act
    final boolean actual = StringUtils.isEmpty(arg0);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNullOutputTrue() {

    // Act and Assert result
    Assert.assertTrue(StringUtils.isEmpty(null));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputZeroOutputFalse() {

    // Act and Assert result
    Assert.assertFalse(StringUtils.isEmpty(0));
  }
}
