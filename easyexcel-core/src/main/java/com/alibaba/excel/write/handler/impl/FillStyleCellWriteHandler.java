package com.alibaba.excel.write.handler.impl;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * fill cell style.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class FillStyleCellWriteHandler implements CellWriteHandler {

    @Override
    public int order() {
        return OrderConstant.FILL_STYLE;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (BooleanUtils.isTrue(context.getIgnoreFillStyle())) {
            return;
        }

        WriteCellData<?> cellData = context.getFirstCellData();
        if (cellData == null) {
            return;
        }
        WriteCellStyle writeCellStyle = cellData.getWriteCellStyle();
        CellStyle originCellStyle = cellData.getOriginCellStyle();
        if (writeCellStyle == null && originCellStyle == null) {
            return;
        }
        WriteWorkbookHolder writeWorkbookHolder = context.getWriteWorkbookHolder();
        context.getCell().setCellStyle(writeWorkbookHolder.createCellStyle(writeCellStyle, originCellStyle));
    }

}
