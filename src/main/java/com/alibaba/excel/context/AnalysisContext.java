package com.alibaba.excel.context;

import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

/**
 *
 * A context is the main anchorage point of a excel reader.
 * 
 * @author jipengfei
 */
public interface AnalysisContext {
    /**
     * Select the current table
     *
     * @param readSheet
     */
    void currentSheet(ReadSheet readSheet);

    /**
     * All information about the workbook you are currently working on
     *
     * @return
     */
    ReadWorkbookHolder currentWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     *
     * @return
     */
    ReadSheetHolder currentSheetHolder();

    /**
     * Row of currently operated cell
     *
     * @return
     */
    ReadRowHolder currentRowHolder();
}
