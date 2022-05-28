package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set the height of each table
 *
 * @author Jiaju Zhuang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HeadRowHeight {
    /**
     * Set the header height
     * <p>
     * -1 mean the auto set height
     *
     * @return Header height
     */
    short value() default -1;
}
