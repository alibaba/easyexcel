package com.alibaba.excel.write.metadata.holder;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.write.metadata.WriteTable;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
public class WriteTableHolder extends AbstractWriteHolder {
    /***
     * poi sheet
     */
    private WriteSheetHolder parentWriteSheetHolder;
    /***
     * tableNo
     */
    private Integer tableNo;
    /**
     * current table param
     */
    private WriteTable writeTable;

    public WriteTableHolder(WriteTable writeTable, WriteSheetHolder writeSheetHolder,
        WriteWorkbookHolder writeWorkbookHolder) {
        super(writeTable, writeSheetHolder, writeWorkbookHolder.getWriteWorkbook().getConvertAllFiled());
        this.parentWriteSheetHolder = writeSheetHolder;
        this.tableNo = writeTable.getTableNo();
        this.writeTable = writeTable;
    }

    public WriteSheetHolder getParentWriteSheetHolder() {
        return parentWriteSheetHolder;
    }

    public void setParentWriteSheetHolder(WriteSheetHolder parentWriteSheetHolder) {
        this.parentWriteSheetHolder = parentWriteSheetHolder;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public WriteTable getWriteTable() {
        return writeTable;
    }

    public void setWriteTable(WriteTable writeTable) {
        this.writeTable = writeTable;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.TABLE;
    }
}
