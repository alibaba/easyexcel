package com.alibaba.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
     *
     * @return
     */
    int value();

    /**
     * Default @see com.alibaba.excel.util.TypeUtil
     * if default is not  meet you can set format
     *
     * @return
     */
    String format() default "";

    /**
     * according the JSON convert key to value;
     * =====================================
     * Default JSON format: {'k1':'v1','k2':'v2'}
     *
     * @return java.lang.String
     * @author Muscleape
     * @date 2019/4/24 22:50
     */
    String keyValue() default "";
}
