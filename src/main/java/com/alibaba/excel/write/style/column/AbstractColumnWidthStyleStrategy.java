package com.alibaba.excel.write.style.column;

import java.util.List;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Column width style strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractColumnWidthStyleStrategy implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        setColumnWidth(context);
    }

    /**
     * Sets the column width when head create
     *
     * @param context
     */
    protected void setColumnWidth(CellWriteHandlerContext context) {
        setColumnWidth(context.getWriteSheetHolder(), context.getCellDataList(), context.getCell(),
            context.getHeadData(), context.getRelativeRowIndex(), context.getHead());
    }

    /**
     * Sets the column width when head create
     *
     * @param writeSheetHolder
     * @param cellDataList
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell,
        Head head, Integer relativeRowIndex, Boolean isHead) {
        throw new UnsupportedOperationException("Custom styles must override the setColumnWidth method.");
    }

}
