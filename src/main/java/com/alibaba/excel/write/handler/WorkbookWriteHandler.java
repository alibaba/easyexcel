package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * intercepts handle Workbook creation
 *
 * @author Jiaju Zhuang
 */
public interface WorkbookWriteHandler extends WriteHandler {

    /**
     * Called before create the workbook
     */
    default void beforeWorkbookCreate() {}

    /**
     * Called after the workbook is created
     *
     * @param writeWorkbookHolder
     */
    default void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {}

    /**
     * Called after all operations on the workbook have been completed
     *
     * @param writeWorkbookHolder
     */
    default void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {}
}
