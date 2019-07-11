package com.alibaba.excel.metadata;

import java.util.ArrayList;
import java.util.List;

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

    public Head(Integer columnIndex, String fieldName, String headName) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        headNameList = new ArrayList<String>();
        headNameList.add(headName);
    }

    public Head(Integer columnIndex, String fieldName, List<String> headNameList) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        if (headNameList == null) {
            headNameList = new ArrayList<String>();
        }
        this.headNameList = headNameList;
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

    @Override
    public String toString() {
        return "Head{" + "columnIndex=" + columnIndex + ", fieldName='" + fieldName + '\'' + ", headNameList="
            + headNameList + '}';
    }

    /**
     * Get head name with index
     * 
     * @param index
     * @return
     */
    public String getHeadName(int index) {
        if (headNameList == null || headNameList.isEmpty()) {
            return null;
        }
        if (index >= headNameList.size()) {
            return headNameList.get(headNameList.size() - 1);
        } else {
            return headNameList.get(index);
        }
    }
}
