package com.alibaba.excel;

import com.alibaba.excel.parameter.GenerateParam;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class 	ExcelWriterTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void constructorInputNullOutputNullPointerException() {
    final GenerateParam generateParam = null;
    thrown.expect(NullPointerException.class);
    final ExcelWriter excelWriter = new ExcelWriter(generateParam);
  }
}
