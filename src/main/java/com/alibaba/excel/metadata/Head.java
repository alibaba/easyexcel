package com.alibaba.excel.metadata;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.metadata.property.CellStyleProperty;
import com.alibaba.excel.metadata.property.ColumnWidthProperty;

/**
 * excel head
 *
 * @author zhuangjiaju
 **/
public class Head {
    /**
     * Column index of head
     */
    private Integer columnIndex;
    /**
     * It only has values when passed in {@link Sheet#setClazz(Class)} and {@link Table#setClazz(Class)}
     */
    private String fieldName;
    /**
     * Head name
     */
    private List<String> headNameList;
    /**
     * Whether index is specified
     */
    private Boolean forceIndex;
    /**
     * Cell style property
     */
    private CellStyleProperty cellStyleProperty;
    /**
     * column with
     */
    private ColumnWidthProperty columnWidthProperty;

    public Head(Integer columnIndex, String fieldName, String headName) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        headNameList = new ArrayList<String>();
        headNameList.add(headName);
        this.forceIndex = Boolean.FALSE;
    }

    public Head(Integer columnIndex, String fieldName, List<String> headNameList, Boolean forceIndex) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        if (headNameList == null) {
            headNameList = new ArrayList<String>();
        }
        this.headNameList = headNameList;
        this.forceIndex = forceIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getHeadNameList() {
        return headNameList;
    }

    public void setHeadNameList(List<String> headNameList) {
        this.headNameList = headNameList;
    }

    public CellStyleProperty getCellStyleProperty() {
        return cellStyleProperty;
    }

    public void setCellStyleProperty(CellStyleProperty cellStyleProperty) {
        this.cellStyleProperty = cellStyleProperty;
    }

    public ColumnWidthProperty getColumnWidthProperty() {
        return columnWidthProperty;
    }

    public void setColumnWidthProperty(ColumnWidthProperty columnWidthProperty) {
        this.columnWidthProperty = columnWidthProperty;
    }

    public Boolean getForceIndex() {
        return forceIndex;
    }

    public void setForceIndex(Boolean forceIndex) {
        this.forceIndex = forceIndex;
    }
}
