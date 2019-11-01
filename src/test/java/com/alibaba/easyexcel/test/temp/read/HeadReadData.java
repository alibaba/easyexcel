package com.alibaba.easyexcel.test.temp.read;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Data
public class HeadReadData {
    @ExcelProperty("头1")
    private String h1;
    @ExcelProperty({"头", "头2"})
    private String h2;
}
