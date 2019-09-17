package com.alibaba.excel.write.style.column;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;

/**
 * Take the width of the longest column as the width.
 * <p>
 * This is not very useful at the moment, for example if you have Numbers it will cause a newline.And the length is not
 * exactly the same as the actual length.
 *
 * @author Jiaju Zhuang
 */
public class LongestMatchColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    private static final int MAX_COLUMN_WIDTH = 256;

    Map<Integer, Map<Integer, Integer>> cache = new HashMap<Integer, Map<Integer, Integer>>(8);

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, CellData cellData, Cell cell, Head head,
        int relativeRowIndex, boolean isHead) {
        if (!isHead && cellData == null) {
            return;
        }
        Map<Integer, Integer> maxColumnWidthMap = cache.get(writeSheetHolder.getSheetNo());
        if (maxColumnWidthMap == null) {
            maxColumnWidthMap = new HashMap<Integer, Integer>(16);
            cache.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
        }
        Integer columnWidth = dataLength(cellData, cell, isHead);
        if (columnWidth < 0) {
            return;
        }
        if (columnWidth > MAX_COLUMN_WIDTH) {
            columnWidth = MAX_COLUMN_WIDTH;
        }
        Integer maxColumnWidth = maxColumnWidthMap.get(head.getColumnIndex());
        if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
            maxColumnWidthMap.put(head.getColumnIndex(), columnWidth);
            writeSheetHolder.getSheet().setColumnWidth(head.getColumnIndex(), columnWidth * 256);
        }
    }

    private Integer dataLength(CellData cellData, Cell cell, boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        }
        switch (cellData.getType()) {
            case STRING:
                return cellData.getStringValue().getBytes().length;
            case BOOLEAN:
                return cellData.getBooleanValue().toString().getBytes().length;
            case NUMBER:
                return cellData.getNumberValue().toString().getBytes().length;
            default:
                return -1;
        }
    }
}
