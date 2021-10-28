package com.alibaba.easyexcel.test.temp.simple;

import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jiaju Zhuang
 */
@Slf4j
public class WriteCellHandler implements CellWriteHandler {
    //
    //@Override
    //public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
    //    WriteCellData<?> cellData, Cell cell, Head head, Integer integer, Boolean isHead) {
    //
    //    if (!isHead) {
    //        CreationHelper createHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
    //        CellStyle cellStyle = writeSheetHolder.getSheet().getWorkbook().createCellStyle();
    //        if (cellStyle != null) {
    //            DataFormat dataFormat = createHelper.createDataFormat();
    //            cellStyle.setWrapText(true);
    //            cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
    //            cellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
    //            cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
    //            cell.setCellStyle(cellStyle);
    //        }
    //    }
    //}

    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {

    }

    @Override
    public void afterCellCreate(CellWriteHandlerContext context) {

    }

    @Override
    public void afterCellDataConverted(CellWriteHandlerContext context) {

    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {

    }
}
