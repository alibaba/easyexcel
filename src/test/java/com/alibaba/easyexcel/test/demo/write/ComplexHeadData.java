package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * 复杂头数据.这里最终效果是第一行就一个主标题，第二行分类
 *
 * @author Jiaju Zhuang
 **/
public class ComplexHeadData {
    private final static String TITLE = "主标题";
    @ExcelProperty({TITLE, "字符串标题"})
    private String string;
    @ExcelProperty({TITLE, "日期标题"})
    private Date date;
    @ExcelProperty({TITLE, "数字标题"})
    private Double doubleData;
}
