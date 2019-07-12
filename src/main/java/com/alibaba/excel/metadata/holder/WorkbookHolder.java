package com.alibaba.excel.metadata.holder;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterBuilder;
import com.alibaba.excel.write.handler.DefaultWriteHandlerBuilder;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Workbook holder
 *
 * @author zhuangjiaju
 */
public class WorkbookHolder extends AbstractConfigurationSelector {
    /***
     * poi Workbook
     */
    private Workbook workbook;
    /**
     * prevent duplicate creation of sheet objects
     */
    private Map<Integer, SheetHolder> hasBeenInitializedSheet;
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
    /**
     * Default true
     */
    private Boolean autoCloseStream;

    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    private com.alibaba.excel.event.WriteHandler writeHandler;

    public WorkbookHolder(com.alibaba.excel.metadata.Workbook workbook) {
        super();
        this.workbookParam = workbook;
        this.templateInputStream = workbook.getTemplateInputStream();
        this.outputStream = workbook.getOutputStream();
        this.templateInputStream = workbook.getTemplateInputStream();
        setHead(workbook.getHead());
        setClazz(workbook.getClazz());
        setNewInitialization(Boolean.TRUE);
        if (workbook.getAutoCloseStream() == null) {
            setAutoCloseStream(Boolean.TRUE);
        } else {
            setAutoCloseStream(workbook.getAutoCloseStream());
        }
        if (workbook.getNeedHead() == null) {
            setNeedHead(Boolean.TRUE);
        } else {
            setNeedHead(workbook.getNeedHead());
        }
        if (workbook.getWriteRelativeHeadRowIndex() == null) {
            setWriteRelativeHeadRowIndex(0);
        } else {
            setWriteRelativeHeadRowIndex(workbook.getWriteRelativeHeadRowIndex());
        }
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (workbook.getCustomWriteHandlerList() != null && !workbook.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(workbook.getCustomWriteHandlerList());
        }
        handlerList.addAll(DefaultWriteHandlerBuilder.loadDefaultHandler());
        setWriteHandlerMap(sortAndClearUpHandler(handlerList, null));
        Map<Class, Converter> converterMap = DefaultConverterBuilder.loadDefaultWriteConverter();
        if (workbook.getCustomConverterMap() != null && !workbook.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(workbook.getCustomConverterMap());
        }
        setConverterMap(converterMap);
        setHasBeenInitializedSheet(new HashMap<Integer, SheetHolder>());
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Map<Integer, SheetHolder> getHasBeenInitializedSheet() {
        return hasBeenInitializedSheet;
    }

    public void setHasBeenInitializedSheet(Map<Integer, SheetHolder> hasBeenInitializedSheet) {
        this.hasBeenInitializedSheet = hasBeenInitializedSheet;
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

    public com.alibaba.excel.event.WriteHandler getWriteHandler() {
        return writeHandler;
    }

    public void setWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
    }

    public Boolean getAutoCloseStream() {
        return autoCloseStream;
    }

    public void setAutoCloseStream(Boolean autoCloseStream) {
        this.autoCloseStream = autoCloseStream;
    }
}
