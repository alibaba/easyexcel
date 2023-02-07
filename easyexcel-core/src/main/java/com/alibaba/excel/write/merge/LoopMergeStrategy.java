package com.alibaba.excel.write.merge;

import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.util.CellUtils;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * The regions of the loop merge
 *
 * @author Jiaju Zhuang
 */
public class LoopMergeStrategy implements RowWriteHandler, WorkbookWriteHandler {
    /**
     * Each row
     */
    private final int eachRow;
    /**
     * Extend column
     */
    private final int columnExtend;
    /**
     * The number of the current column
     */
    private final int columnIndex;

    //  custom merge field

    /**
     * custom merge strategy
     **/
    private ValueSetting valueSetting;
    /**
     * current merged value cache.
     **/
    private Object[][] currentMergedValue;

    /**
     * support the last merged when excel row can't divisible by eachRow
     **/
    private int lastRowIndexStart;

    public LoopMergeStrategy(int eachRow, int columnIndex) {
        this(eachRow, 1, columnIndex);
    }

    public LoopMergeStrategy(int eachRow, int columnExtend, int columnIndex) {
        if (eachRow < 1) {
            throw new IllegalArgumentException("EachRows must be greater than 1");
        }
        if (columnExtend < 1) {
            throw new IllegalArgumentException("ColumnExtend must be greater than 1");
        }
        if (columnExtend == 1 && eachRow == 1) {
            throw new IllegalArgumentException("ColumnExtend or eachRows must be greater than 1");
        }
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.eachRow = eachRow;
        this.columnExtend = columnExtend;
        this.columnIndex = columnIndex;
    }

    public LoopMergeStrategy(LoopMergeProperty loopMergeProperty, Integer columnIndex) {
        this(loopMergeProperty.getEachRow(), loopMergeProperty.getColumnExtend(), columnIndex);
    }


    /**
     * setting you custom value when the cell merge.
     * but if the data row can't divisible by eachRow.
     * the last merge cell can't call the setting
     *
     * @param valueSetting setting value strategy
     **/
    public void setValueSetting(ValueSetting valueSetting) {
        this.valueSetting = valueSetting;
    }


    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (context.getHead() || context.getRelativeRowIndex() == null) {
            return;
        }
        if (this.valueSetting == null) {
            doSimpleMerge(context);
        } else {
            doCustomMerge(context);
        }
    }

    @Override
    public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        if (this.currentMergedValue != null) {
            WriteSheetHolder writeSheetHolder = context.getWriteContext().writeSheetHolder();
            Sheet sheet = writeSheetHolder.getSheet();
            CellRangeAddress cellRangeAddress = new CellRangeAddress(lastRowIndexStart,
                lastRowIndexStart + eachRow - 1,
                columnIndex, columnIndex + columnExtend - 1);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            this.currentMergedValue = null;
        }
    }


    private void doCustomMerge(RowWriteHandlerContext context) {
        int indexMod = context.getRelativeRowIndex() % eachRow;
        if (indexMod == 0) {
            this.currentMergedValue = new String[eachRow][columnExtend];
            this.lastRowIndexStart = context.getRowIndex();
        }
        Row row = context.getRow();
        for (int i = 0; i < columnExtend; i++) {
            Cell cell = row.getCell(i + columnIndex);
            Object cellValue = CellUtils.getCellValue(cell);
            currentMergedValue[indexMod][i] = cellValue;
        }
        if (indexMod == eachRow - 1) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(lastRowIndexStart,
                lastRowIndexStart + eachRow - 1,
                columnIndex, columnIndex + columnExtend - 1);
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            int mergedRowIndex = context.getRowIndex() - eachRow + 1;
            Cell mergedCell = sheet.getRow(mergedRowIndex).getCell(columnIndex);
            this.valueSetting.settingMergedCell(mergedCell, this.currentMergedValue);
            this.currentMergedValue = null;
        }
    }

    private void doSimpleMerge(RowWriteHandlerContext context) {
        if (context.getRelativeRowIndex() % eachRow == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(context.getRowIndex(),
                context.getRowIndex() + eachRow - 1,
                columnIndex, columnIndex + columnExtend - 1);
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }
    }

    @FunctionalInterface
    public interface ValueSetting {

        /**
         * setting merged cell value
         *
         * @param merged            merged cell
         * @param beforeMergedValue the value of the cell to be merged
         **/
        void settingMergedCell(Cell merged, Object[][] beforeMergedValue);
    }
}
