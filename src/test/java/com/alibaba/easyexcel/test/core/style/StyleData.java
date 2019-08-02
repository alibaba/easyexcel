package com.alibaba.easyexcel.test.core.style;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author zhuangjiaju
 */
@Data
public class StyleData {
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("字符串1")
    private String string1;
}
