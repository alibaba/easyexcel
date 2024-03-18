package com.alibaba.excel.annotation;

import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.*;

/**
 *  Annotate embedded sub-objects
 *
 * @author bigNoseCat
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelSubData {

    /**
     * 分组按照颜色梯度来区分
     */
    IndexedColors[] fillForegroundColors() default {};

}
