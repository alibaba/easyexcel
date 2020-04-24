package com.alibaba.excel.read.metadata.holder;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
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
    /**
     * Data storage of the current row.
     */
    private Map<Integer, Cell> cellMap;
    /**
     * Data storage of the current extra cell.
     */
    private CellExtra cellExtra;
    /**
     * Index of the current row.
     */
    private Integer rowIndex;
    /**
     * Current CellData
     */
    private CellData tempCellData;

    public ReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder, readWorkbookHolder.getReadWorkbook().getConvertAllFiled());
        this.readSheet = readSheet;
        this.parentReadWorkbookHolder = readWorkbookHolder;
        this.sheetNo = readSheet.getSheetNo();
        this.sheetName = readSheet.getSheetName();
        this.cellMap = new LinkedHashMap<Integer, Cell>();
        this.rowIndex = -1;
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

    public Map<Integer, Cell> getCellMap() {
        return cellMap;
    }

    public void setCellMap(Map<Integer, Cell> cellMap) {
        this.cellMap = cellMap;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public CellData getTempCellData() {
        return tempCellData;
    }

    public void setTempCellData(CellData tempCellData) {
        this.tempCellData = tempCellData;
    }

    public CellExtra getCellExtra() {
        return cellExtra;
    }

    public void setCellExtra(CellExtra cellExtra) {
        this.cellExtra = cellExtra;
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
