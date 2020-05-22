package com.alibaba.excel.write.metadata.holder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.write.metadata.WriteWorkbook;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
public class WriteWorkbookHolder extends AbstractWriteHolder {
    /***
     * Current poi Workbook.This is only for writing, and there may be no data in version 07 when template data needs to
     * be read.
     * <ul>
     * <li>03:{@link HSSFWorkbook}</li>
     * <li>07:{@link SXSSFWorkbook}</li>
     * </ul>
     */
    private Workbook workbook;
    /***
     * Current poi Workbook.Be sure to use and this method when reading template data.
     * <ul>
     * <li>03:{@link HSSFWorkbook}</li>
     * <li>07:{@link XSSFWorkbook}</li>
     * </ul>
     */
    private Workbook cachedWorkbook;
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
     * Temporary template file stream.
     * <p>
     * A temporary file stream needs to be created in order not to modify the original template file.
     */
    private InputStream tempTemplateInputStream;
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
    private Map<Integer, WriteSheetHolder> hasBeenInitializedSheetIndexMap;
    /**
     * prevent duplicate creation of sheet objects
     */
    private Map<String, WriteSheetHolder> hasBeenInitializedSheetNameMap;
    /**
     * Whether the encryption
     */
    private String password;
    /**
     * Write excel in memory. Default false,the cache file is created and finally written to excel.
     * <p>
     * Comment and RichTextString are only supported in memory mode.
     */
    private Boolean inMemory;
    /**
     * Excel is also written in the event of an exception being thrown.The default false.
     */
    private Boolean writeExcelOnException;


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
        if (writeWorkbook.getAutoCloseStream() == null) {
            this.autoCloseStream = Boolean.TRUE;
        } else {
            this.autoCloseStream = writeWorkbook.getAutoCloseStream();
        }
        try {
            copyTemplate();
        } catch (IOException e) {
            throw new ExcelGenerateException("Copy template failure.", e);
        }
        if (writeWorkbook.getExcelType() == null) {
            boolean isXls = (file != null && file.getName().endsWith(ExcelTypeEnum.XLS.getValue()))
                || (writeWorkbook.getTemplateFile() != null
                    && writeWorkbook.getTemplateFile().getName().endsWith(ExcelTypeEnum.XLS.getValue()));
            if (isXls) {
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
        this.hasBeenInitializedSheetIndexMap = new HashMap<Integer, WriteSheetHolder>();
        this.hasBeenInitializedSheetNameMap = new HashMap<String, WriteSheetHolder>();
        this.password = writeWorkbook.getPassword();
        if (writeWorkbook.getInMemory() == null) {
            this.inMemory = Boolean.FALSE;
        } else {
            this.inMemory = writeWorkbook.getInMemory();
        }
        if (writeWorkbook.getWriteExcelOnException() == null) {
            this.writeExcelOnException = Boolean.FALSE;
        } else {
            this.writeExcelOnException = writeWorkbook.getWriteExcelOnException();
        }
    }

    private void copyTemplate() throws IOException {
        if (writeWorkbook.getTemplateFile() == null && writeWorkbook.getTemplateInputStream() == null) {
            return;
        }
        byte[] templateFileByte = null;
        if (writeWorkbook.getTemplateFile() != null) {
            templateFileByte = FileUtils.readFileToByteArray(writeWorkbook.getTemplateFile());
        } else if (writeWorkbook.getTemplateInputStream() != null) {
            try {
                templateFileByte = IoUtils.toByteArray(writeWorkbook.getTemplateInputStream());
            } finally {
                if (autoCloseStream) {
                    writeWorkbook.getTemplateInputStream().close();
                }
            }
        }
        this.tempTemplateInputStream = new ByteArrayInputStream(templateFileByte);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Workbook getCachedWorkbook() {
        return cachedWorkbook;
    }

    public void setCachedWorkbook(Workbook cachedWorkbook) {
        this.cachedWorkbook = cachedWorkbook;
    }

    public Map<Integer, WriteSheetHolder> getHasBeenInitializedSheetIndexMap() {
        return hasBeenInitializedSheetIndexMap;
    }

    public void setHasBeenInitializedSheetIndexMap(Map<Integer, WriteSheetHolder> hasBeenInitializedSheetIndexMap) {
        this.hasBeenInitializedSheetIndexMap = hasBeenInitializedSheetIndexMap;
    }

    public Map<String, WriteSheetHolder> getHasBeenInitializedSheetNameMap() {
        return hasBeenInitializedSheetNameMap;
    }

    public void setHasBeenInitializedSheetNameMap(Map<String, WriteSheetHolder> hasBeenInitializedSheetNameMap) {
        this.hasBeenInitializedSheetNameMap = hasBeenInitializedSheetNameMap;
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

    public InputStream getTempTemplateInputStream() {
        return tempTemplateInputStream;
    }

    public void setTempTemplateInputStream(InputStream tempTemplateInputStream) {
        this.tempTemplateInputStream = tempTemplateInputStream;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getInMemory() {
        return inMemory;
    }

    public void setInMemory(Boolean inMemory) {
        this.inMemory = inMemory;
    }

    public Boolean getWriteExcelOnException() {
        return writeExcelOnException;
    }

    public void setWriteExcelOnException(Boolean writeExcelOnException) {
        this.writeExcelOnException = writeExcelOnException;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }
}
