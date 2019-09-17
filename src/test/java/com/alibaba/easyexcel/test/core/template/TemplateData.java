package com.alibaba.easyexcel.test.core.template;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class TemplateData {
    @ExcelProperty("字符串0")
    private String string0;
    @ExcelProperty("字符串1")
    private String string1;
}
