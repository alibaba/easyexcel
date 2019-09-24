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
     * @param ch
     * @param start
     * @param length
     */
    void appendCurrentCellValue(char[] ch, int start, int length);

    /**
     * Get row content
     *
     * @return
     */
    Map<Integer, CellData> getCurRowContent();
}
