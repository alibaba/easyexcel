package com.alibaba.excel.write.metadata.holder;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.write.metadata.WriteTable;

import lombok.Data;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
@Data
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

    public WriteTableHolder(WriteTable writeTable, WriteSheetHolder writeSheetHolder) {
        super(writeTable, writeSheetHolder);
        this.parentWriteSheetHolder = writeSheetHolder;
        this.tableNo = writeTable.getTableNo();
        this.writeTable = writeTable;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.TABLE;
    }
}
