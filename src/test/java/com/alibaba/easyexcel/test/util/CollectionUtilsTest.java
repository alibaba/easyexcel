package com.alibaba.excel.util;

import com.alibaba.excel.util.CollectionUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class CollectionUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void testIsEmptyCollection() {
    ArrayList collection = null;
    Assert.assertTrue(CollectionUtils.isEmpty(collection));
    
    collection = new ArrayList();
    Assert.assertTrue(CollectionUtils.isEmpty(collection));

    collection.add(null);
    Assert.assertFalse(CollectionUtils.isEmpty(collection));
  }

  @Test
  public void testIsEmptyMap() {
    HashMap map = null;
    Assert.assertTrue(CollectionUtils.isEmpty(map));

    map = new HashMap();
    Assert.assertTrue(CollectionUtils.isEmpty(map));

    map.put(null, null);
    Assert.assertFalse(CollectionUtils.isEmpty(map));
  }

  @Test
  public void testArrayToList() {
    final ArrayList arrayList = new ArrayList();
    Assert.assertEquals(arrayList, CollectionUtils.arrayToList(null));
  }

  @Test
  public void testMergeEmptyArrayIntoCollection() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    CollectionUtils.mergeArrayIntoCollection(null, collection);
    // Method returns void, testing that no exception is thrown
  }

  @Test
  public void testMergeInvalidArrayIntoCollection() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergeArrayIntoCollection(1, new ArrayList());
    // Method is not expected to return due to exception thrown
  }

  @Test
  public void testMergeArrayIntoEmptyCollection() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergeArrayIntoCollection(null, null);
    // Method is not expected to return due to exception thrown
  }

  @Test
  public void testMergeArrayIntoCollection() throws Exception {
    final Object[] array = {0};
    final ArrayList collection = new ArrayList();
    CollectionUtils.mergeArrayIntoCollection(array, collection);

    // Assert side effects
    final ArrayList arrayList = new ArrayList();
    arrayList.add(0);
    Assert.assertEquals(arrayList, collection);
  }

  @Test
  public void testMergePropertiesIntoNullMap() {
    thrown.expect(IllegalArgumentException.class);
    CollectionUtils.mergePropertiesIntoMap(new Properties(), null);
    // Method is not expected to return due to exception thrown
  }

  @Test
  public void testMergeEmptyPropertiesIntoMap() {
    CollectionUtils.mergePropertiesIntoMap(null, new HashMap());
    // Method returns void, testing that no exception is thrown
  }

  @Test
  public void testMergePropertiesIntoMap() throws Exception {
    final Properties defaults = new Properties();
    defaults.setProperty("baz", "qux");
    final Properties props = new Properties(defaults);
    props.setProperty("foo", "bar");
    final HashMap map = new HashMap();
    CollectionUtils.mergePropertiesIntoMap(props, map);

    // Assert side effects
    final HashMap hashMap = new HashMap();
    hashMap.put("foo", "bar");
    hashMap.put("baz", "qux");
    Assert.assertEquals(hashMap, map);
  }

  @Test
  public void testContainsEnumeration() {
    StringTokenizer enumeration = null;
    Assert.assertFalse(CollectionUtils.contains(enumeration, "0"));

    enumeration = new StringTokenizer("1");
    Assert.assertFalse(CollectionUtils.contains(enumeration, "0"));

    enumeration = new StringTokenizer("0");
    Assert.assertTrue(CollectionUtils.contains(enumeration, "0"));
  }

  @Test
  public void testContainsInstance() {
    final ArrayList collection = new ArrayList();
    collection.add(null);
    Assert.assertTrue(CollectionUtils.containsInstance(collection, null));

    Assert.assertFalse(CollectionUtils.containsInstance(collection, 0));
    Assert.assertFalse(CollectionUtils.containsInstance(null, 0));
  }

  @Test
  public void testContainsAny() {
    final ArrayList source = new ArrayList();
    final ArrayList candidates = new ArrayList();
    Assert.assertFalse(CollectionUtils.containsAny(source, candidates));
   
    source.add(1);
    Assert.assertFalse(CollectionUtils.containsAny(source, candidates));

    candidates.add(0);
    Assert.assertFalse(CollectionUtils.containsAny(source, candidates));
    
    candidates.add(1);
    Assert.assertTrue(CollectionUtils.containsAny(source, candidates));
  }


  @Test
  public void testFindFirstMatch() {
    final ArrayList source = new ArrayList();
    final ArrayList candidates = new ArrayList();
    Assert.assertNull(CollectionUtils.findFirstMatch(source, candidates));
   
    source.add(1);
    Assert.assertNull(CollectionUtils.findFirstMatch(source, candidates));

    candidates.add(0);
    Assert.assertNull(CollectionUtils.findFirstMatch(source, candidates));
    
    candidates.add(1);
    Assert.assertEquals(1, CollectionUtils.findFirstMatch(source, candidates));
  }

  @Test
  public void testFindValueOfType() {
    final ArrayList collection = new ArrayList();
    final Class[] types = {null};
    Assert.assertNull(CollectionUtils.findValueOfType(collection, types));

    collection.add(null);
    Assert.assertNull(CollectionUtils.findValueOfType(collection, types));

    final Class[] emptyTypes = {};
    Assert.assertNull(CollectionUtils.findValueOfType(collection, emptyTypes));

    collection.add(0);
    Assert.assertEquals(0, CollectionUtils.findValueOfType(collection, types));
  }

  @Test
  public void testHasUniqueObject() {
    final ArrayList collection = new ArrayList();
    Assert.assertFalse(CollectionUtils.hasUniqueObject(collection));

    collection.add(1);
    Assert.assertTrue(CollectionUtils.hasUniqueObject(collection));

    collection.add(1);
    Assert.assertTrue(CollectionUtils.hasUniqueObject(collection));
    
    collection.add(2);
    Assert.assertFalse(CollectionUtils.hasUniqueObject(collection));
  }

  @Test
  public void testFindCommonElementType() {
    ArrayList collection = new ArrayList();
    Assert.assertNull(CollectionUtils.findCommonElementType(collection));

    collection.add(0);
    Assert.assertEquals(Integer.class,
            CollectionUtils.findCommonElementType(collection));

    collection.add(1);
    Assert.assertEquals(Integer.class,
            CollectionUtils.findCommonElementType(collection));

    collection.add(1.0);
    Assert.assertNull(CollectionUtils.findCommonElementType(collection));

    collection = new ArrayList();
    collection.add(null);
    Assert.assertNull(CollectionUtils.findCommonElementType(collection));
  }
  
  @Test
  public void testToArray() throws Exception {
    StringTokenizer enumeration = new StringTokenizer("0");
    Object[] array = {};
    Assert.assertArrayEquals(new Object[] {"0"},
            CollectionUtils.toArray(enumeration, array));
  }
}
