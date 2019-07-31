package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;

/**
 * Returns the column width according to each column header
 *
 * @author zhuangjiaju
 */
public abstract class AbstractHeadColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {
    @Override
    protected void setColumnWidth(Sheet sheet, Cell cell, Head head, int relativeRowIndex, boolean isHead) {
        if (!isHead && relativeRowIndex != 0) {
            return;
        }
        Integer width = columnWidth(head);
        if (width != null) {
            sheet.setColumnWidth(cell.getColumnIndex(), columnWidth(head));
        }
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * <li>if return null,ignore
     *
     * @param head
     *            Nullable
     * @return the width in units of 1/256th of a character width . Using the Calibri font as an example, the maximum
     *         digit width of 11 point font size is 7 pixels (at 96 dpi). If you set a column width to be eight
     *         characters wide, e.g. you need return 8*256
     */
    protected abstract Integer columnWidth(Head head);
}
