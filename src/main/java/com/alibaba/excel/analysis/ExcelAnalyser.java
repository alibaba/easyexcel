package com.alibaba.excel.analysis;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {
    /**
     * parse one sheet
     *
     * @param readSheet
     *            sheet to read
     */
    void analysis(ReadSheet readSheet);

    /**
     * Complete the entire read file.Release the cache and close stream
     */
    void finish();

    /**
     * Acquisition excel executor
     *
     * @return Excel file Executor
     */
    ExcelReadExecutor excelExecutor();

    /**
     * get the analysis context.
     *
     * @return analysis context
     */
    AnalysisContext analysisContext();

}
