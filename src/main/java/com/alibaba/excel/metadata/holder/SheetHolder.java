package com.alibaba.excel.metadata.holder;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class SheetHolder implements ConfigurationSelector {
    /***
     * poi sheet
     */
    private Sheet sheet;

    /***
     * has been initialized table
     */
    private Map<Integer, TableHolder> hasBeenInitializedTable;
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
     * current param
     */
    private com.alibaba.excel.metadata.Sheet sheetParam;

    /**
     * Excel head property
     */
    private ExcelHeadProperty excelHeadProperty;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Map<Integer, TableHolder> getHasBeenInitializedTable() {
        return hasBeenInitializedTable;
    }

    public void setHasBeenInitializedTable(Map<Integer, TableHolder> hasBeenInitializedTable) {
        this.hasBeenInitializedTable = hasBeenInitializedTable;
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

    public com.alibaba.excel.metadata.Sheet getSheetParam() {
        return sheetParam;
    }

    public void setSheetParam(com.alibaba.excel.metadata.Sheet sheetParam) {
        this.sheetParam = sheetParam;
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
}
