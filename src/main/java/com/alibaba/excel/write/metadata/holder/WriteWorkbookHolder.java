package com.alibaba.excel.write.metadata.holder;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteWorkbook;

/**
 * Workbook holder
 *
 * @author zhuangjiaju
 */
public class WriteWorkbookHolder extends AbstractWriteHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteWorkbookHolder.class);
    /***
     * poi Workbook
     */
    private Workbook workbook;

    /**
     * current param
     */
    private WriteWorkbook writeWorkbook;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * Template input stream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private InputStream templateInputStream;
    /**
     * Template file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private File templateFile;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * Mandatory use 'inputStream'
     */
    private Boolean mandatoryUseInputStream;
    /**
     * prevent duplicate creation of sheet objects
     */
    private Map<Integer, WriteSheetHolder> hasBeenInitializedSheet;

    public WriteWorkbookHolder(WriteWorkbook writeWorkbook) {
        super(writeWorkbook, null, writeWorkbook.getConvertAllFiled());
        this.writeWorkbook = writeWorkbook;
        this.outputStream = writeWorkbook.getOutputStream();
        this.templateInputStream = writeWorkbook.getTemplateInputStream();
        this.templateFile = writeWorkbook.getTemplateFile();
        if (writeWorkbook.getAutoCloseStream() == null) {
            this.autoCloseStream = Boolean.TRUE;
        } else {
            this.autoCloseStream = writeWorkbook.getAutoCloseStream();
        }
        if (writeWorkbook.getExcelType() == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("The default specified xlsx.");
            }
            this.excelType = ExcelTypeEnum.XLSX;
        } else {
            this.excelType = writeWorkbook.getExcelType();
        }
        if (writeWorkbook.getMandatoryUseInputStream() == null) {
            this.mandatoryUseInputStream = Boolean.FALSE;
        } else {
            this.mandatoryUseInputStream = writeWorkbook.getMandatoryUseInputStream();
        }
        this.hasBeenInitializedSheet = new HashMap<Integer, WriteSheetHolder>();
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Map<Integer, WriteSheetHolder> getHasBeenInitializedSheet() {
        return hasBeenInitializedSheet;
    }

    public void setHasBeenInitializedSheet(Map<Integer, WriteSheetHolder> hasBeenInitializedSheet) {
        this.hasBeenInitializedSheet = hasBeenInitializedSheet;
    }

    public WriteWorkbook getWriteWorkbook() {
        return writeWorkbook;
    }

    public void setWriteWorkbook(WriteWorkbook writeWorkbook) {
        this.writeWorkbook = writeWorkbook;
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

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public Boolean getAutoCloseStream() {
        return autoCloseStream;
    }

    public void setAutoCloseStream(Boolean autoCloseStream) {
        this.autoCloseStream = autoCloseStream;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Boolean getMandatoryUseInputStream() {
        return mandatoryUseInputStream;
    }

    public void setMandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        this.mandatoryUseInputStream = mandatoryUseInputStream;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }
}
