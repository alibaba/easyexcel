package com.alibaba.excel.write.style;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

/**
 * Use the same style for the column
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractVerticalCellStyleStrategy extends AbstractCellStyleStrategy {

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (stopProcessing(context)) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle.merge(headCellStyle(context), cellData.getOrCreateStyle());
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (context.getFirstCellData() == null) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle.merge(contentCellStyle(context), cellData.getOrCreateStyle());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param context
     * @return
     */
    protected WriteCellStyle headCellStyle(CellWriteHandlerContext context) {
        return headCellStyle(context.getHeadData());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected WriteCellStyle headCellStyle(Head head) {
        return null;
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * @param context
     * @return
     */
    protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
        return contentCellStyle(context.getHeadData());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected WriteCellStyle contentCellStyle(Head head) {
        return null;
    }

    protected boolean stopProcessing(CellWriteHandlerContext context) {
        if (context.getFirstCellData() == null) {
            return true;
        }
        return context.getHeadData() == null;
    }
}
