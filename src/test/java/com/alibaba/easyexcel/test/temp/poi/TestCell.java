package com.alibaba.easyexcel.test.temp.poi;

import java.util.List;

import com.alibaba.excel.metadata.data.CellData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author 罗成
 **/
@Getter
@Setter
@EqualsAndHashCode
public class TestCell {
    private CellData<?> c1;
    private CellData<List<String>> c2;
}
