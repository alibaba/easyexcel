package com.alibaba.excel.write.merge;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Merge strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractMergeStrategy implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (context.getHead()) {
            return;
        }
        merge(context.getWriteSheetHolder().getSheet(), context.getCell(), context.getHeadData(),
            context.getRelativeRowIndex());
    }

    /**
     * merge
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected abstract void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex);
}
