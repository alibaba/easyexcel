package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.IoUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

public class IoUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  @Rule public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover
  @Test
  public void copyInputNotNullNotNullOutputPositive() throws IOException {

    // Arrange
    final byte[] myByteArray = {(short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0};
    final ByteArrayInputStream arg0 = new ByteArrayInputStream(myByteArray);
    final ByteArrayOutputStream arg1 = new ByteArrayOutputStream();

    // Act
    final int actual = IoUtils.copy(arg0, arg1);

    // Assert side effects
    Assert.assertNotNull(arg1);
    Assert.assertEquals(24, ((ByteArrayOutputStream)arg1).size());
    Assert.assertNotNull(arg0);

    // Assert result
    Assert.assertEquals(24, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void copyInputNotNullNotNullOutputPositive1() throws IOException {

    // Arrange
    final byte[] myByteArray = {(byte)2, (byte)0};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);
    final ByteArrayOutputStream output = new ByteArrayOutputStream();

    // Act and Assert result
    Assert.assertEquals(2, IoUtils.copy(input, output));

    // Assert side effects
    Assert.assertNotNull(input);
    Assert.assertNotNull(output);
  }

  // Test written by Diffblue Cover.
  @Test
  public void copyInputNotNullNotNullOutputZero() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);
    final ByteArrayOutputStream output = new ByteArrayOutputStream();

    // Act and Assert result
    Assert.assertEquals(0, IoUtils.copy(input, output));
  }

  // Test written by Diffblue Cover.
  @Test
  public void copyInputNotNullNotNullOutputZero1() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input =
        new ByteArrayInputStream(myByteArray, 1908932608, 1312292864);
    input.mark(0);
    final ByteArrayOutputStream output = new ByteArrayOutputStream(65535);

    // Act and Assert result
    Assert.assertEquals(0, IoUtils.copy(input, output));
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullNegativeOutputIllegalArgumentException() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);

    // Act
    thrown.expect(IllegalArgumentException.class);
    IoUtils.toByteArray(input, -2147446782);

    // The method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullOutput0() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);

    // Act
    final byte[] actual = IoUtils.toByteArray(input);

    // Assert result
    Assert.assertArrayEquals(new byte[] {}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullOutput01() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input =
        new ByteArrayInputStream(myByteArray, -1759510528, -251658240);
    input.mark(0);

    // Act
    final byte[] actual = IoUtils.toByteArray(input);

    // Assert result
    Assert.assertArrayEquals(new byte[] {}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullOutput1() throws IOException {

    // Arrange
    final byte[] myByteArray = {(byte)16};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray, 0, 1);

    // Act
    final byte[] actual = IoUtils.toByteArray(input);

    // Assert side effects
    Assert.assertNotNull(input);

    // Assert result
    Assert.assertArrayEquals(new byte[] {(byte)16}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullOutput3() throws IOException {

    // Arrange
    final byte[] myByteArray = {(byte)1, (byte)1, (byte)81};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);

    // Act
    final byte[] actual = IoUtils.toByteArray(input);

    // Assert side effects
    Assert.assertNotNull(input);

    // Assert result
    Assert.assertArrayEquals(new byte[] {(byte)1, (byte)1, (byte)81}, actual);
  }

  // Test written by Diffblue Cover
  @Test
  public void toByteArrayInputNotNullOutput24() throws IOException {

    // Arrange
    final byte[] myByteArray = {(short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0};
    final ByteArrayInputStream arg0 = new ByteArrayInputStream(myByteArray);

    // Act
    final byte[] actual = IoUtils.toByteArray(arg0);

    // Assert side effects
    Assert.assertNotNull(arg0);

    // Assert result
    Assert.assertArrayEquals(
        new byte[] {(short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                    (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                    (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0, (short)0},
        actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullPositiveOutput2() throws IOException {

    // Arrange
    final byte[] myByteArray = {(byte)0, (byte)0, (byte)0};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);

    // Act
    final byte[] actual = IoUtils.toByteArray(input, 2);

    // Assert side effects
    Assert.assertNotNull(input);

    // Assert result
    Assert.assertArrayEquals(new byte[] {(byte)0, (byte)0}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toByteArrayInputNotNullZeroOutput0() throws IOException {

    // Arrange
    final byte[] myByteArray = {};
    final ByteArrayInputStream input = new ByteArrayInputStream(myByteArray);

    // Act
    final byte[] actual = IoUtils.toByteArray(input, 0);

    // Assert result
    Assert.assertArrayEquals(new byte[] {}, actual);
  }

  // Test written by Diffblue Cover
  @Test
  public void toByteArrayInputNotNullZeroOutput01() throws IOException {

    // Arrange
    final byte[] myByteArray = {(short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0,
                                (short)0, (short)0, (short)0, (short)0, (short)0, (short)0};
    final ByteArrayInputStream arg0 = new ByteArrayInputStream(myByteArray);
    final int arg1 = 0;

    // Act
    final byte[] actual = IoUtils.toByteArray(arg0, arg1);

    // Assert result
    Assert.assertArrayEquals(new byte[] {}, actual);
  }
}
