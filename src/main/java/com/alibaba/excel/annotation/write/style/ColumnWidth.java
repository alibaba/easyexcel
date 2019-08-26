package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set the width of the table
 *
 * @author Jiaju Zhuang
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnWidth {

    /**
     * Column width
     * <p>
     * -1 means the default column width is used
     *
     * @return Column width
     */
    int value() default -1;
}
