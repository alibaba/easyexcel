package com.alibaba.easyexcel.test.temp.read;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Data
@Accessors(chain = true)
public class HeadReadData {
    @ExcelProperty("头1")
    private String h1;
    @ExcelProperty({"头", "头2"})
    private String h2;
}
