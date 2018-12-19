package com.alibaba.excel.annotation;

import com.alibaba.excel.metadata.typeconvertor.TypeConvertor;

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
      * @return
      */
     String[] value() default {""};


     /**
      * @return
      */
     int index() default 99999;

     /**
      *
      * default @see com.alibaba.excel.util.TypeUtil
      * if default is not  meet you can set format
      *
      * @return
      */
     String format() default "";

     /**
      * you can define yours TypeConvertor
      * @return
      */
     Class convertor() default TypeConvertor.class;

}
