package com.alibaba.easyexcel.test.core.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("浮点数")
    private Double doubleData;
    @ExcelProperty("日期")
    private Date date;

}
