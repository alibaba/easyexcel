package com.alibaba.excel.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.HashMap;

public class CollectionUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void arrayToListInputNegativeOutputIllegalArgumentException() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.arrayToList(-21);
  }

  @Test
  public void arrayToListInputNullOutput0() {
  	Assert.assertEquals(new ArrayList(), CollectionUtils.arrayToList(null));
  }

  @Test
  public void containsAnyInput00OutputFalse() {
  	Assert.assertFalse(CollectionUtils.containsAny(new ArrayList(), new ArrayList()));
  }

  @Test
  public void containsAnyInput10OutputFalse() {
    final ArrayList source = new ArrayList();
    source.add(null);
    Assert.assertFalse(CollectionUtils.containsAny(source, new ArrayList()));
  }

  @Test
  public void containsAnyInput11OutputTrue() {
    final ArrayList source = new ArrayList();
    source.add(null);
    final ArrayList candidates = new ArrayList();
    candidates.add(null);
    Assert.assertTrue(CollectionUtils.containsAny(source, candidates));
  }

  @Test
  public void containsAnyInput12OutputFalse() {
    final ArrayList source = new ArrayList();
    source.add(null);
    final ArrayList candidates = new ArrayList();
    candidates.add(0);
    candidates.add(1);
    final boolean actual = CollectionUtils.containsAny(source, candidates);
    Assert.assertFalse(actual);
  }

  @Test
  public void containsInstanceInput0NegativeOutputFalse() {
  	Assert.assertFalse(CollectionUtils.containsInstance(new ArrayList(), -999998));
  }

  @Test
  public void containsInstanceInput2NullOutputTrue() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    collection.add(null);
    final boolean actual = CollectionUtils.containsInstance(collection, null);
    Assert.assertTrue(actual);
  }

  @Test
  public void containsInstanceInput2NullOutputTrue2() {
    final ArrayList collection = new ArrayList();
    collection.add(0);
    collection.add(null);
    final Object element = null;
    final boolean actual = CollectionUtils.containsInstance(collection, element);
    Assert.assertTrue(actual);
  }

  @Test
  public void findCommonElementTypeInput0OutputNull() {
    final ArrayList collection = new ArrayList();
    Assert.assertNull(CollectionUtils.findCommonElementType(collection));
  }

  @Test
  public void findCommonElementTypeInput1OutputNull() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    final Class actual = CollectionUtils.findCommonElementType(collection);
    Assert.assertNull(actual);
  }

  @Test
  public void findFirstMatchInput01OutputNull() {
    final ArrayList source = new ArrayList();
    final ArrayList candidates = new ArrayList();
    candidates.add(-999999);
    Assert.assertNull(CollectionUtils.findFirstMatch(source, candidates));
  }

  @Test
  public void findFirstMatchInput11OutputNull() {
    final ArrayList source = new ArrayList();
    source.add(null);
    final ArrayList candidates = new ArrayList();
    candidates.add(null);
    Assert.assertNull(CollectionUtils.findFirstMatch(source, candidates));
  }

  @Test
  public void findFirstMatchInput11OutputNull2() {
    final ArrayList source = new ArrayList();
    source.add(2);
    final ArrayList candidates = new ArrayList();
    candidates.add(null);
    final Object actual = CollectionUtils.findFirstMatch(source, candidates);
    Assert.assertNull(actual);
  }

  @Test
  public void findValueOfTypeInput00OutputNull() {
    final ArrayList collection = new ArrayList();
    final Class[] types = {};
    Assert.assertNull(CollectionUtils.findValueOfType(collection, types));
  }

  @Test
  public void findValueOfTypeInput1NullOutputNull() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    final Class type = null;
    final Object actual = CollectionUtils.findValueOfType(collection, type);
    Assert.assertNull(actual);
  }

  @Test
  public void findValueOfTypeInput2NullOutputNull() {
    final ArrayList collection = new ArrayList();
    collection.add(0);
    collection.add(null);
    final Class type = null;
    final Object actual = CollectionUtils.findValueOfType(collection, type);
    Assert.assertNull(actual);
  }

  @Test
  public void findValueOfTypeInput10OutputNull() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    final Class[] types = {};
    Assert.assertNull(CollectionUtils.findValueOfType(collection, types));
  }

  @Test
  public void findValueOfTypeInput11OutputZero() {
    final ArrayList collection = new ArrayList();
    collection.add(0);
    final Class[] types = {null};
    final Object actual = CollectionUtils.findValueOfType(collection, types);
    Assert.assertEquals(0, actual);
  }

  @Test
  public void findValueOfTypeInput12OutputNull() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    final Class[] types = {null, null};
    final Object actual = CollectionUtils.findValueOfType(collection, types);
    Assert.assertNull(actual);
  }

  @Test
  public void hasUniqueObjectInput0OutputFalse() {
    final ArrayList collection = new ArrayList();
    Assert.assertFalse(CollectionUtils.hasUniqueObject(collection));
  }

  @Test
  public void hasUniqueObjectInput2OutputFalse() {
    final ArrayList collection = new ArrayList();
    collection.add(0);
    collection.add(null);
    final boolean actual = CollectionUtils.hasUniqueObject(collection);
    Assert.assertFalse(actual);
  }

  @Test
  public void hasUniqueObjectInput2OutputTrue() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    collection.add(null);
    final boolean actual = CollectionUtils.hasUniqueObject(collection);
    Assert.assertTrue(actual);
  }

  @Test
  public void isEmptyInput0OutputTrue() {
    final ArrayList collection = new ArrayList();
    Assert.assertTrue(CollectionUtils.isEmpty(collection));
  }

  @Test
  public void isEmptyInput0OutputTrue2() {
    final HashMap map = new HashMap();
    Assert.assertTrue(CollectionUtils.isEmpty(map));
  }

  @Test
  public void isEmptyInput1OutputFalse() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    Assert.assertFalse(CollectionUtils.isEmpty(collection));
  }

  @Test
  public void isEmptyInput1OutputFalse2() {
    final HashMap map = new HashMap();
    map.put(null, null);
    Assert.assertFalse(CollectionUtils.isEmpty(map));
  }

  @Test
  public void mergeArrayIntoCollectionInputNegative1OutputIllegalArgumentException() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergeArrayIntoCollection(-21, collection);
  }

  @Test
  public void mergeArrayIntoCollectionInputNullNullOutputIllegalArgumentException() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergeArrayIntoCollection(null, null);
  }

  @Test
  public void mergePropertiesIntoMapInputNullNullOutputIllegalArgumentException() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergePropertiesIntoMap(null, null);
  }
}
