package com.alibaba.excel.write.metadata.fill;

import java.util.List;

/**
 * Read the cells of the template while populating the data.
 *
 * @author Jiaju Zhuang
 **/
public class AnalysisCell {
    private int columnIndex;
    private int rowIndex;
    private List<String> variableList;
    private String prepareData;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<String> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<String> variableList) {
        this.variableList = variableList;
    }

    public String getPrepareData() {
        return prepareData;
    }

    public void setPrepareData(String prepareData) {
        this.prepareData = prepareData;
    }
}
