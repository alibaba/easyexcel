package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author atdt
 */
@Data
public class ParentWithPropertyAnnotation {
    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("电话")
    private String phone;
}
