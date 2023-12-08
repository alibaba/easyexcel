package com.alibaba.easyexcel.test.temp.ignore_annotation;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author atdt
 */
@Data
public class ParentWithIgnoreAnnotation {
    @ExcelProperty("名字")
    private String name;

    @ExcelIgnore
    private String phone;
}
