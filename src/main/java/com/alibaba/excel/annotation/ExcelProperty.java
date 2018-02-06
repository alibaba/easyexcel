package com.alibaba.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jipengfei
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelProperty {

     /**
      * 某列表头值
      * @return 表头值
      */
     String[] value() default {""};


     /**
      * 列顺序，越小越靠前
      * @return 列顺序
      */
     int index() default 99999;

     /**
      *
      * default @see com.alibaba.TypeUtil
      * if default is not  meet you can set format
      *
      * @return 日期格式化
      */
     String format() default "";
}
