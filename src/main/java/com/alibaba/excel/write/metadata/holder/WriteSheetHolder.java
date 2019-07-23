package com.alibaba.excel.write.metadata.holder;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.read.metadata.holder.TableHolder;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 * sheet holder
 *
 * @author zhuangjiaju
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
    private WriteWorkbookHolder parentWorkBook;
    /***
     * has been initialized table
     */
    private Map<Integer, TableHolder> hasBeenInitializedTable;

    public WriteSheetHolder(WriteSheet writeSheet, WriteWorkbookHolder writeWorkbookHolder) {
        super(writeSheet, writeWorkbookHolder, writeWorkbookHolder.getWriteWorkbook().getConvertAllFiled());
        this.writeSheet = writeSheet;
        this.sheetNo = writeSheet.getSheetNo();
        if (writeSheet.getSheetName() == null) {
            this.sheetName = writeSheet.getSheetNo().toString();
        } else {
            this.sheetName = writeSheet.getSheetName();
        }
        this.parentWorkBook = writeWorkbookHolder;
        this.hasBeenInitializedTable = new HashMap<Integer, TableHolder>();
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

    public WriteWorkbookHolder getParentWorkBook() {
        return parentWorkBook;
    }

    public void setParentWorkBook(WriteWorkbookHolder parentWorkBook) {
        this.parentWorkBook = parentWorkBook;
    }

    public Map<Integer, TableHolder> getHasBeenInitializedTable() {
        return hasBeenInitializedTable;
    }

    public void setHasBeenInitializedTable(
        Map<Integer, TableHolder> hasBeenInitializedTable) {
        this.hasBeenInitializedTable = hasBeenInitializedTable;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.SHEET;
    }
}
