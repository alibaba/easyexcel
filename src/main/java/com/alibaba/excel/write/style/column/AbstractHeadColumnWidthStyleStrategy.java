package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

/**
 * Returns the column width according to each column header
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractHeadColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, CellData cellData, Cell cell, Head head,
        int relativeRowIndex, boolean isHead) {
        if (!isHead && relativeRowIndex != 0) {
            return;
        }
        Integer width = columnWidth(head);
        if (width != null) {
            width = width * 256;
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), width);
        }
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * <li>if return null,ignore
     *
     * @param head
     *            Nullable
     * @return
     */
    protected abstract Integer columnWidth(Head head);
}
