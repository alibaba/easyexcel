package com.alibaba.easyexcel.test.demo.write;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * 基础数据类.行高 列宽可以忽略
 *
 * @author zhuangjiaju
 **/
@Data
public class DemoData {

    @ExcelProperty("字符串标题")
    private String string;
    /**
     * 指定写到excel的格式
     */
    @ExcelProperty("日期标题")
    private Date date;
    /**
     * 数字转成百分比，默认数字存到excel是数字，不存在格式，现在要变成百分比就变成文本了，所以要指定转换器
     */
    @ExcelProperty("数字标题")
    private Double doubleData;
}
