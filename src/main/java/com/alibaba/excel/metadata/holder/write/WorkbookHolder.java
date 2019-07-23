package com.alibaba.excel.metadata.holder.write;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.listener.ModelBuildEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.DefaultWriteHandlerLoader;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Workbook holder
 *
 * @author zhuangjiaju
 */
public class WorkbookHolder extends AbstractWriteConfiguration {
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
     * <li>write: Template input stream
     * <li>read: Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private InputStream inputStream;
    /**
     * <li>write: Template file
     * <li>read: Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private File file;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * This object can be read in the Listener {@link AnalysisEventListener#invoke(Object, AnalysisContext)}
     * {@link AnalysisContext#getCustom()}
     *
     */
    private Object readCustomObject;
    /**
     * A cache that stores temp data to save memory.Default use {@link com.alibaba.excel.cache.Ehcache}
     */
    private ReadCache readCache;
    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * @return
     */
    private Boolean use1904windowing;

    /**
     * Mmandatory use 'inputStream'
     */
    private Boolean mandatoryUseInputStream;

    /**
     * Temporary files when reading excel
     */
    private File readTempFile;

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

    public static WorkbookHolder buildWriteWorkbookHolder(com.alibaba.excel.metadata.Workbook workbook) {
        WorkbookHolder workbookHolder = buildBaseWorkbookHolder(workbook);
        workbookHolder.setNewInitialization(Boolean.TRUE);
        if (workbook.getNeedHead() == null) {
            workbookHolder.setNeedHead(Boolean.TRUE);
        } else {
            workbookHolder.setNeedHead(workbook.getNeedHead());
        }
        if (workbook.getWriteRelativeHeadRowIndex() == null) {
            workbookHolder.setWriteRelativeHeadRowIndex(0);
        } else {
            workbookHolder.setWriteRelativeHeadRowIndex(workbook.getWriteRelativeHeadRowIndex());
        }
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (workbook.getCustomWriteHandlerList() != null && !workbook.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(workbook.getCustomWriteHandlerList());
        }
        handlerList.addAll(DefaultWriteHandlerLoader.loadDefaultHandler());
        workbookHolder.setWriteHandlerMap(workbookHolder.sortAndClearUpHandler(handlerList, null));

        Map<Class, Converter> converterMap = DefaultConverterLoader.loadDefaultWriteConverter();
        if (workbook.getCustomConverterList() != null && !workbook.getCustomConverterList().isEmpty()) {
            for (Converter converter : workbook.getCustomConverterList()) {
                converterMap.put(converter.getClass(), converter);
            }
        }
        workbookHolder.setWriteConverterMap(converterMap);
        workbookHolder.setHasBeenInitializedSheet(new HashMap<Integer, SheetHolder>());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Wookbook writeHandlerMap:{}", workbookHolder.getWriteHandlerMap());
        }
        return workbookHolder;
    }

    public static WorkbookHolder buildReadWorkbookHolder(com.alibaba.excel.metadata.Workbook workbook) {
        WorkbookHolder workbookHolder = buildBaseWorkbookHolder(workbook);
        if (workbook.getFile() == null && workbookHolder.getInputStream() == null) {
            throw new ExcelAnalysisException("Read excel 'file' and 'inputStream' cannot be empty at the same time!");
        }
        workbookHolder.setReadCustomObject(workbook.getReadCustomObject());
        workbookHolder.setReadHeadRowNumber(workbook.getReadHeadRowNumber());
        workbookHolder.setReadCache(workbook.getReadCache());

        Map<ConverterKey, Converter> converterMap = DefaultConverterLoader.loadDefaultReadConverter();
        if (workbook.getCustomConverterList() != null && !workbook.getCustomConverterList().isEmpty()) {
            for (Converter converter : workbook.getCustomConverterList()) {
                converterMap.put(ConverterKey.buildConverterKey(converter), converter);
            }
        }
        workbookHolder.setReadConverterMap(converterMap);

        List<ReadListener> readListenerList = new ArrayList<ReadListener>();
        readListenerList.add(new ModelBuildEventListener());
        if (workbook.getCustomReadListenerList() != null && !workbook.getCustomReadListenerList().isEmpty()) {
            readListenerList.addAll(workbook.getCustomReadListenerList());
        }
        workbookHolder.setReadListenerList(readListenerList);
        return workbookHolder;
    }

    private static WorkbookHolder buildBaseWorkbookHolder(com.alibaba.excel.metadata.Workbook workbook) {
        WorkbookHolder workbookHolder = new WorkbookHolder();
        workbookHolder.setUse1904windowing(workbook.getUse1904windowing());
        workbookHolder.setWorkbookParam(workbook);
        workbookHolder.setInputStream(workbook.getInputStream());
        workbookHolder.setFile(workbook.getFile());
        workbookHolder.setExcelType(workbook.getExcelType());
        workbookHolder.setHead(workbook.getHead());
        workbookHolder.setClazz(workbook.getClazz());
        if (workbook.getConvertAllFiled() == null) {
            workbookHolder.setConvertAllFiled(Boolean.TRUE);
        } else {
            workbookHolder.setConvertAllFiled(workbook.getConvertAllFiled());
        }
        if (workbook.getAutoCloseStream() == null) {
            workbookHolder.setAutoCloseStream(Boolean.TRUE);
        } else {
            workbookHolder.setAutoCloseStream(workbook.getAutoCloseStream());
        }
        if (workbook.getAutoTrim() == null) {
            workbookHolder.setAutoTrim(Boolean.TRUE);
        } else {
            workbookHolder.setAutoTrim(workbook.getNeedHead());
        }
        return workbookHolder;
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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Object getReadCustomObject() {
        return readCustomObject;
    }

    public void setReadCustomObject(Object readCustomObject) {
        this.readCustomObject = readCustomObject;
    }

    public ReadCache getReadCache() {
        return readCache;
    }

    public void setReadCache(ReadCache readCache) {
        this.readCache = readCache;
    }

    public Boolean getUse1904windowing() {
        return use1904windowing;
    }

    public void setUse1904windowing(Boolean use1904windowing) {
        this.use1904windowing = use1904windowing;
    }

    public Boolean getMandatoryUseInputStream() {
        return mandatoryUseInputStream;
    }

    public void setMandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        this.mandatoryUseInputStream = mandatoryUseInputStream;
    }

    public File getReadTempFile() {
        return readTempFile;
    }

    public void setReadTempFile(File readTempFile) {
        this.readTempFile = readTempFile;
    }
}
