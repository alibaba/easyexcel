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
     * The direction of dynamic colletion
     *
     * Portrait(default): dynamic row
     *
     * Orientation: dynamic column
     *
     * @return The direction of dynamic colletion
     */
    DynamicDirectionEnum direction() default DynamicDirectionEnum.PORTRAIT;
}
