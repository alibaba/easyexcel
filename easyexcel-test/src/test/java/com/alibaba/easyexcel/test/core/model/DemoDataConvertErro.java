package com.alibaba.easyexcel.test.core.model;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

/**
 * @author Jasonyou
 * @date 2024/03/24
 */
public class DemoDataConvertErro {
    //exchange the type of the following  fields in order to produce type conversion errors
    @ExcelProperty(index =0)
    private DemoData string;
    @ExcelProperty(index = 1)
    private DemoData doubleData;
    @ExcelProperty(index = 2)
    private DemoData date;
}
