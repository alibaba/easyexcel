package com.alibaba.excel.read.metadata;

/**
 * Read sheet
 * 
 * @author jipengfei
 */
public class ReadSheet extends ReadBasicParameter {
    /**
     * Starting from 0
     */
    private Integer sheetNo;
    /**
     * sheet name
     */
    private String sheetName;

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
}
