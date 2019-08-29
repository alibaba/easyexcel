package com.alibaba.excel.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import sun.util.calendar.ZoneInfo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Properties;

@RunWith(PowerMockRunner.class)
public class StringUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void addStringToArrayInput0NotNullOutput1() {
	  Assert.assertArrayEquals(new String[] {"/"},
			  StringUtils.addStringToArray(new String[]{}, "/"));
  }

  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull() {
    Assert.assertEquals("foo", StringUtils.applyRelativePath("foo", "foo"));
  }

  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull2() {
    Assert.assertEquals(
        "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000/",
        StringUtils.applyRelativePath(
            "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000/????", ""));
  }

  @Test
  public void applyRelativePathInputNotNullNotNullOutputNotNull3() {
    Assert.assertEquals(
        "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000/",
        StringUtils.applyRelativePath(
            "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000/????", "/"));
  }

  @Test
  public void arrayToCommaDelimitedStringInput0OutputNotNull() {
  	final String actual = StringUtils.arrayToCommaDelimitedString(new Object[]{});
    Assert.assertEquals("", actual);
  }
  
  @Test
  public void arrayToDelimitedStringInput0NotNullOutputNotNull() {
	  Assert.assertEquals("",
			  StringUtils.arrayToDelimitedString(new Object[]{}, "foo"));
  }

  @Test
  public void arrayToDelimitedStringInput1NullOutputNotNull() {
	  Assert.assertEquals("-491519",
			  StringUtils.arrayToDelimitedString(new Object[]{-491519}, null));
  }

  @Test
  public void arrayToDelimitedStringInput2NotNullOutputNotNull() {
  	Assert.assertEquals("-491519?null",
			StringUtils.arrayToDelimitedString(new Object[]{-491519, null}, "?"));
  }

  @Test
  public void capitalizeInputNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.capitalize("/"));
  }

  @Test
  public void capitalizeInputNotNullOutputNotNull2() {
  	Assert.assertEquals("D\u0000",
			StringUtils.capitalize("d\u0000"));
  }

  @Test
  public void capitalizeInputNullOutputNull() {
    Assert.assertNull(StringUtils.capitalize(null));
  }

  @Test
  public void cleanPathInputNullOutputNull() {
    Assert.assertNull(StringUtils.cleanPath(null));
  }

  @Test
  public void collectionToCommaDelimitedStringInput0OutputNotNull() {
	  Assert.assertEquals("",
			  StringUtils.collectionToCommaDelimitedString(new ArrayList()));
  }

  @Test
  public void collectionToDelimitedStringInput0NotNullOutputNotNull() {
	  Assert.assertEquals("",
			  StringUtils.collectionToDelimitedString(new ArrayList(), "foo"));
  }

  @Test
  public void collectionToDelimitedStringInput1NotNullOutputNotNull() {
    final ArrayList coll = new ArrayList();
    coll.add(1);
    Assert.assertEquals("1",
			StringUtils.collectionToDelimitedString(coll, "??"));
  }

  @Test
  public void collectionToDelimitedStringInput2NotNullOutputNotNull() {
    final ArrayList coll = new ArrayList();
    coll.add(3);
    coll.add(3);
    Assert.assertEquals("3????3",
			StringUtils.collectionToDelimitedString(coll, "????"));
  }

  @Test
  public void commaDelimitedListToSetInputNotNullOutputNotNull() {
  	final LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
    Assert.assertEquals(linkedHashSet, StringUtils.commaDelimitedListToSet(""));
  }

  @Test
  public void commaDelimitedListToSetInputNotNullOutputNotNull2() {
  	final LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
    linkedHashSet.add("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
    Assert.assertEquals(linkedHashSet,
			StringUtils.commaDelimitedListToSet("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"));
  }

  @Test
  public void commaDelimitedListToSetInputNullOutputNotNull() {
  	Assert.assertEquals(new LinkedHashSet<String>(),
			StringUtils.commaDelimitedListToSet(null));
  }

  @Test
  public void commaDelimitedListToStringArrayInputNotNullOutput1() {
  	Assert.assertArrayEquals(new String[] {"/"},
			StringUtils.commaDelimitedListToStringArray("/"));
  }

  @Test
  public void commaDelimitedListToStringArrayInputNotNullOutput2() {
  	Assert.assertArrayEquals(new String[] {"", ""},
			StringUtils.commaDelimitedListToStringArray(","));
  }

  @Test
  public void concatenateStringArraysInput00Output0() {
  	Assert.assertArrayEquals(new String[] {},
			StringUtils.concatenateStringArrays(new String[]{}, new String[]{}));
  }

  @Test
  public void concatenateStringArraysInput1NullOutput1() {
  	Assert.assertArrayEquals(new String[] {null},
			StringUtils.concatenateStringArrays(new String[]{null}, null));
  }

  @Test
  public void containsWhitespaceInputNotNullOutputFalse() {
  	Assert.assertFalse(StringUtils.containsWhitespace((CharSequence) "\u0000"));
  }

  @Test
  public void containsWhitespaceInputNotNullOutputFalse2() {
  	Assert.assertFalse(StringUtils.containsWhitespace((CharSequence) ""));
  }

  @Test
  public void containsWhitespaceInputNotNullOutputTrue() {
  	Assert.assertTrue(StringUtils.containsWhitespace((CharSequence) "\u0000\u0000\n"));
  }

  @Test
  public void containsWhitespaceInputNullOutputFalse() {
    Assert.assertFalse(StringUtils.containsWhitespace(null));
  }

  @Test
  public void containsWhitespaceInputNullOutputFalse2() {
    Assert.assertFalse(StringUtils.containsWhitespace(null));
  }

  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputPositive() {
    Assert.assertEquals(
        1,
        StringUtils.countOccurrencesOf("333333333333333333333333211111111111111111111111",
                                       "3333333333333333333333332"));
  }

  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputZero() {
    Assert.assertEquals(0, StringUtils.countOccurrencesOf("foo", "a\'b\'c"));
  }

  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputZero2() {
    Assert.assertEquals(0, StringUtils.countOccurrencesOf("!!!!!", ""));
  }

  @Test
  public void countOccurrencesOfInputNotNullNotNullOutputZero3() {
    Assert.assertEquals(0, StringUtils.countOccurrencesOf("", "!"));
  }

  @Test
  public void deleteAnyInputNullNotNullOutputNull() {
    Assert.assertNull(StringUtils.deleteAny(
        null, "\uffe1\uffe0??????????????????????????????????????????????????????????????"));
  }

  @Test
  public void deleteInputNotNullNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.delete("/", "foo"));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNotNullOutput0() {
  	Assert.assertArrayEquals(new String[] {},
			StringUtils.delimitedListToStringArray("", "", "}}{ooooo~~"));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNotNullOutput02() {
  	Assert.assertArrayEquals(new String[] {},
			StringUtils.delimitedListToStringArray("", "!!!!!!!!", "}}#ooooo~~"));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNullOutput1() {
  	Assert.assertArrayEquals(new String[] {"\u0000"},
			StringUtils.delimitedListToStringArray("\u0000", "", null));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullNullOutput2() {
  	Assert.assertArrayEquals(new String[] {"", ""},
			StringUtils.delimitedListToStringArray("\u01fc", "\u01fc", null));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullOutput1() {
  	Assert.assertArrayEquals(new String[] {"a/b/c"},
			StringUtils.delimitedListToStringArray("a/b/c", "a\'b\'c"));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullOutput2() {
    final String str =
        "\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u5fee";
    final String delimiter =
        "\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef\u7fef" +
				"\u5fee";
    Assert.assertArrayEquals(new String[] {"", ""},
			  StringUtils.delimitedListToStringArray(str, delimiter));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNotNullOutput12() {
  	Assert.assertArrayEquals(new String[] {"Y"},
			StringUtils.delimitedListToStringArray("Y", ""));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNullNullOutput1() {
	  Assert.assertArrayEquals(new String[] {"?"},
			  StringUtils.delimitedListToStringArray("?", null, null));
  }

  @Test
  public void delimitedListToStringArrayInputNotNullNullOutput1() {
	  Assert.assertArrayEquals(new String[] {""},
			  StringUtils.delimitedListToStringArray("", null));
  }

  @Test
  public void delimitedListToStringArrayInputNullNullNullOutput0() {
	  Assert.assertArrayEquals(new String[] {},
			  StringUtils.delimitedListToStringArray(null, null, null));
  }

  @Test
  public void endsWithIgnoreCaseInputNotNullNotNullOutputFalse2() {
    Assert.assertFalse(StringUtils.endsWithIgnoreCase("/", "a\'b\'c"));
  }

  @Test
  public void getFilenameExtensionInputNotNullOutputNotNull() {
    Assert.assertEquals(
        "\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1",
        StringUtils.getFilenameExtension(
            "...\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
					"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1"));
  }

  @Test
  public void getFilenameExtensionInputNotNullOutputNull() {
    Assert.assertNull(StringUtils.getFilenameExtension("foo"));
  }

  @Test
  public void getFilenameExtensionInputNotNullOutputNull2() {
    Assert.assertNull(StringUtils.getFilenameExtension(
        "...................////\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1\uffd1" +
				"\uffd1"));
  }

  @Test
  public void getFilenameExtensionInputNullOutputNull() {
    Assert.assertNull(StringUtils.getFilenameExtension(null));
  }

  @Test
  public void getFilenameInputNotNullOutputNotNull() {
    Assert.assertEquals("foo", StringUtils.getFilename("foo"));
  }

  @Test
  public void getFilenameInputNotNullOutputNotNull2() {
    Assert.assertEquals(
        "",
        StringUtils.getFilename(
            "///////////////////////////////////////////////////////////" +
					"/////////////////////////////////////////"));
  }

  @Test
  public void getFilenameInputNullOutputNull() {
    Assert.assertNull(StringUtils.getFilename(null));
  }

  @Test
  public void hasLengthInputNotNullOutputFalse() {
  	Assert.assertFalse(StringUtils.hasLength((CharSequence) ""));
  }

  @Test
  public void hasLengthInputNotNullOutputFalse2() {
    Assert.assertFalse(StringUtils.hasLength(""));
  }

  @Test
  public void hasLengthInputNotNullOutputTrue() {
    Assert.assertTrue(StringUtils.hasLength("/"));
  }

  @Test
  public void hasLengthInputNotNullOutputTrue2() {
  	Assert.assertTrue(StringUtils.hasLength((CharSequence) "?"));
  }

  @Test
  public void hasLengthInputNullOutputFalse() {
    Assert.assertFalse(StringUtils.hasLength(null));
  }

  @Test
  public void hasTextInputNotNullOutputFalse() {
    Assert.assertFalse(StringUtils.hasText("\n\n"));
  }

  @Test
  public void hasTextInputNotNullOutputTrue() {
    Assert.assertTrue(StringUtils.hasText("foo"));
  }

  @Test
  public void hasTextInputNotNullOutputTrue2() {
    Assert.assertTrue(StringUtils.hasText("\n\u0000\u0000??"));
  }

  @Test
  public void hasTextInputNotNullOutputTrue3() {
    final CharSequence str =
        "\n\u0000\u0000?????????????????????????????????????????????????????????????";
    Assert.assertTrue(StringUtils.hasText(str));
  }

  @Test
  public void hasTextInputNullOutputFalse() {
    Assert.assertFalse(StringUtils.hasText(null));
  }

  @Test
  public void hasTextInputNullOutputFalse2() {
    Assert.assertFalse(StringUtils.hasText(null));
  }

  @Test
  public void mergeStringArraysInput00Output0() {
  	Assert.assertArrayEquals(new String[] {},
			  StringUtils.mergeStringArrays(new String[]{}, new String[]{}));
  }

  @Test
  public void mergeStringArraysInput1NullOutput1() {
  	Assert.assertArrayEquals(new String[] {null},
			  StringUtils.mergeStringArrays(new String[]{null}, null));
  }

  @Test
  public void mergeStringArraysInput12Output2() {
  	Assert.assertArrayEquals(new String[] {"", null},
			StringUtils.mergeStringArrays(new String[]{""}, new String[]{null, null}));
  }

  @Test
  public void parseTimeZoneStringInputNotNullOutputIllegalArgumentException() {
  	thrown.expect(IllegalArgumentException.class);
    StringUtils.parseTimeZoneString("a\'b\'c");
  }

  @Test
  public void parseTimeZoneStringInputNotNullOutputNotNull() {
  	Assert.assertEquals("America/Los_Angeles",
			((ZoneInfo) StringUtils.parseTimeZoneString("America/Los_Angeles")).getID());
    Assert.assertEquals(-28800000,
			((ZoneInfo) StringUtils.parseTimeZoneString("America/Los_Angeles")).getRawOffset());
  }

  @Test
  public void pathEqualsInputNotNullNullOutputFalse() {
    Assert.assertFalse(StringUtils.pathEquals("", null));
  }

  @Test
  public void pathEqualsInputNotNullNullOutputFalse2() {
    Assert.assertFalse(StringUtils.pathEquals("/", null));
  }

  @Test
  public void quoteIfStringInputNotNullOutputNotNull() {
    Assert.assertEquals("\'/\'", StringUtils.quoteIfString("/"));
  }

  @Test
  public void quoteInputNotNullOutputNotNull() {
    Assert.assertEquals("\'\'\'", StringUtils.quote("\'"));
  }

  @Test
  public void removeDuplicateStringsInput0Output0() {
  	Assert.assertArrayEquals(new String[] {},
			  StringUtils.removeDuplicateStrings(new String[]{}));
  }

  @Test
  public void replaceInputNotNullNotNullNotNullOutputNotNull() {
    Assert.assertEquals("", StringUtils.replace("", "!", ""));
  }

  @Test
  public void sortStringArrayInput0Output0() {
  	Assert.assertArrayEquals(new String[] {}, StringUtils.sortStringArray(new String[]{}));
  }

  @Test
  public void sortStringArrayInput2Output2() {
    final String[] array = {
        "\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd",
        "\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
				"\ufbfd"};
    final String[] actual = StringUtils.sortStringArray(array);
    Assert.assertArrayEquals(
        new String[] {
            "\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd",
            "\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd" +
					"\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd\ufbfd"},
        actual);
  }

  @Test
  public void splitArrayElementsIntoPropertiesInput0NotNullNotNullOutputNull() {
  	Assert.assertNull(StringUtils.splitArrayElementsIntoProperties(new String[]{},
			  "foo", "foo"));
  }

  @Test
  public void splitArrayElementsIntoPropertiesInput0NotNullOutputNull() {
  	Assert.assertNull(StringUtils.splitArrayElementsIntoProperties(new String[]{},
			  "a/b/c"));
  }

  @Test
  public void splitArrayElementsIntoPropertiesInput1NullNotNullOutput0() {
  	Assert.assertEquals(new Properties(),
			  StringUtils.splitArrayElementsIntoProperties(new String[]{null},
					  null, "?"));
  }

  @Test
  public void splitArrayElementsIntoPropertiesInput1NullNullOutput0() {
  	Assert.assertEquals(new Properties(),
			StringUtils.splitArrayElementsIntoProperties(new String[]{null}, null, null));
  }

  @Test
  public void splitArrayElementsIntoPropertiesInput1NullOutput0() {
  	Assert.assertEquals(new Properties(),
			  StringUtils.splitArrayElementsIntoProperties(new String[]{null}, null));
  }

  @Test
  public void splitInputNotNullNotNullOutput2() {
  	Assert.assertArrayEquals(new String[] {"", ""},
			StringUtils.split("/", "/"));
  }

  @Test
  public void splitInputNotNullNotNullOutputNull() {
    Assert.assertNull(StringUtils.split("/", ","));
  }

  @Test
  public void splitInputNullNullOutputNull() {
    Assert.assertNull(StringUtils.split(null, null));
  }

  @Test
  public void startsWithIgnoreCaseInputNotNullNotNullOutputFalse2() {
    Assert.assertFalse(StringUtils.startsWithIgnoreCase("/", "foo"));
  }

  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull() {
    Assert.assertEquals("foo", StringUtils.stripFilenameExtension("foo"));
  }

  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull2() {
    Assert.assertEquals("./", StringUtils.stripFilenameExtension("./"));
  }

  @Test
  public void stripFilenameExtensionInputNotNullOutputNotNull3() {
    Assert.assertEquals(
        "//////////////////////////////////////////////////////////////////////////////////////\u0000\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083",
        StringUtils.stripFilenameExtension(
            "//////////////////////////////////////////////////////////////////////////////////////\u0000\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083\u0083.\u402e"));
  }

  @Test
  public void stripFilenameExtensionInputNullOutputNull() {
    Assert.assertNull(StringUtils.stripFilenameExtension(null));
  }

  @Test
  public void substringMatchInputNotNullNegativeNotNullOutputStringIndexOutOfBoundsException() {
  	thrown.expect(StringIndexOutOfBoundsException.class);
    StringUtils.substringMatch("?", -2077277182, "\u0000\u0000\u0000");
  }

  @Test
  public void substringMatchInputNotNullNegativeNotNullOutputTrue() {
  	Assert.assertTrue(StringUtils.substringMatch("?", -2077277182, ""));
  }

  @Test
  public void substringMatchInputNotNullPositiveNotNullOutputFalse() {
  	Assert.assertFalse(StringUtils.substringMatch("\uffe0",
			1015874, "\u0000\u0000\u0000"));
  }

  @Test
  public void substringMatchInputNotNullPositiveNotNullOutputFalse2() {
    final CharSequence str =
        "\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0" +
				"\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0" +
				"\uffe0\uffe0\uffe1?????????????????????????????????????????????????????";
    final CharSequence substring =
        "\u0000\u0000\uffe0????????????????????????????????????????????????";
    Assert.assertFalse(StringUtils.substringMatch(str, 21, substring));
  }

  @Test
  public void substringMatchInputNotNullPositiveNotNullOutputFalse3() {
    final CharSequence str =
        "\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0" +
				"\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0\uffe0" +
				"\uffe0\uffe0\uffe1?????????????????????????????????????????????????????";
    final CharSequence substring =
        "\uffe0\u0000\uffe0????????????????????????????????????????????????";
    Assert.assertFalse(StringUtils.substringMatch(str, 21, substring));
  }

  @Test
  public void tokenizeToStringArrayInputNullNullFalseFalseOutputNull() {
    Assert.assertNull(StringUtils.tokenizeToStringArray(null, null, false, false));
  }

  @Test
  public void tokenizeToStringArrayInputNullNullOutputNull() {
    Assert.assertNull(StringUtils.tokenizeToStringArray(null, null));
  }

  @Test
  public void trimAllWhitespaceInputNullOutputNull() {
    Assert.assertNull(StringUtils.trimAllWhitespace(null));
  }

  @Test
  public void trimArrayElementsInput0Output0() {
  	Assert.assertArrayEquals(new String[] {},
  	StringUtils.trimArrayElements(new String[]{}));
  }

  @Test
  public void trimArrayElementsInput1Output1() {
  	Assert.assertArrayEquals(new String[] {null},
			StringUtils.trimArrayElements(new String[]{null}));
  }

  @Test
  public void trimArrayElementsInput1Output12() {
  	Assert.assertArrayEquals(new String[] {"!``"},
			StringUtils.trimArrayElements(new String[]{"                !``        "}));
  }

  @Test
  public void trimLeadingCharacterInputNotNullNotNullOutputNotNull() {
    Assert.assertEquals("foo", StringUtils.trimLeadingCharacter("foo", '!'));
  }

  @Test
  public void trimLeadingCharacterInputNotNullNotNullOutputNotNull2() {
    Assert.assertEquals("", StringUtils.trimLeadingCharacter("", '\u0000'));
  }

  @Test
  public void trimLeadingCharacterInputNotNullNotNullOutputNotNull3() {
    Assert.assertEquals("", StringUtils.trimLeadingCharacter("\u0001", '\u0001'));
  }

  @Test
  public void trimLeadingWhitespaceInputNotNullOutputNotNull() {
    Assert.assertEquals("foo", StringUtils.trimLeadingWhitespace("foo"));
  }

  @Test
  public void trimLeadingWhitespaceInputNotNullOutputNotNull2() {
    Assert.assertEquals("", StringUtils.trimLeadingWhitespace("\n"));
  }

  @Test
  public void trimLeadingWhitespaceInputNullOutputNull() {
    Assert.assertNull(StringUtils.trimLeadingWhitespace(null));
  }

  @Test
  public void trimTrailingCharacterInputNotNullNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.trimTrailingCharacter("/", '!'));
  }

  @Test
  public void trimTrailingCharacterInputNotNullNotNullOutputNotNull2() {
    Assert.assertEquals("              !",
                        StringUtils.trimTrailingCharacter("              ! ", ' '));
  }

  @Test
  public void trimTrailingCharacterInputNotNullNotNullOutputNotNull3() {
    Assert.assertEquals("", StringUtils.trimTrailingCharacter("%", '%'));
  }

  @Test
  public void trimTrailingCharacterInputNullNotNullOutputNull() {
    Assert.assertNull(StringUtils.trimTrailingCharacter(null, '\uffe1'));
  }

  @Test
  public void trimTrailingWhitespaceInputNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.trimTrailingWhitespace("/"));
  }

  @Test
  public void trimTrailingWhitespaceInputNotNullOutputNotNull2() {
    Assert.assertEquals("", StringUtils.trimTrailingWhitespace("\u2028"));
  }

  @Test
  public void trimTrailingWhitespaceInputNullOutputNull() {
    Assert.assertNull(StringUtils.trimTrailingWhitespace(null));
  }

  @Test
  public void trimWhitespaceInputNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.trimWhitespace("/"));
  }

  @Test
  public void trimWhitespaceInputNotNullOutputNotNull2() {
    Assert.assertEquals("", StringUtils.trimWhitespace(""));
  }

  @Test
  public void trimWhitespaceInputNotNullOutputNotNull3() {
    Assert.assertEquals("", StringUtils.trimWhitespace("\u2005\u001f"));
  }

  @Test
  public void trimWhitespaceInputNotNullOutputNotNull4() {
    Assert.assertEquals("\b", StringUtils.trimWhitespace("\b\u000b"));
  }

  @Test
  public void uncapitalizeInputNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.uncapitalize("/"));
  }

  @Test
  public void uncapitalizeInputNotNullOutputNotNull2() {
    final String str = "V\u0000";
    final String actual = StringUtils.uncapitalize(str);
    Assert.assertEquals("v\u0000", actual);
  }

  @Test
  public void uncapitalizeInputNullOutputNull() {
    Assert.assertNull(StringUtils.uncapitalize(null));
  }

  @Test
  public void unqualifyInputNotNullNotNullOutputNotNull() {
    Assert.assertEquals("", StringUtils.unqualify("/", '/'));
  }

  @Test
  public void unqualifyInputNotNullOutputNotNull() {
    Assert.assertEquals("/", StringUtils.unqualify("/"));
  }
}
