package com.alibaba.easyexcel.test.temp.dataformat;

import com.alibaba.excel.metadata.data.ReadCellData;

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
public class DataFormatData {
    private ReadCellData<String> date;
    private ReadCellData<String> num;
}
