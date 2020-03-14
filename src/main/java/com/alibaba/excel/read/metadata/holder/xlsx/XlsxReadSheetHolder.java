package com.alibaba.excel.read.metadata.holder.xlsx;

import java.util.Deque;
import java.util.LinkedList;

import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
public class XlsxReadSheetHolder extends ReadSheetHolder {
    /**
     * Record the label of the current operation to prevent NPE.
     */
    private Deque<String> tagDeque;
    /**
     * Current Column
     */
    private Integer columnIndex;
    /**
     * Data for current label.
     */
    private StringBuilder tempData;
    /**
     * Formula for current label.
     */
    private StringBuilder tempFormula;

    public XlsxReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder);
        this.tagDeque = new LinkedList<String>();
    }

    public Deque<String> getTagDeque() {
        return tagDeque;
    }

    public void setTagDeque(Deque<String> tagDeque) {
        this.tagDeque = tagDeque;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public StringBuilder getTempData() {
        return tempData;
    }

    public void setTempData(StringBuilder tempData) {
        this.tempData = tempData;
    }

    public StringBuilder getTempFormula() {
        return tempFormula;
    }

    public void setTempFormula(StringBuilder tempFormula) {
        this.tempFormula = tempFormula;
    }
}
