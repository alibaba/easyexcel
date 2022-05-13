package com.alibaba.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.excel.enums.BooleanEnum;

/**
 * Convert date format.
 *
 * <p>
 * write: It can be used on classes {@link java.util.Date}
 * <p>
 * read: It can be used on classes {@link String}
 *
 * @author Jiaju Zhuang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DateTimeFormat {

    /**
     *
     * Specific format reference {@link java.text.SimpleDateFormat}
     *
     * @return Format pattern
     */
    String value() default "";

    /**
     * True if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * @return True if date uses 1904 windowing, or false if using 1900 date windowing.
     */
    BooleanEnum use1904windowing() default BooleanEnum.DEFAULT;
}
