package com.alibaba.excel.analysis.v07;

public interface XlsxRowResultHolder {
    void clearResult();
    
    void appendCurrentCellValue(String currentCellValue);
    
    String[] getCurRowContent();
    
    int getColumnSize();
}
