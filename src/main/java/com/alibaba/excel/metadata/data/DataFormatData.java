package com.alibaba.excel.metadata.data;

import lombok.Data;

/**
 * data format
 *
 * @author Jiaju Zhuang
 */
@Data
public class DataFormatData {
    /**
     * index
     */
    private Short index;

    /**
     * format
     */
    private String format;

    @Override
    public DataFormatData clone() {
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setIndex(getIndex());
        dataFormatData.setFormat(getFormat());
        return dataFormatData;
    }
}
