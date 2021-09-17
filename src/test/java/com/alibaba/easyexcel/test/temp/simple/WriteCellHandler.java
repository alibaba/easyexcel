package com.alibaba.easyexcel.test.temp.simple;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author Jiaju Zhuang
 */
@Slf4j
public class WriteCellHandler implements CellWriteHandler {

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        WriteCellData<?> cellData, Cell cell, Head head, Integer integer, Boolean isHead) {

        if (!isHead) {
            CreationHelper createHelper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
            CellStyle cellStyle = writeSheetHolder.getSheet().getWorkbook().createCellStyle();
            if (cellStyle != null) {
                DataFormat dataFormat = createHelper.createDataFormat();
                cellStyle.setWrapText(true);
                cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
                cellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
                cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
                cell.setCellStyle(cellStyle);
            }
        }
    }
}
