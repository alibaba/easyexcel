package com.alibaba.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;

/**
 * @author jipengfei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelProperty {

    /**
     * The name of the sheet header.
     *
     * <li>write: It automatically merges when you have more than one head
     * <li>read: When you have multiple heads, take the first one
     *
     * @return
     */
    String[] value() default {""};

    /**
     * Index of column
     *
     * Read or write it on the index of column,If it's equal to -1, it's sorted by Java class
     *
     * @return
     */
    int index() default -1;

    /**
     * Force the current field to use this converter.
     *
     * @return
     */
    Class<? extends Converter> converter() default AutoConverter.class;

    /**
     *
     * default @see com.alibaba.excel.util.TypeUtil if default is not meet you can set format
     *
     * @return
     * @deprecated please use {@link com.alibaba.excel.annotation.format.DateTimeFormat}
     */
    @Deprecated
    String format() default "";
}
