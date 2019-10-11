package com.alibaba.easyexcel.test.demo.read;

import java.util.Date;

import com.alibaba.excel.metadata.CellData;

import lombok.Data;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class CellDataReadDemoData {
    private CellData<String> string;
    // 这里注意 虽然是日期 但是 类型 存储的是number 因为excel 存储的就是number
    private CellData<Date> date;
    private CellData<Double> doubleData;
    // 这里并不一定能完美的获取 有些公式是依赖性的 可能会读不到 这个问题后续会修复
    private CellData<String> formulaValue;
}
