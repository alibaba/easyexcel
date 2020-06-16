package com.alibaba.easyexcel.test.core.excludeorinclude;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class ExcludeOrIncludeData {
    @ExcelProperty(order = 1)
    private String column1;
    @ExcelProperty(order = 2)
    private String column2;
    @ExcelProperty(order = 3)
    private String column3;
    @ExcelProperty(order = 4)
    private String column4;
}
