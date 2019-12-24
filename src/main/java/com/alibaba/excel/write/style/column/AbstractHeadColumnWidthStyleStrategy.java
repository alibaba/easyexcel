package com.alibaba.excel.write.style.column;

import java.util.List;

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
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head,
        Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = relativeRowIndex != null && (isHead || relativeRowIndex == 0);
        if (!needSetWidth) {
            return;
        }
        Integer width = columnWidth(head, cell.getColumnIndex());
        if (width != null) {
            width = width * 256;
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), width);
        }
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * <p>
     * if return null,ignore
     *
     * @param head
     *            Nullable.
     * @param columnIndex
     *            Not null.
     * @return
     */
    protected abstract Integer columnWidth(Head head, Integer columnIndex);

}
