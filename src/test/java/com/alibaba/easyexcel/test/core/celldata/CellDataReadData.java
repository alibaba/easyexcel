package com.alibaba.easyexcel.test.core.celldata;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.data.ReadCellData;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class CellDataReadData {
    @DateTimeFormat("yyyy年MM月dd日")
    private ReadCellData<String> date;
    private ReadCellData<Integer> integer1;
    private Integer integer2;
    private ReadCellData<?> formulaValue;
}
