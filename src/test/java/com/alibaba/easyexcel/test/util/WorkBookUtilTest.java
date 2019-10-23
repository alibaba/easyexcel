package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.WorkBookUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class WorkBookUtilTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  @Rule public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover
  @Test
  public void createCellInputNullZeroNotNullOutputNullPointerException() {

    // Arrange
    final Row arg0 = null;
    final int arg1 = 0;
    final String arg2 = "aaaaa";

    // Act
    thrown.expect(NullPointerException.class);
    WorkBookUtil.createCell(arg0, arg1, arg2);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void createCellInputNullZeroNullNotNullOutputNullPointerException() {

    // Arrange
    final Row arg0 = null;
    final int arg1 = 0;
    final CellStyle arg2 = null;
    final String arg3 = "aaaaa";

    // Act
    thrown.expect(NullPointerException.class);
    WorkBookUtil.createCell(arg0, arg1, arg2, arg3);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void createCellInputNullZeroNullOutputNullPointerException() {

    // Arrange
    final Row arg0 = null;
    final int arg1 = 0;
    final CellStyle arg2 = null;

    // Act
    thrown.expect(NullPointerException.class);
    WorkBookUtil.createCell(arg0, arg1, arg2);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void createCellInputNullZeroOutputNullPointerException() {

    // Arrange
    final Row arg0 = null;
    final int arg1 = 0;

    // Act
    thrown.expect(NullPointerException.class);
    WorkBookUtil.createCell(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void createRowInputNullZeroOutputNullPointerException() {

    // Arrange
    final Sheet arg0 = null;
    final int arg1 = 0;

    // Act
    thrown.expect(NullPointerException.class);
    WorkBookUtil.createRow(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }
}
