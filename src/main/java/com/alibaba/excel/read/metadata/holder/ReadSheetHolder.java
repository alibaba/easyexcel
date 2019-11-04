package com.alibaba.excel.read.metadata.holder;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
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
     * Gets the total number of rows , data may be inaccurate
     */
    private Integer approximateTotalRowNumber;

    public ReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder, readWorkbookHolder.getReadWorkbook().getConvertAllFiled());
        this.readSheet = readSheet;
        this.parentReadWorkbookHolder = readWorkbookHolder;
        this.sheetNo = readSheet.getSheetNo();
        this.sheetName = readSheet.getSheetName();
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

    /**
     *
     * Approximate total number of rows
     *
     * @return
     * @see #getApproximateTotalRowNumber()
     */
    @Deprecated
    public Integer getTotal() {
        return approximateTotalRowNumber;
    }

    /**
     * Approximate total number of rows
     *
     * @return
     */
    public Integer getApproximateTotalRowNumber() {
        return approximateTotalRowNumber;
    }

    public void setApproximateTotalRowNumber(Integer approximateTotalRowNumber) {
        this.approximateTotalRowNumber = approximateTotalRowNumber;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.SHEET;
    }

    @Override
    public String toString() {
        return "ReadSheetHolder{" + "sheetNo=" + sheetNo + ", sheetName='" + sheetName + '\'' + "} " + super.toString();
    }
}
