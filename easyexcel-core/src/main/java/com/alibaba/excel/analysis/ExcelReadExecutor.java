package com.alibaba.excel.analysis;

import java.util.List;

import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * Excel file Executor
 *
 * @author Jiaju Zhuang
 */
public interface ExcelReadExecutor {

    /**
     * Returns the actual sheet in excel
     *
     * @return Actual sheet in excel
     */
    List<ReadSheet> sheetList();

    /**
     * Read the sheet.
     */
    void execute();
}
