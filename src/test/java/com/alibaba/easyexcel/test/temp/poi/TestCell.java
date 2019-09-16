package com.alibaba.easyexcel.test.temp.poi;

import java.util.List;

import com.alibaba.excel.metadata.CellData;

import lombok.Data;

/**
 * TODO
 *
 * @author 罗成
 **/
@Data
public class TestCell {
    private CellData c1;
    private CellData<List<String>> c2;
}
