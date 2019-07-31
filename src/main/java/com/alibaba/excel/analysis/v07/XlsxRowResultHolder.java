package com.alibaba.excel.analysis.v07;

import java.util.List;

import com.alibaba.excel.metadata.CellData;

/**
 * Result holder
 *
 * @author jipengfei
 */
public interface XlsxRowResultHolder {
    /**
     * Clear Result
     */
    void clearResult();

    /**
     * Append current 'cellValue'
     *
     * @param currentCellValue
     */
    void appendCurrentCellValue(String currentCellValue);

    /**
     * Get row content
     *
     * @return
     */
    List<CellData> getCurRowContent();

    /**
     * get column size
     *
     * @return
     */
    int getColumnSize();
}
