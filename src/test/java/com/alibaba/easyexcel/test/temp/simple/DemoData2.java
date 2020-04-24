package com.alibaba.easyexcel.test.temp.simple;

import java.util.Date;

import org.apache.poi.ss.usermodel.FillPatternType;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;

import lombok.Data;

@Data
public class DemoData2 {
    @ExcelProperty("字符串标题")
    @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 42)
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
