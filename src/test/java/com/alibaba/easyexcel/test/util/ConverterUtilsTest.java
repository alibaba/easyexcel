package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.util.ConverterUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.util.HashMap;

public class ConverterUtilsTest {


  @Rule public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover
  @Test
  public void convertToJavaObjectInputNotNullNullNotNullNotNullNotNullOutputNullPointerException() throws NullPointerException {

    // Arrange
    final byte[] myByteArray = {(short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0};
    final CellData arg0 = new CellData(myByteArray);
    final Class arg1 = null;
    final ExcelContentProperty arg2 = new ExcelContentProperty();
    final HashMap<String, Converter> arg3 = new HashMap<String, Converter>();
    final AutoConverter autoConverter = new AutoConverter();
    arg3.put(" error ", autoConverter);
    final GlobalConfiguration arg4 = new GlobalConfiguration();

    // Act
    thrown.expect(NullPointerException.class);
    ConverterUtils.convertToJavaObject(arg0, arg1, arg2, arg3, arg4);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover
  @Test
  public void convertToStringMapInputNotNullNullOutputNullPointerException() throws NullPointerException {

    // Arrange
    final HashMap<Integer, CellData> arg0 = new HashMap<Integer, CellData>();
    final Integer integer = new Integer(1);
    final byte[] myByteArray = {(short)0,  (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0,  (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0,  (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)10, (short)0, (short)0, (short)0, (short)0, (short)0};
    final CellData cellData = new CellData(myByteArray);
    arg0.put(integer, cellData);
    final ReadHolder arg1 = null;

    // Act
    thrown.expect(NullPointerException.class);
    ConverterUtils.convertToStringMap(arg0, arg1);

    // The method is not expected to return due to exception thrown
  }
}
