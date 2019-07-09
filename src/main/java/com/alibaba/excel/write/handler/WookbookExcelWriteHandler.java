package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * intercepts handle Wookbook creation
 * 
 * @author zhuangjiaju
 */
public interface WookbookExcelWriteHandler extends ExcelWriteHandler {

    /**
     * called before create the sheet
     * 
     * @param writeContext
     */
    void beforeWookbookCreate();

    /**
     * called after the sheet is created
     *
     * @param writeContext
     */
    void afterWookbookCreate(Workbook workbook);
}
