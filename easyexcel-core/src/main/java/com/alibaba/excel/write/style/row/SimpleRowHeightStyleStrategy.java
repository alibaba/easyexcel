package com.alibaba.excel.write.style.row;

import org.apache.poi.ss.usermodel.Row;

/**
 * Set the head column high and content column high
 *
 * @author Jiaju Zhuang
 */
public class SimpleRowHeightStyleStrategy extends AbstractRowHeightStyleStrategy {
    private final Short headRowHeight;
    private final Short contentRowHeight;

    public SimpleRowHeightStyleStrategy(Short headRowHeight, Short contentRowHeight) {
        this.headRowHeight = headRowHeight;
        this.contentRowHeight = contentRowHeight;
    }

    @Override
    protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
        if (headRowHeight != null) {
            row.setHeightInPoints(headRowHeight);
        }
    }

    @Override
    protected void setContentColumnHeight(Row row, int relativeRowIndex) {
        if (contentRowHeight != null) {
            row.setHeightInPoints(contentRowHeight);
        }
    }

}
