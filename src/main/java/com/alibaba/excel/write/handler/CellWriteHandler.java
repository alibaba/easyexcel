package com.alibaba.excel.write.handler;

import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

/**
 * intercepts handle cell creation
 *
 * @author Jiaju Zhuang
 */
public interface CellWriteHandler extends WriteHandler {

    /**
     * Called before create the cell
     *
     * @param context
     */
     void beforeCellCreate(CellWriteHandlerContext context);


    /**
     * Called after the cell is created
     *
     * @param context
     */
     void afterCellCreate(CellWriteHandlerContext context) ;



    /**
     * Called after the cell data is converted
     *
     * @param context
     */
     void afterCellDataConverted(CellWriteHandlerContext context);

    /**
     * Called after all operations on the cell have been completed
     *
     * @param context
     */
     void afterCellDispose(CellWriteHandlerContext context);

}
