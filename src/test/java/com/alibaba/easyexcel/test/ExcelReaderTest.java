package com.alibaba.excel;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.modelbuild.ModelBuildEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;

public class ExcelReaderTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void constructorInputNullNullNullNotNullFalseOutputIllegalArgumentException() {
  	thrown.expect(IllegalArgumentException.class);
    final ExcelReader excelReader =
        new ExcelReader(null, null, null, new ModelBuildEventListener(), false);
  }

  @Test
  public void constructorInputNullNullNullNullFalseOutputIllegalArgumentException() {
  	thrown.expect(IllegalArgumentException.class);
    final ExcelReader excelReader =
        new ExcelReader(null, null, null, null, false);
  }

  @Test
  public void constructorInputNullNullNullNullOutputIllegalArgumentException() {
  	thrown.expect(IllegalArgumentException.class);
    final ExcelReader excelReader =
        new ExcelReader(null, null, null, null);
  }
}
