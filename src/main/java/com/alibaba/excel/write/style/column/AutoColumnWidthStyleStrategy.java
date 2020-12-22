package com.alibaba.excel.write.style.column;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto adjust the column width of the longest row, only expand or not.
 * <p>
 * This has copied a lot of ideas from LongestMatchColumnWidthStyleStrategy.
 * <p>
 * @author Liyi
 */
public class AutoColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    private static final int MAX_COLUMN_WIDTH = 255;

    private static final int COLUMN_WIDTH_SCALE = 256;

    private Map<Integer, Map<Integer, Integer>> cache = new HashMap<Integer, Map<Integer, Integer>>(8);

    private boolean onlyExpand = false;

    private List<Integer> excludeColumnIndexs;

    /**
     * Only expand column width or not
     * <p>
     * @param onlyExpand
     */
    public AutoColumnWidthStyleStrategy onlyExpand(boolean onlyExpand){
        this.onlyExpand = onlyExpand;
        return this;
    }

    /**
     *
     * @param excludeColumnIndexs
     */
    public AutoColumnWidthStyleStrategy excludeColumns(List<Integer> excludeColumnIndexs){
        this.excludeColumnIndexs = excludeColumnIndexs;
        return this;
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head,
                                  Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (!needSetWidth){
            return;
        }
        if(!CollectionUtils.isEmpty(excludeColumnIndexs) && excludeColumnIndexs.contains(cell.getColumnIndex())){
            return;
        }
        Map<Integer, Integer> maxColumnWidthMap = cache.get(writeSheetHolder.getSheetNo());
        if (maxColumnWidthMap == null) {
            maxColumnWidthMap = new HashMap<Integer, Integer>(16);
            cache.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
        }
        Integer columnWidth = dataLength(cellDataList, cell, isHead);
        if (columnWidth < 0) {
            return;
        }
        if(onlyExpand && columnWidth * COLUMN_WIDTH_SCALE <= writeSheetHolder.getSheet().getColumnWidth(cell.getColumnIndex())){
            return;
        }
        if (columnWidth > MAX_COLUMN_WIDTH) {
            columnWidth = MAX_COLUMN_WIDTH;
        }
        Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
        if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
            maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
            writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * COLUMN_WIDTH_SCALE);
        }
    }

    private Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        }
        CellData cellData = cellDataList.get(0);
        CellDataTypeEnum type = cellData.getType();
        if (type == null) {
            return -1;
        }
        switch (type) {
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
