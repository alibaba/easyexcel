package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  @Rule public final ExpectedException thrown = ExpectedException.none();


  // Test written by Diffblue Cover
  @Test
  public void openInputStreamInputNotNullOutputFileNotFoundException() throws IOException {

    // Arrange
    final File arg0 = new File("' cannot be read");

    // Act
    thrown.expect(FileNotFoundException.class);
    FileUtils.openInputStream(arg0);

    // The method is not expected to return due to exception thrown
  }
}
