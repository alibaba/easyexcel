package com.alibaba.excel.analysis;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Sheet;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {
    /**
     * parse one sheet
     *
     * @param sheetParam
     */
    void analysis(Sheet sheetParam);

    /**
     * Complete the entire read file.Release the cache and close stream
     */
    void finish();

    /**
     * Acquisition excel executor
     * 
     * @return
     */
    ExcelExecutor excelExecutor();

    /**
     * get the analysis context.
     * 
     * @return analysis context
     */
    AnalysisContext analysisContext();

}
