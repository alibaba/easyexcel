package com.alibaba.excel.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WorkBookUtilTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void createOrGetSheetInputNullNullOutputRuntimeException() {
    thrown.expect(RuntimeException.class);
    WorkBookUtil.createOrGetSheet(null, null);
  }
}
