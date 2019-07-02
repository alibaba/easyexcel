package com.alibaba.easyexcel.test.util;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.isNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.alibaba.excel.util.ObjectUtils;
import com.alibaba.excel.util.StringUtils;
import com.diffblue.deeptestutils.mock.DTUMemberMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.util.calendar.ZoneInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

@RunWith(PowerMockRunner.class)
public class StringUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Rule public final Timeout globalTimeout = new Timeout(10000);

  /* testedClasses: StringUtils */
  // Test written by Diffblue Cover.
  @PrepareForTest({StringUtils.class, System.class})
  @Test
  public void addStringToArrayInput1NotNullOutput2() throws Exception {

    // Setup mocks
    mockStatic(System.class);

    // Arrange
    final String[] array = {"Bar"};
    final String str = "1a 2b 3c";

    // Act
    final String[] actual = StringUtils.addStringToArray(array, str);

    // Assert result
    Assert.assertArrayEquals(new String[] {null, "1a 2b 3c"}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void addStringToArrayInputNullNotNullOutput1() {

    // Arrange
    final String[] array = null;
    final String str = "foo";

    // Act
    final String[] actual = StringUtils.addStringToArray(array, str);

    // Assert result
    Assert.assertArrayEquals(new String[] {"foo"}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull() {

    // Arrange
    final String path = ",";
    final String relativePath = "a\'b\'c";

    // Act
    final String actual = StringUtils.applyRelativePath(path, relativePath);

    // Assert result
    Assert.assertEquals("a\'b\'c", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull2() {

    // Arrange
    final String path = "/";
    final String relativePath = "/";

    // Act
    final String actual = StringUtils.applyRelativePath(path, relativePath);

    // Assert result
    Assert.assertEquals("/", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull3() {

    // Arrange
    final String path = "/";
    final String relativePath = "\'";

    // Act
    final String actual = StringUtils.applyRelativePath(path, relativePath);

    // Assert result
    Assert.assertEquals("/\'", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void arrayToCommaDelimitedStringInputNullOutputNotNull() {

    // Arrange
    final Object[] arr = null;

    // Act
    final String actual = StringUtils.arrayToCommaDelimitedString(arr);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void arrayToDelimitedStringInput1NotNullOutputNotNull() {

    // Arrange
    final Object[] arr = {1};
    final String delim = "/";

    // Act
    final String actual = StringUtils.arrayToDelimitedString(arr, delim);

    // Assert result
    Assert.assertEquals("1", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void arrayToDelimitedStringInput2NotNullOutputNotNull() {

    // Arrange
    final Object[] arr = {null, null};
    final String delim = "foo";

    // Act
    final String actual = StringUtils.arrayToDelimitedString(arr, delim);

    // Assert result
    Assert.assertEquals("nullfoonull", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void arrayToDelimitedStringInputNullNotNullOutputNotNull() {

    // Arrange
    final Object[] arr = null;
    final String delim = ",";

    // Act
    final String actual = StringUtils.arrayToDelimitedString(arr, delim);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void capitalizeInputNotNullOutputNotNull() {

    // Arrange
    final String str = "2";

    // Act
    final String actual = StringUtils.capitalize(str);

    // Assert result
    Assert.assertEquals("2", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void capitalizeInputNotNullOutputNotNull2() {

    // Arrange
    final String str = "foo";

    // Act
    final String actual = StringUtils.capitalize(str);

    // Assert result
    Assert.assertEquals("Foo", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void capitalizeInputNullOutputNull() {

    // Arrange
    final String str = null;

    // Act
    final String actual = StringUtils.capitalize(str);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void collectionToCommaDelimitedStringInputNullOutputNotNull() {

    // Arrange
    final Collection coll = null;

    // Act
    final String actual = StringUtils.collectionToCommaDelimitedString(coll);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void collectionToDelimitedStringInput1NotNullOutputNotNull() {

    // Arrange
    final ArrayList coll = new ArrayList();
    coll.add(null);
    final String delim = "A1B2C3";

    // Act
    final String actual = StringUtils.collectionToDelimitedString(coll, delim);

    // Assert result
    Assert.assertEquals("null", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void collectionToDelimitedStringInputNullNotNullOutputNotNull() {

    // Arrange
    final Collection coll = null;
    final String delim = ",";

    // Act
    final String actual = StringUtils.collectionToDelimitedString(coll, delim);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void commaDelimitedListToSetInputNotNullOutputNotNull() {

    // Arrange
    final String str = "foo";

    // Act
    final Set<String> actual = StringUtils.commaDelimitedListToSet(str);

    // Assert result
    final LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
    linkedHashSet.add("foo");
    Assert.assertEquals(linkedHashSet, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void commaDelimitedListToStringArrayInputNotNullOutput1() {

    // Arrange
    final String str = "3";

    // Act
    final String[] actual = StringUtils.commaDelimitedListToStringArray(str);

    // Assert result
    Assert.assertArrayEquals(new String[] {"3"}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void concatenateStringArraysInput10Output1() {

    // Arrange
    final String[] array1 = {null};
    final String[] array2 = {};

    // Act
    final String[] actual = StringUtils.concatenateStringArrays(array1, array2);

    // Assert result
    Assert.assertArrayEquals(new String[] {null}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringUtils.class, System.class})
  @Test
  public void concatenateStringArraysInput11Output2() throws Exception {

    // Setup mocks
    mockStatic(System.class);

    // Arrange
    final String[] array1 = {null};
    final String[] array2 = {null};

    // Act
    final String[] actual = StringUtils.concatenateStringArrays(array1, array2);

    // Assert result
    Assert.assertArrayEquals(new String[] {null, null}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void concatenateStringArraysInputNullNullOutputNull() {

    // Arrange
    final String[] array1 = null;
    final String[] array2 = null;

    // Act
    final String[] actual = StringUtils.concatenateStringArrays(array1, array2);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void containsWhitespaceInputNotNullOutputFalse() {

    // Arrange
    final String str = "1";

    // Act
    final boolean actual = StringUtils.containsWhitespace(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void containsWhitespaceInputNotNullOutputTrue() {

    // Arrange
    final String str = "1a 2b 3c";

    // Act
    final boolean actual = StringUtils.containsWhitespace(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void containsWhitespaceInputNullOutputFalse() {

    // Arrange
    final CharSequence str = null;

    // Act
    final boolean actual = StringUtils.containsWhitespace(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputPositive() {

    // Arrange
    final String str = "foo";
    final String sub = "foo";

    // Act
    final int actual = StringUtils.countOccurrencesOf(str, sub);

    // Assert result
    Assert.assertEquals(1, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputZero() {

    // Arrange
    final String str = "foo";
    final String sub = "1a 2b 3c";

    // Act
    final int actual = StringUtils.countOccurrencesOf(str, sub);

    // Assert result
    Assert.assertEquals(0, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputZero2() {

    // Arrange
    final String str = "";
    final String sub = "?????????????????????????????????????????????????????????????????";

    // Act
    final int actual = StringUtils.countOccurrencesOf(str, sub);

    // Assert result
    Assert.assertEquals(0, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void deleteInputNotNullNotNullOutputNotNull() {

    // Arrange
    final String inString = "foo";
    final String pattern = "1234";

    // Act
    final String actual = StringUtils.delete(inString, pattern);

    // Assert result
    Assert.assertEquals("foo", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNotNullOutput0() {

    // Arrange
    final String str = "";
    final String delimiter =
        "\u5416\u5416\u5415\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416\u5416";
    final String charsToDelete = "";

    // Act
    final String[] actual = StringUtils.delimitedListToStringArray(str, delimiter, charsToDelete);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNotNullOutput1() {

    // Arrange
    final String str = "\u171c";
    final String delimiter = "";
    final String charsToDelete = "";

    // Act
    final String[] actual = StringUtils.delimitedListToStringArray(str, delimiter, charsToDelete);

    // Assert result
    Assert.assertArrayEquals(new String[] {"\u171c"}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNotNullOutput02() {

    // Arrange
    final String str = "";
    final String delimiter = "";
    final String charsToDelete = "";

    // Act
    final String[] actual = StringUtils.delimitedListToStringArray(str, delimiter, charsToDelete);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullOutput1() {

    // Arrange
    final String str = "A1B2C3";
    final String delimiter = "a,b,c";

    // Act
    final String[] actual = StringUtils.delimitedListToStringArray(str, delimiter);

    // Assert result
    Assert.assertArrayEquals(new String[] {"A1B2C3"}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void endsWithIgnoreCaseInputNotNullNotNullOutputFalse() {

    // Arrange
    final String str = "3";
    final String suffix = "BAZ";

    // Act
    final boolean actual = StringUtils.endsWithIgnoreCase(str, suffix);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameExtensionInputNotNullOutputNotNull() {

    // Arrange
    final String path = "................\uffd5\uffd5";

    // Act
    final String actual = StringUtils.getFilenameExtension(path);

    // Assert result
    Assert.assertEquals("\uffd5\uffd5", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameExtensionInputNotNullOutputNull() {

    // Arrange
    final String path = "a\'b\'c";

    // Act
    final String actual = StringUtils.getFilenameExtension(path);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameExtensionInputNotNullOutputNull2() {

    // Arrange
    final String path = "................/\uffd5";

    // Act
    final String actual = StringUtils.getFilenameExtension(path);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameExtensionInputNullOutputNull() {

    // Arrange
    final String path = null;

    // Act
    final String actual = StringUtils.getFilenameExtension(path);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameInputNotNullOutputNotNull() {

    // Arrange
    final String path = "3";

    // Act
    final String actual = StringUtils.getFilename(path);

    // Assert result
    Assert.assertEquals("3", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameInputNotNullOutputNotNull2() {

    // Arrange
    final String path = "a/b/c";

    // Act
    final String actual = StringUtils.getFilename(path);

    // Assert result
    Assert.assertEquals("c", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void getFilenameInputNullOutputNull() {

    // Arrange
    final String path = null;

    // Act
    final String actual = StringUtils.getFilename(path);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasLengthInputNotNullOutputFalse() {

    // Arrange
    final CharSequence str = "";

    // Act
    final boolean actual = StringUtils.hasLength(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasLengthInputNotNullOutputTrue() {

    // Arrange
    final String str = "3";

    // Act
    final boolean actual = StringUtils.hasLength(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasLengthInputNullOutputFalse() {

    // Arrange
    final CharSequence str = null;

    // Act
    final boolean actual = StringUtils.hasLength(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNotNullOutputFalse() {

    // Arrange
    final CharSequence str = "\n\n";

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNotNullOutputTrue() {

    // Arrange
    final String str = "foo";

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNotNullOutputTrue2() {

    // Arrange
    final CharSequence str = "\u0000";

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNotNullOutputTrue3() {

    // Arrange
    final CharSequence str = "\n\u0000";

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNullOutputFalse() {

    // Arrange
    final CharSequence str = null;

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void hasTextInputNullOutputFalse2() {

    // Arrange
    final String str = null;

    // Act
    final boolean actual = StringUtils.hasText(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputNullOutputTrue() {

    // Arrange
    final Object str = null;

    // Act
    final boolean actual = StringUtils.isEmpty(str);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void isEmptyInputZeroOutputFalse() {

    // Arrange
    final Object str = 0;

    // Act
    final boolean actual = StringUtils.isEmpty(str);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest(ObjectUtils.class)
  @Test
  public void mergeStringArraysInput02Output1() throws Exception {

    // Setup mocks
    PowerMockito.mockStatic(ObjectUtils.class);

    // Arrange
    final String[] array1 = {};
    final String[] array2 = {null, null};
    final Method isEmptyMethod =
        DTUMemberMatcher.method(ObjectUtils.class, "isEmpty", java.lang.Object[].class);
    ((PowerMockitoStubber)PowerMockito.doReturn(false).doReturn(false))
        .when(ObjectUtils.class, isEmptyMethod)
        .withArguments(or(isA(java.lang.Object[].class), isNull(java.lang.Object[].class)));

    // Act
    final String[] actual = StringUtils.mergeStringArrays(array1, array2);

    // Assert result
    Assert.assertArrayEquals(new String[] {null}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void mergeStringArraysInput10Output1() {

    // Arrange
    final String[] array1 = {null};
    final String[] array2 = {};

    // Act
    final String[] actual = StringUtils.mergeStringArrays(array1, array2);

    // Assert result
    Assert.assertArrayEquals(new String[] {null}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void mergeStringArraysInput12Output1() {

    // Arrange
    final String[] array1 = {null};
    final String[] array2 = {null, null};

    // Act
    final String[] actual = StringUtils.mergeStringArrays(array1, array2);

    // Assert result
    Assert.assertArrayEquals(new String[] {null}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void mergeStringArraysInputNullNullOutputNull() {

    // Arrange
    final String[] array1 = null;
    final String[] array2 = null;

    // Act
    final String[] actual = StringUtils.mergeStringArrays(array1, array2);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void parseLocaleStringInputNotNullOutputNotNull() throws Exception {

    // Arrange
    final String localeString = "3";
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    ((PowerMockitoStubber)PowerMockito.doReturn(true).doReturn(false))
        .when(stringTokenizer, hasMoreTokensMethod)
        .withNoArguments();
    final Method nextTokenMethod = DTUMemberMatcher.method(StringTokenizer.class, "nextToken");
    PowerMockito.doReturn("2").when(stringTokenizer, nextTokenMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);
    final Locale locale = PowerMockito.mock(Locale.class);
    PowerMockito.whenNew(Locale.class)
        .withParameterTypes(String.class, String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(locale);

    // Act
    final Locale actual = StringUtils.parseLocaleString(localeString);

    // Assert result
    Assert.assertNotNull(actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void parseLocaleStringInputNotNullOutputNull() throws Exception {

    // Arrange
    final String localeString = ",";
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    PowerMockito.doReturn(false).when(stringTokenizer, hasMoreTokensMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final Locale actual = StringUtils.parseLocaleString(localeString);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void parseTimeZoneStringInputNotNullOutputIllegalArgumentException() {

    // Arrange
    final String timeZoneString = "3";

    // Act
    thrown.expect(IllegalArgumentException.class);
    StringUtils.parseTimeZoneString(timeZoneString);

    // Method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void parseTimeZoneStringInputNotNullOutputNotNull() {

    // Arrange
    final String timeZoneString = "America/New_York";

    // Act
    final TimeZone actual = StringUtils.parseTimeZoneString(timeZoneString);

    // Assert result
    Assert.assertEquals("America/New_York", ((ZoneInfo)actual).getID());
    Assert.assertEquals(-18000000, ((ZoneInfo)actual).getRawOffset());
  }

  // Test written by Diffblue Cover.
  @Test
  public void quoteIfStringInputNotNullOutputNotNull() {

    // Arrange
    final Object obj = "1234";

    // Act
    final Object actual = StringUtils.quoteIfString(obj);

    // Assert result
    Assert.assertEquals("\'1234\'", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void quoteIfStringInputNullOutputNull() {

    // Arrange
    final Object obj = null;

    // Act
    final Object actual = StringUtils.quoteIfString(obj);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void quoteInputNotNullOutputNotNull() {

    // Arrange
    final String str = "1234";

    // Act
    final String actual = StringUtils.quote(str);

    // Assert result
    Assert.assertEquals("\'1234\'", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void removeDuplicateStringsInputNullOutputNull() {

    // Arrange
    final String[] array = null;

    // Act
    final String[] actual = StringUtils.removeDuplicateStrings(array);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void replaceInputNullNullNullOutputNull() {

    // Arrange
    final String inString = null;
    final String oldPattern = null;
    final String newPattern = null;

    // Act
    final String actual = StringUtils.replace(inString, oldPattern, newPattern);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void sortStringArrayInput1Output1() {

    // Arrange
    final String[] array = {"/"};

    // Act
    final String[] actual = StringUtils.sortStringArray(array);

    // Assert result
    Assert.assertArrayEquals(new String[] {"/"}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void sortStringArrayInputNullOutput0() {

    // Arrange
    final String[] array = null;

    // Act
    final String[] actual = StringUtils.sortStringArray(array);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void splitArrayElementsIntoPropertiesInput0NotNullOutputNull() {

    // Arrange
    final String[] array = {};
    final String delimiter = "a,b,c";

    // Act
    final Properties actual = StringUtils.splitArrayElementsIntoProperties(array, delimiter);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void splitArrayElementsIntoPropertiesInput1NotNullNotNullOutput0() {

    // Arrange
    final String[] array = {null};
    final String delimiter = "foo";
    final String charsToDelete = "foo";

    // Act
    final Properties actual =
        StringUtils.splitArrayElementsIntoProperties(array, delimiter, charsToDelete);

    // Assert result
    final Properties properties = new Properties();
    Assert.assertEquals(properties, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void splitInputNotNullNotNullOutput2() {

    // Arrange
    final String toSplit = "3";
    final String delimiter = "3";

    // Act
    final String[] actual = StringUtils.split(toSplit, delimiter);

    // Assert result
    Assert.assertArrayEquals(new String[] {"", ""}, actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void splitInputNotNullNotNullOutputNull() {

    // Arrange
    final String toSplit = "A1B2C3";
    final String delimiter = "\'";

    // Act
    final String[] actual = StringUtils.split(toSplit, delimiter);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void startsWithIgnoreCaseInputNotNullNotNullOutputFalse() {

    // Arrange
    final String str = "\'";
    final String prefix = "1a 2b 3c";

    // Act
    final boolean actual = StringUtils.startsWithIgnoreCase(str, prefix);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull() {

    // Arrange
    final String path = "3";

    // Act
    final String actual = StringUtils.stripFilenameExtension(path);

    // Assert result
    Assert.assertEquals("3", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull2() {

    // Arrange
    final String path = "./";

    // Act
    final String actual = StringUtils.stripFilenameExtension(path);

    // Assert result
    Assert.assertEquals("./", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull3() {

    // Arrange
    final String path =
        "//\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b.,,,,,";

    // Act
    final String actual = StringUtils.stripFilenameExtension(path);

    // Assert result
    Assert.assertEquals(
        "//\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b\udf2b",
        actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void stripFilenameExtensionInputNullOutputNull() {

    // Arrange
    final String path = null;

    // Act
    final String actual = StringUtils.stripFilenameExtension(path);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void substringMatchInputNotNullNegativeNotNullOutputStringIndexOutOfBoundsException() {

    // Arrange
    final CharSequence str = "";
    final int index = -1073750012;
    final CharSequence substring = "\u0000\u0000\u0000?????????????";

    // Act
    thrown.expect(StringIndexOutOfBoundsException.class);
    StringUtils.substringMatch(str, index, substring);

    // Method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void substringMatchInputNotNullNegativeNotNullOutputTrue() {

    // Arrange
    final CharSequence str = "";
    final int index = -1073750012;
    final CharSequence substring = "";

    // Act
    final boolean actual = StringUtils.substringMatch(str, index, substring);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void substringMatchInputNotNullPositiveNotNullOutputFalse() {

    // Arrange
    final CharSequence str = "";
    final int index = 2147483647;
    final CharSequence substring = "";

    // Act
    final boolean actual = StringUtils.substringMatch(str, index, substring);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void substringMatchInputNotNullZeroNotNullOutputFalse() {

    // Arrange
    final CharSequence str = "\uffe0";
    final int index = 0;
    final CharSequence substring = "\uffe1";

    // Act
    final boolean actual = StringUtils.substringMatch(str, index, substring);

    // Assert result
    Assert.assertFalse(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void substringMatchInputNotNullZeroNotNullOutputTrue() {

    // Arrange
    final CharSequence str = "\uffe0";
    final int index = 0;
    final CharSequence substring = "\uffe0";

    // Act
    final boolean actual = StringUtils.substringMatch(str, index, substring);

    // Assert result
    Assert.assertTrue(actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNotNullFalseFalseOutput1() throws Exception {

    // Arrange
    final String str = "foo";
    final String delimiters = "Bar";
    final boolean trimTokens = false;
    final boolean ignoreEmptyTokens = false;
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    ((PowerMockitoStubber)PowerMockito.doReturn(true).doReturn(false))
        .when(stringTokenizer, hasMoreTokensMethod)
        .withNoArguments();
    final Method nextTokenMethod = DTUMemberMatcher.method(StringTokenizer.class, "nextToken");
    PowerMockito.doReturn("foo").when(stringTokenizer, nextTokenMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final String[] actual =
        StringUtils.tokenizeToStringArray(str, delimiters, trimTokens, ignoreEmptyTokens);

    // Assert result
    Assert.assertArrayEquals(new String[] {"foo"}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNotNullFalseTrueOutput1() throws Exception {

    // Arrange
    final String str = "foo";
    final String delimiters = "Bar";
    final boolean trimTokens = false;
    final boolean ignoreEmptyTokens = true;
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    ((PowerMockitoStubber)PowerMockito.doReturn(true).doReturn(false))
        .when(stringTokenizer, hasMoreTokensMethod)
        .withNoArguments();
    final Method nextTokenMethod = DTUMemberMatcher.method(StringTokenizer.class, "nextToken");
    PowerMockito.doReturn("3").when(stringTokenizer, nextTokenMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final String[] actual =
        StringUtils.tokenizeToStringArray(str, delimiters, trimTokens, ignoreEmptyTokens);

    // Assert result
    Assert.assertArrayEquals(new String[] {"3"}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNotNullOutput0() throws Exception {

    // Arrange
    final String str = "1234";
    final String delimiters = "2";
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    PowerMockito.doReturn(false).when(stringTokenizer, hasMoreTokensMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final String[] actual = StringUtils.tokenizeToStringArray(str, delimiters);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNotNullTrueFalseOutput1() throws Exception {

    // Arrange
    final String str = "foo";
    final String delimiters = "foo";
    final boolean trimTokens = true;
    final boolean ignoreEmptyTokens = false;
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    ((PowerMockitoStubber)PowerMockito.doReturn(true).doReturn(false))
        .when(stringTokenizer, hasMoreTokensMethod)
        .withNoArguments();
    final Method nextTokenMethod = DTUMemberMatcher.method(StringTokenizer.class, "nextToken");
    PowerMockito.doReturn("foo").when(stringTokenizer, nextTokenMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final String[] actual =
        StringUtils.tokenizeToStringArray(str, delimiters, trimTokens, ignoreEmptyTokens);

    // Assert result
    Assert.assertArrayEquals(new String[] {"foo"}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNotNullTrueTrueOutput0() throws Exception {

    // Arrange
    final String str = "foo";
    final String delimiters = "1a 2b 3c";
    final boolean trimTokens = true;
    final boolean ignoreEmptyTokens = true;
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    PowerMockito.doReturn(false).when(stringTokenizer, hasMoreTokensMethod).withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    final String[] actual =
        StringUtils.tokenizeToStringArray(str, delimiters, trimTokens, ignoreEmptyTokens);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.
  @PrepareForTest({StringTokenizer.class, StringUtils.class})
  @Test
  public void tokenizeToStringArrayInputNotNullNullOutputNullPointerException() throws Exception {

    // Arrange
    final String str = "?";
    final String delimiters = null;
    final StringTokenizer stringTokenizer = PowerMockito.mock(StringTokenizer.class);
    final Method hasMoreTokensMethod =
        DTUMemberMatcher.method(StringTokenizer.class, "hasMoreTokens");
    ((PowerMockitoStubber)PowerMockito.doReturn(true).doReturn(true))
        .when(stringTokenizer, hasMoreTokensMethod)
        .withNoArguments();
    final Method nextTokenMethod = DTUMemberMatcher.method(StringTokenizer.class, "nextToken");
    ((PowerMockitoStubber)PowerMockito.doReturn("\u0000\u0000").doReturn(null))
        .when(stringTokenizer, nextTokenMethod)
        .withNoArguments();
    PowerMockito.whenNew(StringTokenizer.class)
        .withParameterTypes(String.class, String.class)
        .withArguments(or(isA(String.class), isNull(String.class)),
                       or(isA(String.class), isNull(String.class)))
        .thenReturn(stringTokenizer);

    // Act
    thrown.expect(NullPointerException.class);
    StringUtils.tokenizeToStringArray(str, delimiters);

    // Method is not expected to return due to exception thrown
  }

  // Test written by Diffblue Cover.
  @Test
  public void tokenizeToStringArrayInputNullNullOutputNull() {

    // Arrange
    final String str = null;
    final String delimiters = null;

    // Act
    final String[] actual = StringUtils.tokenizeToStringArray(str, delimiters);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toStringArrayInputNullOutputNull() {

    // Arrange
    final Collection<String> collection = null;

    // Act
    final String[] actual = StringUtils.toStringArray(collection);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void toStringArrayInputNullOutputNull2() {

    // Arrange
    final Enumeration<String> enumeration = null;

    // Act
    final String[] actual = StringUtils.toStringArray(enumeration);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimAllWhitespaceInputNullOutputNull() {

    // Arrange
    final String str = null;

    // Act
    final String actual = StringUtils.trimAllWhitespace(str);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void trimArrayElementsInput0Output0() {

    // Arrange
    final String[] array = {};

    // Act
    final String[] actual = StringUtils.trimArrayElements(array);

    // Assert result
    Assert.assertArrayEquals(new String[] {}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void trimArrayElementsInput1Output1() {

    // Arrange
    final String[] array = {null};

    // Act
    final String[] actual = StringUtils.trimArrayElements(array);

    // Assert result
    Assert.assertArrayEquals(new String[] {null}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void trimArrayElementsInput1Output12() {

    // Arrange
    final String[] array = {"1"};

    // Act
    final String[] actual = StringUtils.trimArrayElements(array);

    // Assert result
    Assert.assertArrayEquals(new String[] {"1"}, actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void trimLeadingCharacterInputNotNull2OutputNotNull() {

    // Arrange
    final String str = "2";
    final char leadingCharacter = '2';

    // Act
    final String actual = StringUtils.trimLeadingCharacter(str, leadingCharacter);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimLeadingCharacterInputNotNullNotNullOutputNotNull() {

    // Arrange
    final String str = "\'";
    final char leadingCharacter = '\u0000';

    // Act
    final String actual = StringUtils.trimLeadingCharacter(str, leadingCharacter);

    // Assert result
    Assert.assertEquals("\'", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimLeadingCharacterInputNotNullNotNullOutputNotNull2() {

    // Arrange
    final String str = "";
    final char leadingCharacter = '\u0000';

    // Act
    final String actual = StringUtils.trimLeadingCharacter(str, leadingCharacter);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimLeadingWhitespaceInputNotNullOutputNotNull() {

    // Arrange
    final String str = "\'";

    // Act
    final String actual = StringUtils.trimLeadingWhitespace(str);

    // Assert result
    Assert.assertEquals("\'", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimLeadingWhitespaceInputNotNullOutputNotNull2() {

    // Arrange
    final String str = "";

    // Act
    final String actual = StringUtils.trimLeadingWhitespace(str);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimLeadingWhitespaceInputNotNullOutputNotNull3() {

    // Arrange
    final String str = "\n";

    // Act
    final String actual = StringUtils.trimLeadingWhitespace(str);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingCharacterInputNotNull2OutputNotNull() {

    // Arrange
    final String str = "2";
    final char trailingCharacter = '2';

    // Act
    final String actual = StringUtils.trimTrailingCharacter(str, trailingCharacter);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingCharacterInputNotNullNotNullOutputNotNull() {

    // Arrange
    final String str = "3";
    final char trailingCharacter = '\u0001';

    // Act
    final String actual = StringUtils.trimTrailingCharacter(str, trailingCharacter);

    // Assert result
    Assert.assertEquals("3", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingCharacterInputNullNotNullOutputNull() {

    // Arrange
    final String str = null;
    final char trailingCharacter = '\u0000';

    // Act
    final String actual = StringUtils.trimTrailingCharacter(str, trailingCharacter);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingWhitespaceInputNotNullOutputNotNull() {

    // Arrange
    final String str = ",";

    // Act
    final String actual = StringUtils.trimTrailingWhitespace(str);

    // Assert result
    Assert.assertEquals(",", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingWhitespaceInputNotNullOutputNotNull2() {

    // Arrange
    final String str = "\u6339\ua3d9\u23d9\u1680";

    // Act
    final String actual = StringUtils.trimTrailingWhitespace(str);

    // Assert result
    Assert.assertEquals("\u6339\ua3d9\u23d9", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingWhitespaceInputNotNullOutputNotNull3() {

    // Arrange
    final String str = "\u1680";

    // Act
    final String actual = StringUtils.trimTrailingWhitespace(str);

    // Assert result
    Assert.assertEquals("", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimTrailingWhitespaceInputNullOutputNull() {

    // Arrange
    final String str = null;

    // Act
    final String actual = StringUtils.trimTrailingWhitespace(str);

    // Assert result
    Assert.assertNull(actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void trimWhitespaceInputNotNullOutputNotNull() {

    // Arrange
    final String str = "3";

    // Act
    final String actual = StringUtils.trimWhitespace(str);

    // Assert result
    Assert.assertEquals("3", actual);
  }

  // Test written by Diffblue Cover.

  @Test
  public void uncapitalizeInputNotNullOutputNotNull() {

    // Arrange
    final String str = "1";

    // Act
    final String actual = StringUtils.uncapitalize(str);

    // Assert result
    Assert.assertEquals("1", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void unqualifyInputNotNullNotNullOutputNotNull() {

    // Arrange
    final String qualifiedName = ",";
    final char separator = '\'';

    // Act
    final String actual = StringUtils.unqualify(qualifiedName, separator);

    // Assert result
    Assert.assertEquals(",", actual);
  }

  // Test written by Diffblue Cover.
  @Test
  public void unqualifyInputNotNullOutputNotNull() {

    // Arrange
    final String qualifiedName = ",";

    // Act
    final String actual = StringUtils.unqualify(qualifiedName);

    // Assert result
    Assert.assertEquals(",", actual);
  }
}
