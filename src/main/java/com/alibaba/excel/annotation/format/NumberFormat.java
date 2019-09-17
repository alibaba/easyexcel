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
 * <p>
 * write: It can be used on classes that inherit {@link Number}
 * <p>
 * read: It can be used on classes {@link String}
 *
 * @author Jiaju Zhuang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NumberFormat {

    /**
     *
     * Specific format reference {@link java.text.DecimalFormat}
     *
     * @return Format pattern
     */
    String value() default "";

    /**
     * Rounded by default
     *
     * @return RoundingMode
     */
    RoundingMode roundingMode() default RoundingMode.HALF_UP;
}
