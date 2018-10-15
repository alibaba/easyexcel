package com.alibaba.excel.annotation;

import java.lang.annotation.*;

/**
 * Created by jipengfei on 17/3/19.
 * Field column num at excel head
 *
 * @author jipengfei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelColumnNum {

    /**
     * col num
     * @return
     */
    int value();

    /**
     *
     * Default @see com.alibaba.excel.util.TypeUtil
     * if default is not  meet you can set format
     *
     * @return
     */
    String format() default "";
}
