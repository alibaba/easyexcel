package com.alibaba.easyexcel.test.wirte.simple;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author zhuangjiaju
 */
public class SimpleData {
    @ExcelProperty("字符串")
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
