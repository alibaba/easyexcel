package com.alibaba.excel.analysis.v07;

import com.alibaba.excel.metadata.CellData;

public interface XlsxRowResultHolder {
    void clearResult();
    
    void appendCurrentCellValue(String currentCellValue);

    CellData[] getCurRowContent();
    
    int getColumnSize();
}
