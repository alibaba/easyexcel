package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.CollectionUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionUtilsTest {

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInput0OutputTrue() {

    // Arrange
    final ArrayList collection = new ArrayList();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(collection));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInput1OutputFalse() {

    // Arrange
    final ArrayList collection = new ArrayList();
    collection.add(null);

    // Act and Assert result
    Assert.assertFalse(CollectionUtils.isEmpty(collection));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputFalse() {

    // Arrange
    final ArrayList collection = new ArrayList();
    collection.add(0);

    // Act and Assert result
    Assert.assertFalse(CollectionUtils.isEmpty(collection));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputTrue() {

    // Arrange
    final ArrayList collection = new ArrayList();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(collection));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputTrue1() {

    // Arrange
    final ArrayList collection = new ArrayList();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(collection));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInput0OutputTrue1() {

    // Arrange
    final HashMap map = new HashMap();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(map));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInput1OutputFalse1() {

    // Arrange
    final HashMap map = new HashMap();
    map.put(null, null);

    // Act and Assert result
    Assert.assertFalse(CollectionUtils.isEmpty(map));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputFalse1() {

    // Arrange
    final HashMap map = new HashMap();
    map.put(-2013199320, 0);

    // Act and Assert result
    Assert.assertFalse(CollectionUtils.isEmpty(map));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputTrue2() {

    // Arrange
    final HashMap map = new HashMap();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(map));
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNotNullOutputTrue3() {

    // Arrange
    final HashMap map = new HashMap();

    // Act and Assert result
    Assert.assertTrue(CollectionUtils.isEmpty(map));
  }

  // Test written by Diffblue Cover
  @Test
  public void isEmptyInputNotNullOutputFalse2() {

    // Arrange
    final HashMap<Object, Object> arg0 = new HashMap<Object, Object>();
    arg0.put("aaaaa", "aaaaa");

    // Act
    final boolean actual = CollectionUtils.isEmpty(arg0);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover
  @Test
  public void isEmptyInputNotNullOutputFalse3() {

    // Arrange
    final ArrayList<Object> arg0 = new ArrayList<Object>();
    arg0.add("aaaaa");

    // Act
    final boolean actual = CollectionUtils.isEmpty(arg0);

    // Assert result
    Assert.assertFalse(actual);
  }
}
