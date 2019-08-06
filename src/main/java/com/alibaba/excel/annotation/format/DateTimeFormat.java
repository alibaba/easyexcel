package com.alibaba.excel.annotation.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Convert date format.
 *
 * <li>write: It can be used on classes {@link java.util.Date}
 * <li>read: It can be used on classes {@link String}
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
     * @return
     */
    String value() default "";

    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * @return
     */
    boolean use1904windowing() default false;
}
