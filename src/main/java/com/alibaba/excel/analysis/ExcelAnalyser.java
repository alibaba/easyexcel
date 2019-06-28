package com.alibaba.excel.analysis;

import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.Sheet;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {
    
    void beforeAnalysis();
    
    /**
     * parse one sheet
     *
     * @param sheetParam
     */
    void analysis(Sheet sheetParam);

    /**
     * parse all sheets
     */
    void analysis();

    /**
     * get all sheet of workbook
     *
     * @return all sheets
     */
    List<Sheet> getSheets();
    
    /**
     * get the analysis context.
     * @return analysis context
     */
    AnalysisContext getAnalysisContext();

}
