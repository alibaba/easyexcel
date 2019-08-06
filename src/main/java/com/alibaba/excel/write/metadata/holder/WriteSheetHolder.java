package com.alibaba.excel.write.metadata.holder;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.enums.WriteLastRowType;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
public class WriteSheetHolder extends AbstractWriteHolder {
    /**
     * current param
     */
    private WriteSheet writeSheet;
    /***
     * poi sheet
     */
    private Sheet sheet;
    /***
     * sheetNo
     */
    private Integer sheetNo;
    /***
     * sheetName
     */
    private String sheetName;
    /***
     * poi sheet
     */
    private WriteWorkbookHolder parentWriteWorkbookHolder;
    /***
     * has been initialized table
     */
    private Map<Integer, WriteTableHolder> hasBeenInitializedTable;

    /**
     * last column type
     *
     * @param writeSheet
     * @param writeWorkbookHolder
     */
    private WriteLastRowType writeLastRowType;

    public WriteSheetHolder(WriteSheet writeSheet, WriteWorkbookHolder writeWorkbookHolder) {
        super(writeSheet, writeWorkbookHolder, writeWorkbookHolder.getWriteWorkbook().getConvertAllFiled());
        this.writeSheet = writeSheet;
        this.sheetNo = writeSheet.getSheetNo();
        if (writeSheet.getSheetName() == null) {
            this.sheetName = writeSheet.getSheetNo().toString();
        } else {
            this.sheetName = writeSheet.getSheetName();
        }
        this.parentWriteWorkbookHolder = writeWorkbookHolder;
        this.hasBeenInitializedTable = new HashMap<Integer, WriteTableHolder>();
        if (writeWorkbookHolder.getTemplateInputStream() == null && writeWorkbookHolder.getTemplateFile() == null) {
            writeLastRowType = WriteLastRowType.COMMON_EMPTY;
        } else {
            writeLastRowType = WriteLastRowType.TEMPLATE_EMPTY;
        }
    }

    public WriteSheet getWriteSheet() {
        return writeSheet;
    }

    public void setWriteSheet(WriteSheet writeSheet) {
        this.writeSheet = writeSheet;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Integer getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public WriteWorkbookHolder getParentWriteWorkbookHolder() {
        return parentWriteWorkbookHolder;
    }

    public void setParentWriteWorkbookHolder(WriteWorkbookHolder parentWriteWorkbookHolder) {
        this.parentWriteWorkbookHolder = parentWriteWorkbookHolder;
    }

    public Map<Integer, WriteTableHolder> getHasBeenInitializedTable() {
        return hasBeenInitializedTable;
    }

    public void setHasBeenInitializedTable(Map<Integer, WriteTableHolder> hasBeenInitializedTable) {
        this.hasBeenInitializedTable = hasBeenInitializedTable;
    }

    public WriteLastRowType getWriteLastRowType() {
        return writeLastRowType;
    }

    public void setWriteLastRowType(WriteLastRowType writeLastRowType) {
        this.writeLastRowType = writeLastRowType;
    }

    /**
     * Get the last line of index,you have to make sure that the data is written next
     *
     * @return
     */
    public int getNewRowIndexAndStartDoWrite() {
        // 'getLastRowNum' doesn't matter if it has one or zero,is's zero
        int newRowIndex = 0;
        switch (writeLastRowType) {
            case TEMPLATE_EMPTY:
                if (parentWriteWorkbookHolder.getExcelType() == ExcelTypeEnum.XLSX) {
                    if (parentWriteWorkbookHolder.getTemplateLastRowMap().containsKey(sheetNo)) {
                        newRowIndex = parentWriteWorkbookHolder.getTemplateLastRowMap().get(sheetNo);
                    }
                } else {
                    newRowIndex = sheet.getLastRowNum();
                }
                newRowIndex++;
                break;
            case HAS_DATA:
                newRowIndex = sheet.getLastRowNum();
                newRowIndex++;
                break;
            default:
                break;
        }
        writeLastRowType = WriteLastRowType.HAS_DATA;
        return newRowIndex;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.SHEET;
    }
}
