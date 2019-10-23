package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.NumberFormatProperty;
import com.alibaba.excel.util.NumberUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.text.ParseException;

public class NumberUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover
  @Test
  public void formatInputNotNullNotNullOutputNotNull() {

    // Arrange
    final Integer arg0 = new Integer(1);
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    final String actual = NumberUtils.format(arg0, arg1);

    // Assert result
    Assert.assertEquals("1", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void formatToCellDataInputNegativeNotNullOutputNotNull() {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act
    final CellData actual = NumberUtils.formatToCellData(-10000000, contentProperty);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertFalse(actual.getFormula());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("-10000000", actual.getStringValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertNull(actual.getBooleanValue());
  }

  // Test written by Diffblue Cover.
  @Test
  public void formatToCellDataInputNegativeNotNullOutputNotNull1() {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act
    final CellData actual = NumberUtils.formatToCellData(-1000, contentProperty);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertFalse(actual.getFormula());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("-1000", actual.getStringValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertNull(actual.getBooleanValue());
  }

  // Test written by Diffblue Cover.
  @Test
  public void formatToCellDataInputNegativeNotNullOutputNotNull2() {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();
    final NumberFormatProperty numberFormatProperty = new NumberFormatProperty("?", null);
    numberFormatProperty.setFormat("");
    contentProperty.setNumberFormatProperty(numberFormatProperty);

    // Act
    final CellData actual = NumberUtils.formatToCellData(-10000000, contentProperty);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertFalse(actual.getFormula());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("-10000000", actual.getStringValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertNull(actual.getBooleanValue());
  }

  // Test written by Diffblue Cover.
  @Test
  public void formatToCellDataInputNegativeNullOutputNotNull() {

    // Act
    final CellData actual = NumberUtils.formatToCellData(-10000000, null);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertFalse(actual.getFormula());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("-10000000", actual.getStringValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertNull(actual.getBooleanValue());
  }

  // Test written by Diffblue Cover.
  @Test
  public void formatToCellDataInputNegativeNullOutputNotNull1() {

    // Act
    final CellData actual = NumberUtils.formatToCellData(-1000, null);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertFalse(actual.getFormula());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("-1000", actual.getStringValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertNull(actual.getBooleanValue());
  }

  // Test written by Diffblue Cover
  @Test
  public void formatToCellDataInputNotNullNotNullOutputNotNull() {

    // Arrange
    final Integer arg0 = new Integer(1);
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    final CellData actual = NumberUtils.formatToCellData(arg0, arg1);

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNotNull(actual.getFormula());
    Assert.assertNull(actual.getFormulaValue());
    Assert.assertNull(actual.getImageValue());
    Assert.assertNull(actual.getDataFormat());
    Assert.assertNull(actual.getDataFormatString());
    Assert.assertEquals("1", actual.getStringValue());
    Assert.assertNull(actual.getBooleanValue());
    Assert.assertNull(actual.getNumberValue());
    Assert.assertEquals(CellDataTypeEnum.STRING, actual.getType());
  }

  // Test written by Diffblue Cover
  @Test
  public void parseBigDecimalInputNotNullNotNullOutputNumberFormatException()
      throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseBigDecimal(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void parseByteInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseByte(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseByteInputNotNullNotNullOutputNumberFormatException1() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseByte("99971890", contentProperty);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseByteInputNotNullNotNullOutputPositive() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act and Assert result
    Assert.assertEquals(new Byte((byte)7), NumberUtils.parseByte("7", contentProperty));
  }

  // Test written by Diffblue Cover
  @Test
  public void parseDoubleInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseDouble(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void parseFloatInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseFloat(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void parseIntegerInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseInteger(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseIntegerInputNotNullNotNullOutputPositive() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act and Assert result
    Assert.assertEquals(new Integer(5), NumberUtils.parseInteger("5", contentProperty));
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseIntegerInputNotNullNotNullOutputPositive1() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();
    final NumberFormatProperty numberFormatProperty = new NumberFormatProperty("", null);
    contentProperty.setNumberFormatProperty(numberFormatProperty);

    // Act and Assert result
    Assert.assertEquals(new Integer(7), NumberUtils.parseInteger("7", contentProperty));
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseIntegerInputNotNullNullOutputPositive() throws ParseException {

    // Act and Assert result
    Assert.assertEquals(new Integer(7), NumberUtils.parseInteger("7", null));
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseLongInputNotNullNotNullOutputNegative() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act and Assert result
    Assert.assertEquals(new Long(-9L), NumberUtils.parseLong("-9", contentProperty));
  }

  // Test written by Diffblue Cover
  @Test
  public void parseLongInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseLong(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void parseShortInputNotNullNotNullOutputNumberFormatException() throws ParseException {

    // Arrange
    final String arg0 = "aaaaa";
    final ExcelContentProperty arg1 = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseShort(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseShortInputNotNullNotNullOutputNumberFormatException1() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act
    thrown.expect(NumberFormatException.class);
    NumberUtils.parseShort("99975", contentProperty);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseShortInputNotNullNotNullOutputZero() throws ParseException {

    // Arrange
    final ExcelContentProperty contentProperty = new ExcelContentProperty();

    // Act and Assert result
    Assert.assertEquals(new Short((short)0), NumberUtils.parseShort("0", contentProperty));
  }
}
