package com.alibaba.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

/**
 * Convert number format.
 *
 * <li>write: It can be used on classes that inherit {@link Number}
 * <li>read: It can be used on classes {@link String}
 *
 * @author Jiaju Zhuang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NumberFormat {

    /**
     *
     * Specific format reference {@link org.apache.commons.math3.fraction.BigFractionFormat}
     *
     * @return
     */
    String value() default "";

    /**
     * Rounded by default
     *
     * @return
     */
    RoundingMode roundingMode() default RoundingMode.HALF_UP;
}
