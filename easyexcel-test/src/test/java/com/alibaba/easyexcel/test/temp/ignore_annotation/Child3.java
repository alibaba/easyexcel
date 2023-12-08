package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author atdt
 */
@Data
public class Child3 extends ParentWithPropertyAnnotation {
    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("电话")
    private String phone;
}
