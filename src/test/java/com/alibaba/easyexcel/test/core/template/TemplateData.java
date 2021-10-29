package com.alibaba.easyexcel.test.core.template;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class TemplateData {
    @ExcelProperty("字符串0")
    private String string0;
    @ExcelProperty("字符串1")
    private String string1;
}
