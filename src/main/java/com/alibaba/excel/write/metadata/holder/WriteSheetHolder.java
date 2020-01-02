package com.alibaba.excel.write.metadata.holder;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.enums.WriteLastRowTypeEnum;
import com.alibaba.excel.util.StringUtils;
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
     * Current poi Sheet.This is only for writing, and there may be no data in version 07 when template data needs to be
     * read.
     * <ul>
     * <li>03:{@link HSSFSheet}</li>
     * <li>07:{@link SXSSFSheet}</li>
     * </ul>
     */
    private Sheet sheet;
    /***
     * Current poi Sheet.Be sure to use and this method when reading template data.
     * <ul>
     * <li>03:{@link HSSFSheet}</li>
     * <li>07:{@link XSSFSheet}</li>
     * </ul>
     */
    private Sheet cachedSheet;
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
    private WriteLastRowTypeEnum writeLastRowTypeEnum;

    public WriteSheetHolder(WriteSheet writeSheet, WriteWorkbookHolder writeWorkbookHolder) {
        super(writeSheet, writeWorkbookHolder, writeWorkbookHolder.getWriteWorkbook().getConvertAllFiled());
        this.writeSheet = writeSheet;
        if (writeSheet.getSheetNo() == null && StringUtils.isEmpty(writeSheet.getSheetName())) {
            this.sheetNo = 0;
        } else {
            this.sheetNo = writeSheet.getSheetNo();
        }
        this.sheetName = writeSheet.getSheetName();
        this.parentWriteWorkbookHolder = writeWorkbookHolder;
        this.hasBeenInitializedTable = new HashMap<Integer, WriteTableHolder>();
        if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
            writeLastRowTypeEnum = WriteLastRowTypeEnum.TEMPLATE_EMPTY;
        } else {
            writeLastRowTypeEnum = WriteLastRowTypeEnum.COMMON_EMPTY;
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

    public Sheet getCachedSheet() {
        return cachedSheet;
    }

    public void setCachedSheet(Sheet cachedSheet) {
        this.cachedSheet = cachedSheet;
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

    public WriteLastRowTypeEnum getWriteLastRowTypeEnum() {
        return writeLastRowTypeEnum;
    }

    public void setWriteLastRowTypeEnum(WriteLastRowTypeEnum writeLastRowTypeEnum) {
        this.writeLastRowTypeEnum = writeLastRowTypeEnum;
    }

    /**
     * Get the last line of index,you have to make sure that the data is written next
     *
     * @return
     */
    public int getNewRowIndexAndStartDoWrite() {
        // 'getLastRowNum' doesn't matter if it has one or zero,is's zero
        int newRowIndex = 0;
        switch (writeLastRowTypeEnum) {
            case TEMPLATE_EMPTY:
                newRowIndex = Math.max(sheet.getLastRowNum(), cachedSheet.getLastRowNum());
                if (newRowIndex != 0 || cachedSheet.getRow(0) != null) {
                    newRowIndex++;
                }
                break;
            case HAS_DATA:
                newRowIndex = Math.max(sheet.getLastRowNum(), cachedSheet.getLastRowNum());
                newRowIndex++;
                break;
            default:
                break;
        }
        writeLastRowTypeEnum = WriteLastRowTypeEnum.HAS_DATA;
        return newRowIndex;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.SHEET;
    }
}
