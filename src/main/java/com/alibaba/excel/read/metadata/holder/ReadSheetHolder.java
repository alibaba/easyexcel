package com.alibaba.excel.read.metadata.holder;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class ReadSheetHolder extends AbstractReadHolder {
    /**
     * current param
     */
    private ReadSheet readSheet;
    /***
     * parent
     */
    private ReadWorkbookHolder parentReadWorkbookHolder;
    /***
     * sheetNo
     */
    private Integer sheetNo;
    /***
     * sheetName
     */
    private String sheetName;
    /**
     * get total row , Data may be inaccurate
     */
    @Deprecated
    private Integer total;

    public ReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, null, readWorkbookHolder.getReadWorkbook().getConvertAllFiled());
        this.readSheet = readSheet;
        this.parentReadWorkbookHolder = readWorkbookHolder;
        this.sheetNo = readSheet.getSheetNo();
    }

    public ReadSheet getReadSheet() {
        return readSheet;
    }

    public void setReadSheet(ReadSheet readSheet) {
        this.readSheet = readSheet;
    }

    public ReadWorkbookHolder getParentReadWorkbookHolder() {
        return parentReadWorkbookHolder;
    }

    public void setParentReadWorkbookHolder(ReadWorkbookHolder parentReadWorkbookHolder) {
        this.parentReadWorkbookHolder = parentReadWorkbookHolder;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.SHEET;
    }
}
