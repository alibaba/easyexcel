package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;

/**
 * Returns the column width according to each column header
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractHeadColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    @Override
    protected void setColumnWidth(Sheet sheet, Cell cell, Head head, int relativeRowIndex, boolean isHead) {
        if (!isHead && relativeRowIndex != 0) {
            return;
        }
        Integer width = columnWidth(head);
        if (width != null) {
            width = width * 256;
            sheet.setColumnWidth(cell.getColumnIndex(), width);
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
