package com.alibaba.excel.metadata.holder;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class TableHolder implements ConfigurationSelector {
    /***
     * poi sheet
     */
    private SheetHolder parentSheet;
    /***
     * tableNo
     */
    private Integer tableNo;
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Write handler for workbook
     */
    private List<WriteHandler> writeHandlerList;
    /**
     * Converter for workbook
     */
    private Map<Class, Converter> converterMap;
    /**
     * Excel head property
     */
    private ExcelHeadProperty excelHeadProperty;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;

    /**
     * current table param
     */
    private com.alibaba.excel.metadata.Table tableParam;
    /**
     * Record whether it's new or from cache
     */
    private Boolean newInitialization;

    public SheetHolder getParentSheet() {
        return parentSheet;
    }

    public void setParentSheet(SheetHolder parentSheet) {
        this.parentSheet = parentSheet;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }

    public List<WriteHandler> getWriteHandlerList() {
        return writeHandlerList;
    }

    public void setWriteHandlerList(List<WriteHandler> writeHandlerList) {
        this.writeHandlerList = writeHandlerList;
    }

    public Map<Class, Converter> getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map<Class, Converter> converterMap) {
        this.converterMap = converterMap;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return excelHeadProperty;
    }

    public void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty) {
        this.excelHeadProperty = excelHeadProperty;
    }

    public Integer getWriteRelativeHeadRowIndex() {
        return writeRelativeHeadRowIndex;
    }

    public void setWriteRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
    }

    public Table getTableParam() {
        return tableParam;
    }

    public void setTableParam(Table tableParam) {
        this.tableParam = tableParam;
    }

    public Boolean getNewInitialization() {
        return newInitialization;
    }

    public void setNewInitialization(Boolean newInitialization) {
        this.newInitialization = newInitialization;
    }

    @Override
    public List<WriteHandler> writeHandlerList() {
        return getWriteHandlerList();
    }

    @Override
    public Map<Class, Converter> converterMap() {
        return getConverterMap();
    }

    @Override
    public boolean needHead() {
        return getNeedHead();
    }

    @Override
    public int writeRelativeHeadRowIndex() {
        return getWriteRelativeHeadRowIndex();
    }

    @Override
    public ExcelHeadProperty excelHeadProperty() {
        return getExcelHeadProperty();
    }

    @Override
    public boolean isNew() {
        return getNewInitialization();
    }
}
