package com.alibaba.easyexcel.test.core.simple;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author zhuangjiaju
 */
@Data
public class SimpleData {
    @ExcelProperty("字符串1")
    private String string1;
    @ExcelProperty("字符串2")
    private String string2;
}
