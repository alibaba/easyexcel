package com.alibaba.excel.write.style.row;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * Set the row height strategy
 * 
 * @author zhuangjiaju
 */
public abstract class AbstractRowHeightStyleStrategy implements RowWriteHandler, NotRepeatExecutor {

    @Override
    public String uniqueValue() {
        return "RowHighStyleStrategy";
    }

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, int rowIndex,
        int relativeRowIndex, boolean isHead) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        int relativeRowIndex, boolean isHead) {
        if (isHead) {
            setHeadColumnHeight(row, relativeRowIndex);
        } else {
            setContentColumnHeight(row, relativeRowIndex);
        }
    }

    /**
     * Sets the height of header
     * 
     * @param row
     * @param relativeRowIndex
     */
    protected abstract void setHeadColumnHeight(Row row, int relativeRowIndex);

    /**
     * Sets the height of content
     * 
     * @param row
     * @param relativeRowIndex
     */
    protected abstract void setContentColumnHeight(Row row, int relativeRowIndex);

}
