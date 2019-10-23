package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.PositionUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class PositionUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  @Rule public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover
  @Test
  public void getColInputNotNullOutputPositive() {

    // Arrange
    final String arg0 = "aaaaa";

    // Act
    final int actual = PositionUtils.getCol(arg0);

    // Assert result
    Assert.assertEquals(15683414, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getColInputNullOutputNegative() {

    // Act and Assert result
    Assert.assertEquals(-1, PositionUtils.getCol(null));
  }

  // Test written by Diffblue Cover
  @Test
  public void getRowByRowTagtInputNotNullOutputNumberFormatException() {

    // Arrange
    final String arg0 = "aaaaa";

    // Act
    thrown.expect(NumberFormatException.class);
    PositionUtils.getRowByRowTagt(arg0);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void getRowByRowTagtInputNotNullOutputZero() {

    // Act and Assert result
    Assert.assertEquals(0, PositionUtils.getRowByRowTagt("1"));
  }

  // Test written by Diffblue Cover.
  @Test
  public void getRowByRowTagtInputNullOutputZero() {

    // Act and Assert result
    Assert.assertEquals(0, PositionUtils.getRowByRowTagt(null));
  }

  // Test written by Diffblue Cover
  @Test
  public void getRowInputNotNullOutputNumberFormatException() {

    // Arrange
    final String arg0 = "aaaaa";

    // Act
    thrown.expect(NumberFormatException.class);
    PositionUtils.getRow(arg0);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void getRowInputNullOutputZero() {

    // Act and Assert result
    Assert.assertEquals(0, PositionUtils.getRow(null));
  }
}
