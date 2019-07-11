package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * intercepts handle Workbook creation
 * 
 * @author zhuangjiaju
 */
public interface WorkbookWriteHandler extends WriteHandler {

    /**
     * called before create the sheet
     * 
     * @param writeContext
     */
    void beforeWorkbookCreate();

    /**
     * called after the sheet is created
     *
     * @param writeContext
     */
    void afterWorkbookCreate(Workbook workbook);
}
