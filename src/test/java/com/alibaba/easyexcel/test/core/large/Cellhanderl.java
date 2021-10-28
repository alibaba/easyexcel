package com.alibaba.easyexcel.test.core.large;

import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

public class Cellhanderl implements CellWriteHandler {

    public static int cout;

    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {
        cout=context.getColumnIndex();
    }

    @Override
    public void afterCellCreate(CellWriteHandlerContext context) {
        cout=context.getColumnIndex();
    }

    @Override
    public void afterCellDataConverted(CellWriteHandlerContext context) {
        cout=context.getColumnIndex();
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        cout=context.getColumnIndex();
    }

}
