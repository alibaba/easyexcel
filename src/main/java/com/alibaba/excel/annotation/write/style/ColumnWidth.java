package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set the width of the table
 * 
 * @author zhuangjiaju
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ColumnWidth {
    /**
     * Using the Calibri font as an example, the maximum digit width of 11 point font size is 7 pixels (at 96 dpi). If
     * you set a column width to be 8 characters wide, e.g. <code>8*256</code>
     *
     * <p>
     * -1 mean the auto set width
     */
    int value() default -1;
}
