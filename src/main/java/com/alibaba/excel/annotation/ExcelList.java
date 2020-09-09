package com.alibaba.excel.annotation;

import com.alibaba.excel.enums.DynamicDirectionEnum;

import java.lang.annotation.*;

/**
 * @author dff on 2020-09-07
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelList {
    /**
     * the direction of dynamic colletion
     * default orientation: dynamic column
     * portrait: dynamic row
     * @return
     */
    DynamicDirectionEnum direction() default DynamicDirectionEnum.PORTRAIT;
}
