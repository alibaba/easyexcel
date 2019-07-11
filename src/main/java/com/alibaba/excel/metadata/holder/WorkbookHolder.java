package com.alibaba.excel.metadata.holder;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Workbook holder
 *
 * @author zhuangjiaju
 */
public class WorkbookHolder implements ConfigurationSelector {
    /***
     * poi Workbook
     */
    private Workbook workbook;
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
     * prevent duplicate creation of sheet objects
     */
    private Map<Integer, SheetHolder> hasBeenInitializedSheet;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;
    /**
     * current param
     */
    private com.alibaba.excel.metadata.Workbook workbookParam;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * Template input stream
     */
    private InputStream templateInputStream;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
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

    public Map<Integer, SheetHolder> getHasBeenInitializedSheet() {
        return hasBeenInitializedSheet;
    }

    public void setHasBeenInitializedSheet(Map<Integer, SheetHolder> hasBeenInitializedSheet) {
        this.hasBeenInitializedSheet = hasBeenInitializedSheet;
    }

    public Integer getWriteRelativeHeadRowIndex() {
        return writeRelativeHeadRowIndex;
    }

    public void setWriteRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
    }

    public com.alibaba.excel.metadata.Workbook getWorkbookParam() {
        return workbookParam;
    }

    public void setWorkbookParam(com.alibaba.excel.metadata.Workbook workbookParam) {
        this.workbookParam = workbookParam;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getTemplateInputStream() {
        return templateInputStream;
    }

    public void setTemplateInputStream(InputStream templateInputStream) {
        this.templateInputStream = templateInputStream;
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
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
