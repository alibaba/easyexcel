package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author atdt
 */
@Data
public class Child4 extends ParentWithIgnoreAnnotation {
    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("电话")
    private String phone;
}
