package com.alibaba.excel.context;

import com.alibaba.excel.analysis.ExcelExecutor;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
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
     * @param excelExecutor
     * @param readSheet
     */
    void currentSheet(ExcelExecutor excelExecutor, ReadSheet readSheet);

    /**
     * All information about the workbook you are currently working on
     *
     * @return
     */
    ReadWorkbookHolder readWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     *
     * @return
     */
    ReadSheetHolder readSheetHolder();

    /**
     * Set row of currently operated cell
     * 
     * @param readRowHolder
     */
    void readRowHolder(ReadRowHolder readRowHolder);

    /**
     * Row of currently operated cell
     *
     * @return
     */
    ReadRowHolder readRowHolder();

    /**
     * The current read operation corresponds to the 'readSheetHolder' or 'readWorkbookHolder'
     * 
     * @return
     */
    ReadHolder currentReadHolder();

    /**
     * Custom attribute
     */
    Object getCustom();
}
