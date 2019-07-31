package com.alibaba.excel.analysis.v07;

import java.util.Map;

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
    Map<Integer, CellData> getCurRowContent();
}
