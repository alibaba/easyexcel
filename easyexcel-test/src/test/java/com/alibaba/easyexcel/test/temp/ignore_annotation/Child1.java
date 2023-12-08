package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author atdt
 */
@Data
public class Child1 extends ParentWithPropertyAnnotation {
    @ExcelProperty("地址")
    private String address;

    @ExcelIgnore
    private String phone;
}
