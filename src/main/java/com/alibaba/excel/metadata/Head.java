package com.alibaba.excel.metadata;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.metadata.property.ColumnWidthProperty;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.metadata.property.StyleProperty;

/**
 * excel head
 *
 * @author Jiaju Zhuang
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
     * Whether to specify a name
     */
    private Boolean forceName;
    /**
     * column with
     */
    private ColumnWidthProperty columnWidthProperty;
    /**
     * Loop merge
     */
    private LoopMergeProperty loopMergeProperty;
    /**
     * Head style
     */
    private StyleProperty headStyleProperty;
    /**
     * Content style
     */
    private StyleProperty contentStyleProperty;
    /**
     * Head font
     */
    private FontProperty headFontProperty;
    /**
     * Content font
     */
    private FontProperty contentFontProperty;

    public Head(Integer columnIndex, String fieldName, List<String> headNameList, Boolean forceIndex,
        Boolean forceName) {
        this.columnIndex = columnIndex;
        this.fieldName = fieldName;
        if (headNameList == null) {
            headNameList = new ArrayList<String>();
        }
        this.headNameList = headNameList;
        this.forceIndex = forceIndex;
        this.forceName = forceName;
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

    public Boolean getForceName() {
        return forceName;
    }

    public void setForceName(Boolean forceName) {
        this.forceName = forceName;
    }

    public LoopMergeProperty getLoopMergeProperty() {
        return loopMergeProperty;
    }

    public void setLoopMergeProperty(LoopMergeProperty loopMergeProperty) {
        this.loopMergeProperty = loopMergeProperty;
    }

    public StyleProperty getHeadStyleProperty() {
        return headStyleProperty;
    }

    public void setHeadStyleProperty(StyleProperty headStyleProperty) {
        this.headStyleProperty = headStyleProperty;
    }

    public StyleProperty getContentStyleProperty() {
        return contentStyleProperty;
    }

    public void setContentStyleProperty(StyleProperty contentStyleProperty) {
        this.contentStyleProperty = contentStyleProperty;
    }

    public FontProperty getHeadFontProperty() {
        return headFontProperty;
    }

    public void setHeadFontProperty(FontProperty headFontProperty) {
        this.headFontProperty = headFontProperty;
    }

    public FontProperty getContentFontProperty() {
        return contentFontProperty;
    }

    public void setContentFontProperty(FontProperty contentFontProperty) {
        this.contentFontProperty = contentFontProperty;
    }
}
