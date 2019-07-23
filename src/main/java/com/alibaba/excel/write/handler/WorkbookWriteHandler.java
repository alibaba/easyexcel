package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WorkbookHolder;

/**
 * intercepts handle Workbook creation
 * 
 * @author zhuangjiaju
 */
public interface WorkbookWriteHandler extends WriteHandler {

    /**
     * called before create the sheet
     */
    void beforeWorkbookCreate();

    /**
     * called after the sheet is created
     * 
     * @param workbookHolder
     */
    void afterWorkbookCreate(WorkbookHolder workbookHolder);
}
