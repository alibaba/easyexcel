package com.alibaba.excel.metadata.holder;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.write.handler.DefaultWriteHandlerLoader;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Workbook holder
 *
 * @author zhuangjiaju
 */
public class WorkbookHolder extends AbstractConfigurationSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkbookHolder.class);
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
     * The default is all excel objects.if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a
     * field. if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
     *
     * @deprecated Just to be compatible with historical data, The default is always going to be convert all filed.
     */
    @Deprecated
    private Boolean convertAllFiled;

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
        if (workbook.getConvertAllFiled() == null) {
            this.convertAllFiled = Boolean.TRUE;
        } else {
            this.convertAllFiled = workbook.getConvertAllFiled();
        }
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
        handlerList.addAll(DefaultWriteHandlerLoader.loadDefaultHandler());
        setWriteHandlerMap(sortAndClearUpHandler(handlerList, null));
        Map<Class, Converter> converterMap = DefaultConverterLoader.loadDefaultWriteConverter();
        if (workbook.getCustomConverterMap() != null && !workbook.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(workbook.getCustomConverterMap());
        }
        setConverterMap(converterMap);
        setHasBeenInitializedSheet(new HashMap<Integer, SheetHolder>());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Wookbook writeHandlerMap:{}", getWriteHandlerMap());
        }
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

    public Boolean getConvertAllFiled() {
        return convertAllFiled;
    }

    public void setConvertAllFiled(Boolean convertAllFiled) {
        this.convertAllFiled = convertAllFiled;
    }
}
