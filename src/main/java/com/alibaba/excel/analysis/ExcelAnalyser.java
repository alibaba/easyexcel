package com.alibaba.excel.analysis;

import com.alibaba.excel.metadata.Sheet;

import java.util.List;

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
     * parse all sheets
     */
    void analysis();

    /**
     * get all sheet of workbook
     *
     * @return all sheets
     */
    List<Sheet> getSheets();

}
