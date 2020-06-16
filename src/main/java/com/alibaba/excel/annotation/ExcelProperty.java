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
     * <p>
     * write: It automatically merges when you have more than one head
     * <p>
     * read: When you have multiple heads, take the first one
     *
     * @return The name of the sheet header
     */
    String[] value() default {""};

    /**
     * Index of column
     *
     * Read or write it on the index of column,If it's equal to -1, it's sorted by Java class.
     *
     * priority: index &gt; order &gt; default sort
     *
     * @return Index of column
     */
    int index() default -1;

    /**
     * Defines the sort order for an column.
     *
     * priority: index &gt; order &gt; default sort
     *
     * @return Order of column
     */
    int order() default Integer.MAX_VALUE;

    /**
     * Force the current field to use this converter.
     *
     * @return Converter
     */
    Class<? extends Converter> converter() default AutoConverter.class;

    /**
     *
     * default @see com.alibaba.excel.util.TypeUtil if default is not meet you can set format
     *
     * @return Format string
     * @deprecated please use {@link com.alibaba.excel.annotation.format.DateTimeFormat}
     */
    @Deprecated
    String format() default "";
}
