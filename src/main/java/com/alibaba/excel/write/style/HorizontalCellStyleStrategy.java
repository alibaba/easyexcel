package com.alibaba.excel.write.style;

import java.util.List;

import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import org.apache.commons.collections4.CollectionUtils;

/**
 * Use the same style for the row
 *
 * @author Jiaju Zhuang
 */
public class HorizontalCellStyleStrategy extends AbstractCellStyleStrategy {

    private WriteCellStyle headWriteCellStyle;
    private List<WriteCellStyle> contentWriteCellStyleList;

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
        List<WriteCellStyle> contentWriteCellStyleList) {
        this.headWriteCellStyle = headWriteCellStyle;
        this.contentWriteCellStyleList = contentWriteCellStyleList;
    }

    public HorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        this.headWriteCellStyle = headWriteCellStyle;
        if (contentWriteCellStyle != null) {
            this.contentWriteCellStyleList = ListUtils.newArrayList(contentWriteCellStyle);
        }
    }

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (!continueProcessing(context) || headWriteCellStyle == null) {
            return;
        }
        WriteCellData<?> cellData = context.getCellDataList().get(0);
        cellData.setWriteCellStyle(headWriteCellStyle);
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (!continueProcessing(context) || CollectionUtils.isEmpty(contentWriteCellStyleList)) {
            return;
        }
        WriteCellData<?> cellData = context.getCellDataList().get(0);
        if (context.getRelativeRowIndex() == null || context.getRelativeRowIndex() <= 0) {
            cellData.setWriteCellStyle(contentWriteCellStyleList.get(0));
        } else {
            cellData.setWriteCellStyle(
                contentWriteCellStyleList.get(context.getRelativeRowIndex() % contentWriteCellStyleList.size()));
        }
    }

    protected boolean continueProcessing(CellWriteHandlerContext context) {
        List<WriteCellData<?>> cellDataList = context.getCellDataList();
        if (CollectionUtils.isEmpty(cellDataList) || cellDataList.size() > 1) {
            return false;
        }
        return true;
    }

}
