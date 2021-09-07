package com.alibaba.excel.csv;

import org.apache.poi.ss.usermodel.DataFormat;

public class CsvDataFormat implements DataFormat {
    @Override
    public short getFormat(String format) {
        return 0;
    }

    @Override
    public String getFormat(short index) {
        return null;
    }
}
