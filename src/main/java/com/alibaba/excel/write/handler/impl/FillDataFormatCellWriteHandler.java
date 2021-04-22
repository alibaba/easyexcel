package com.alibaba.excel.write.handler.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * fill cell style.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class FillDataFormatCellWriteHandler implements CellWriteHandler, Order {

    private final Set<CellStyle> cellStyleSet = new HashSet<>();

    private CellStyle defaultDateCellStyle;

    @Override
    public int order() {
        return OrderConstant.FILL_DATA_FORMAT;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (CollectionUtils.isEmpty(cellDataList) || cellDataList.size() > 1) {
            return;
        }
        CellData<?> cellData = cellDataList.get(0);
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null || StyleUtil.isDefaultStyle(cellStyle)) {
            if (cellData.getType() == CellDataTypeEnum.DATE) {
                cell.setCellStyle(getDefaultDateCellStyle(writeSheetHolder));
            }
            return;
        }
        if (cellStyleSet.contains(cellStyle)) {
            return;
        }
        if (cellData.getDataFormat() != null && cellData.getDataFormat() >= 0) {
            cellStyle.setDataFormat(cellData.getDataFormat());
        }
        cellStyleSet.add(cellStyle);
    }

    private CellStyle getDefaultDateCellStyle(WriteSheetHolder writeSheetHolder) {
        if (defaultDateCellStyle != null) {
            return defaultDateCellStyle;
        }
        Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        defaultDateCellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        defaultDateCellStyle.setDataFormat(dataFormat.getFormat(DateUtils.defaultDateFormat));
        return defaultDateCellStyle;
    }

}
