package com.alibaba.excel.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * excel head
 *
 * @author zhuangjiaju
 **/
public class Head {
    private Integer columnIndex;
    private String fieldName;
    private List<String> headNames;

    public Head(Integer columnIndex, String fieldName, String headName) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        List<String> headNamesTmp = new ArrayList<String>();
        headNamesTmp.add(headName);
        this.headNames = headNamesTmp;
    }

    public Head(Integer columnIndex, String fieldName, List<String> headNames) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        if (headNames == null) {
            headNames = new ArrayList<String>();
        }
        this.headNames = headNames;
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

    public List<String> getHeadNames() {
        return headNames;
    }

    public void setHeadNames(List<String> headNames) {
        this.headNames = headNames;
    }

    /**
     * Get head name with index
     * 
     * @param index
     * @return
     */
    public String getHeadName(int index) {
        if (headNames == null || headNames.isEmpty()) {
            return null;
        }
        if (index >= headNames.size()) {
            return headNames.get(headNames.size() - 1);
        } else {
            return headNames.get(index);
        }
    }
}
