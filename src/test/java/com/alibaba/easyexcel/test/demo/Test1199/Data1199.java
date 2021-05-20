package com.alibaba.easyexcel.test.demo.Test1662;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Data1662 {
    @ExcelProperty(index = 0)
    private String str;
    @ExcelProperty(index = 1)
    private Date date;
    @ExcelProperty(index = 2)
    private double r;
}
