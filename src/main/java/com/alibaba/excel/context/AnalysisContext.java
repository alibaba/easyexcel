package com.alibaba.excel.context;

import com.alibaba.excel.event.EachRowAnalysisFinishEvent;
import com.alibaba.excel.metadata.holder.ReadConfiguration;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;

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
     * @param sheet
     */
    void currentSheet(com.alibaba.excel.metadata.Sheet sheet);

    /**
     * All information about the workbook you are currently working on
     *
     * @return
     */
    WorkbookHolder currentWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     *
     * @return
     */
    SheetHolder currentSheetHolder();

    /**
     * Configuration of currently operated cell
     *
     * @return
     */
    ReadConfiguration currentConfiguration();

    /**
     * set current result
     * 
     * @param result
     */
    void setCurrentRowAnalysisResult(Object result);

    /**
     * get current result
     * 
     * @return get current result
     */
    Object currentRowAnalysisResult();

    /**
     * get current row
     * 
     * @return
     */
    Integer currentRowNum();

    /**
     * set current row num
     * 
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     * get total row , Data may be inaccurate
     * 
     * @return
     */
    @Deprecated
    Integer getTotalCount();

    /**
     * get total row ,Data may be inaccurate
     *
     * @param totalCount
     */
    void setTotalCount(Integer totalCount);
}
