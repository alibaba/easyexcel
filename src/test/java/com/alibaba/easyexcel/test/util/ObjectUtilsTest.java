package com.alibaba.excel.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjectUtilsTest {

  @Rule public final ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void containsConstantInput0NotNullFalseOutputFalse() {
	  Assert.assertFalse(ObjectUtils.containsConstant(new Enum[]{}, "/", false));
  }

  @Test
  public void containsConstantInput0NotNullOutputFalse() {
  	Assert.assertFalse(ObjectUtils.containsConstant(new Enum[]{}, "/"));
  }

  @Test
  public void containsElementInput0NegativeOutputFalse() {
  	Assert.assertFalse(ObjectUtils.containsElement(new Object[]{}, -999998));
  }

  @Test
  public void containsElementInput1NullOutputTrue() {
  	Assert.assertTrue(ObjectUtils.containsElement(new Object[]{null}, null));
  }

  @Test
  public void containsElementInput1ZeroOutputFalse() {
	Assert.assertFalse(ObjectUtils.containsElement(new Object[]{null}, 0));
  }

  @Test
  public void containsElementInputNullZeroOutputFalse() {
    Assert.assertFalse(ObjectUtils.containsElement(null, 0));
  }

  @Test
  public void getDisplayStringInputNegativeOutputNotNull() {
    Assert.assertEquals("-8256", ObjectUtils.getDisplayString(-8256));
  }

    @Test
  public void getDisplayStringInputNullOutputNotNull() {
    Assert.assertEquals("", ObjectUtils.getDisplayString(null));
  }

  @Test
  public void hashCodeInputPositiveOutputPositive3() {
    Assert.assertEquals(2, ObjectUtils.hashCode(2L));
  }

  @Test
  public void hashCodeInputTrueOutputPositive() {
    Assert.assertEquals(1231, ObjectUtils.hashCode(true));
  }

  @Test
  public void hashCodeInputZeroOutputZero() {
    Assert.assertEquals(0, ObjectUtils.hashCode(0.0f));
  }
  
  @Test
  public void identityToStringInputNullOutputNotNull() {
    Assert.assertEquals("", ObjectUtils.identityToString(null));
  }
  
  @Test
  public void isArrayInputNegativeOutputFalse() {
    Assert.assertFalse(ObjectUtils.isArray(-999998));
  }
  
  @Test
  public void isCheckedExceptionInputNullOutputTrue() {
    Assert.assertTrue(ObjectUtils.isCheckedException(null));
  }

  @Test
  public void isEmptyInput0OutputTrue() {
  	Assert.assertTrue(ObjectUtils.isEmpty(new Object[]{}));
  }

  @Test
  public void isEmptyInput0OutputTrue2() {
  	Assert.assertTrue(ObjectUtils.isEmpty(new ArrayList()));
  }
  
  @Test
  public void isEmptyInputNotNullOutputFalse() {
  	Assert.assertFalse(ObjectUtils.isEmpty("??"));
  }
  
  @Test
  public void isEmptyInputNotNullOutputTrue() {
	Assert.assertTrue(ObjectUtils.isEmpty(""));
  }
  
  @Test
  public void isEmptyInputNullOutputTrue() {
    Assert.assertTrue(ObjectUtils.isEmpty(null));
  }

  @Test
  public void nullSafeClassNameInputNegativeOutputNotNull() {
    Assert.assertEquals("java.lang.Integer", ObjectUtils.nullSafeClassName(-999998));
  }

  @Test
  public void nullSafeEqualsInputNegativeNegativeOutputFalse() {
    Assert.assertFalse(ObjectUtils.nullSafeEquals(-999997, -999998));
  }
  
  @Test
  public void nullSafeEqualsInputNegativeNegativeOutputTrue() {
    Assert.assertTrue(ObjectUtils.nullSafeEquals(-999998, -999998));
  }

  @Test
  public void nullSafeEqualsInputNullNullOutputTrue() {
    Assert.assertTrue(ObjectUtils.nullSafeEquals(null, null));
  }

  @Test
  public void nullSafeEqualsInputZeroNullOutputFalse() {
    Assert.assertFalse(ObjectUtils.nullSafeEquals(0, null));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new Object[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive2() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new boolean[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive3() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new byte[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive4() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new char[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive5() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new double[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive6() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new float[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive7() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new int[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive8() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new long[]{}));
  }

  @Test
  public void nullSafeHashCodeInput0OutputPositive9() {
	Assert.assertEquals(7,
			  ObjectUtils.nullSafeHashCode(new short[]{}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputPositive() {
	Assert.assertEquals(217,
			  ObjectUtils.nullSafeHashCode(new Object[]{null}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputPositive2() {
	Assert.assertEquals(217,
			  ObjectUtils.nullSafeHashCode(new Object[]{0}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputPositive3() {
	Assert.assertEquals(2077,
			  ObjectUtils.nullSafeHashCode(new char[]{'\u0744'}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputPositive4() {
	Assert.assertEquals(217,
			  ObjectUtils.nullSafeHashCode(new double[]{0.0}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputPositive6() {
	Assert.assertEquals(217,
			  ObjectUtils.nullSafeHashCode(new long[]{0L}));
  }

  @Test
  public void nullSafeHashCodeInput1OutputZero() {
	Assert.assertEquals(0,
			  ObjectUtils.nullSafeHashCode(new short[]{(short)-217}));
  }
  
  @Test
  public void nullSafeHashCodeInput2OutputPositive() {
	Assert.assertEquals(46311,
			  ObjectUtils.nullSafeHashCode(new boolean[]{false, false}));
  }
  
  @Test
  public void nullSafeHashCodeInput2OutputPositive2() {
  	Assert.assertEquals(46119,
			  ObjectUtils.nullSafeHashCode(new boolean[]{true, true}));
  }

  @Test
  public void nullSafeHashCodeInput2OutputPositive3() {
	Assert.assertEquals(2736,
			  ObjectUtils.nullSafeHashCode(new byte[]{(byte)-125, (byte)-116}));
  }

  @Test
  public void nullSafeHashCodeInput2OutputZero() {
	Assert.assertEquals(0,
			  ObjectUtils.nullSafeHashCode(new int[]{-1246757081, -5242880}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new Object[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull2() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new boolean[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull3() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new byte[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull4() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new char[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull5() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new int[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull6() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new long[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull7() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new short[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull8() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new double[]{}));
  }

  @Test
  public void nullSafeToStringInput0OutputNotNull9() {
	Assert.assertEquals("{}",
			  ObjectUtils.nullSafeToString(new float[]{}));
  }

  @Test
  public void nullSafeToStringInput1OutputNotNull2() {
	Assert.assertEquals("{43}",
			  ObjectUtils.nullSafeToString(new byte[]{(byte)43}));
  }

  @Test
  public void nullSafeToStringInput1OutputNotNull3() {
	Assert.assertEquals("{\'\u0001\'}",
			  ObjectUtils.nullSafeToString(new char[]{'\u0001'}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull() {
	Assert.assertEquals("{true, true}",
			  ObjectUtils.nullSafeToString(new boolean[]{true, true}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull3() {
	Assert.assertEquals("{43, -8}",
			  ObjectUtils.nullSafeToString(new byte[]{(byte)43, (byte)-8}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull4() {
	Assert.assertEquals("{-49152, -1215752192}",
			  ObjectUtils.nullSafeToString(new int[]{-49152, -1215752192}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull5() {
	Assert.assertEquals("{\'\u0001\', \'\u0001\'}",
			  ObjectUtils.nullSafeToString(new char[]{'\u0001', '\u0001'}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull6() {
	Assert.assertEquals("{7, 5}",
			  ObjectUtils.nullSafeToString(new long[]{7L, 5L}));
  }

  @Test
  public void nullSafeToStringInput2OutputNotNull7() {
	  Assert.assertEquals("{43, -16384}",
			  ObjectUtils.nullSafeToString(new short[]{(short)43, (short)-16384}));
  }

  @Test
  public void toObjectArrayInputNullOutput0() {
	Assert.assertArrayEquals(new Object[] {}, ObjectUtils.toObjectArray(null));
  }
}
