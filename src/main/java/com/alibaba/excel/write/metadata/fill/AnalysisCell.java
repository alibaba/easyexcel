package com.alibaba.excel.write.metadata.fill;

import java.util.List;

import com.alibaba.excel.enums.WriteTemplateAnalysisCellTypeEnum;

/**
 * Read the cells of the template while populating the data.
 *
 * @author Jiaju Zhuang
 **/
public class AnalysisCell {
    private int columnIndex;
    private int rowIndex;
    private List<String> variableList;
    private List<String> prepareDataList;
    private Boolean onlyOneVariable;
    private WriteTemplateAnalysisCellTypeEnum cellType;
    private String prefix;
    private Boolean firstRow;

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

    public List<String> getPrepareDataList() {
        return prepareDataList;
    }

    public void setPrepareDataList(List<String> prepareDataList) {
        this.prepareDataList = prepareDataList;
    }

    public Boolean getOnlyOneVariable() {
        return onlyOneVariable;
    }

    public void setOnlyOneVariable(Boolean onlyOneVariable) {
        this.onlyOneVariable = onlyOneVariable;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public WriteTemplateAnalysisCellTypeEnum getCellType() {
        return cellType;
    }

    public void setCellType(WriteTemplateAnalysisCellTypeEnum cellType) {
        this.cellType = cellType;
    }

    public Boolean getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Boolean firstRow) {
        this.firstRow = firstRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnalysisCell that = (AnalysisCell)o;
        if (columnIndex != that.columnIndex) {
            return false;
        }
        return rowIndex == that.rowIndex;
    }

    @Override
    public int hashCode() {
        int result = columnIndex;
        result = 31 * result + rowIndex;
        return result;
    }
}
