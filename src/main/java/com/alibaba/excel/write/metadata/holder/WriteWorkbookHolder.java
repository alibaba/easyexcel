package com.alibaba.excel.write.metadata.holder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteWorkbook;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
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
     * Final output file
     * <p>
     * If 'outputStream' and 'file' all not empty,file first
     */
    private File file;
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
    /**
     * When using SXSSFWorkbook, you can't get the actual last line.But we need to read the last line when we are using
     * the template, so we cache it
     */
    private Map<Integer, Integer> templateLastRowMap;

    public WriteWorkbookHolder(WriteWorkbook writeWorkbook) {
        super(writeWorkbook, null, writeWorkbook.getConvertAllFiled());
        this.writeWorkbook = writeWorkbook;
        this.file = writeWorkbook.getFile();
        if (file != null) {
            try {
                this.outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new ExcelGenerateException("Can not found file.", e);
            }
        } else {
            this.outputStream = writeWorkbook.getOutputStream();
        }
        if (writeWorkbook.getTemplateInputStream() != null) {
            if (writeWorkbook.getTemplateInputStream().markSupported()) {
                this.templateInputStream = writeWorkbook.getTemplateInputStream();
            } else {
                this.templateInputStream = new BufferedInputStream(writeWorkbook.getTemplateInputStream());
            }
        }
        this.templateFile = writeWorkbook.getTemplateFile();
        if (writeWorkbook.getAutoCloseStream() == null) {
            this.autoCloseStream = Boolean.TRUE;
        } else {
            this.autoCloseStream = writeWorkbook.getAutoCloseStream();
        }
        if (writeWorkbook.getExcelType() == null) {
            if (file != null && file.getName().endsWith(ExcelTypeEnum.XLS.getValue())) {
                this.excelType = ExcelTypeEnum.XLS;
            } else {
                this.excelType = ExcelTypeEnum.XLSX;
            }
        } else {
            this.excelType = writeWorkbook.getExcelType();
        }
        if (writeWorkbook.getMandatoryUseInputStream() == null) {
            this.mandatoryUseInputStream = Boolean.FALSE;
        } else {
            this.mandatoryUseInputStream = writeWorkbook.getMandatoryUseInputStream();
        }
        this.hasBeenInitializedSheet = new HashMap<Integer, WriteSheetHolder>();
        this.templateLastRowMap = new HashMap<Integer, Integer>(8);
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public Map<Integer, Integer> getTemplateLastRowMap() {
        return templateLastRowMap;
    }

    public void setTemplateLastRowMap(Map<Integer, Integer> templateLastRowMap) {
        this.templateLastRowMap = templateLastRowMap;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }
}
