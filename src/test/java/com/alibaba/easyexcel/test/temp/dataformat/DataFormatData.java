package com.alibaba.easyexcel.test.temp.dataformat;

import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;

import lombok.Data;

/**
 * TODO
 *
 * @author 罗成
 **/
@Data
public class DataFormatData {
    private ReadCellData<String> date;
    private ReadCellData<String> num;
}
