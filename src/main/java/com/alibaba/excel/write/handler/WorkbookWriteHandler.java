package com.alibaba.excel.write.handler;

import com.alibaba.excel.metadata.holder.WorkbookHolder;

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
    void afterWorkbookCreate(WorkbookHolder workbookHolder);
}
