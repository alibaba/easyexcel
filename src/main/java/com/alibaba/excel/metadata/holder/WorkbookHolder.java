package com.alibaba.excel.metadata.holder;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.converters.Converter;
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
