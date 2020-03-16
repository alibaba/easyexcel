package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The regions of the loop merge
 *
 * @author Jiaju Zhuang
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ContentLoopMerge {
    /**
     * Each row
     *
     * @return
     */
    int eachRow() default -1;

    /**
     * Extend column
     *
     * @return
     */
    int columnExtend() default 1;
}
