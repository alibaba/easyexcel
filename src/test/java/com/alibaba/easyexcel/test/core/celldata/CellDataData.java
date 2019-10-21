package com.alibaba.easyexcel.test.core.celldata;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.CellData;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class CellDataData {
    @DateTimeFormat("yyyy年MM月dd日")
    private CellData<Date> date;
    private CellData<Integer> integer1;
    private Integer integer2;
    private CellData formulaValue;
}
