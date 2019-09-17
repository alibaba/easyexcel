package com.alibaba.easyexcel.test.core.annotation;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class AnnotationIndexAndNameData {
    @ExcelProperty(value = "第四个", index = 4)
    private String index4;
    @ExcelProperty(value = "第二个")
    private String index2;
    @ExcelProperty(index = 0)
    private String index0;
    @ExcelProperty(value = "第一个", index = 1)
    private String index1;
}
