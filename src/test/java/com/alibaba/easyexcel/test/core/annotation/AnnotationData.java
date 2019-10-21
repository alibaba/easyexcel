package com.alibaba.easyexcel.test.core.annotation;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.doubleconverter.DoubleStringConverter;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
@ColumnWidth(30)
@HeadRowHeight(15)
@ContentRowHeight(20)
public class AnnotationData {
    @ExcelProperty("日期")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date date;
    @ExcelProperty(value = "数字", converter = DoubleStringConverter.class)
    @NumberFormat("#.##%")
    private Double number;
    @ExcelIgnore
    private String ignore;
    private static final String staticFinal = "test";
    private transient String transientString;
}
