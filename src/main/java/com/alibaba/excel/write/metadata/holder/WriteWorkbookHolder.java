package com.alibaba.excel.write.metadata.holder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
@Data
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
     * If 'outputStream' and 'file' all not empty, file first
     */
    private File file;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * output charset
     */
    private Charset charset;
    /**
     * Template input stream
     * <p>
     * If 'inputStream' and 'file' all not empty, file first
     */
    private InputStream templateInputStream;
    /**
     * Template file
     * <p>
     * If 'inputStream' and 'file' all not empty, file first
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
     * Write excel in memory. Default false, the cache file is created and finally written to excel.
     * <p>
     * Comment and RichTextString are only supported in memory mode.
     */
    private Boolean inMemory;
    /**
     * Excel is also written in the event of an exception being thrown.The default false.
     */
    private Boolean writeExcelOnException;

    /**
     * Used to cell style.
     */
    private Map<WriteCellStyle, CellStyle> cellStyleMap;
    /**
     * Used to font.
     */
    private Map<WriteFont, Font> fontMap;
    /**
     * Used to data format.
     */
    private Map<DataFormatData, Short> dataFormatMap;

    public WriteWorkbookHolder(WriteWorkbook writeWorkbook) {
        super(writeWorkbook, null);
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

        if (writeWorkbook.getCharset() == null) {
            this.charset = Charset.defaultCharset();
        } else {
            this.charset = writeWorkbook.getCharset();
        }

        if (writeWorkbook.getAutoCloseStream() == null) {
            this.autoCloseStream = Boolean.TRUE;
        } else {
            this.autoCloseStream = writeWorkbook.getAutoCloseStream();
        }
        if (writeWorkbook.getExcelType() == null) {
            boolean isXls = (file != null && file.getName().endsWith(ExcelTypeEnum.XLS.getValue()))
                || (writeWorkbook.getTemplateFile() != null
                && writeWorkbook.getTemplateFile().getName().endsWith(ExcelTypeEnum.XLS.getValue()));
            if (isXls) {
                this.excelType = ExcelTypeEnum.XLS;
            } else {
                boolean isCsv = (file != null && file.getName().endsWith(ExcelTypeEnum.CSV.getValue()))
                    || (writeWorkbook.getTemplateFile() != null
                    && writeWorkbook.getTemplateFile().getName().endsWith(ExcelTypeEnum.CSV.getValue()));
                if (isCsv) {
                    this.excelType = ExcelTypeEnum.CSV;
                } else {
                    this.excelType = ExcelTypeEnum.XLSX;
                }
            }
        } else {
            this.excelType = writeWorkbook.getExcelType();
        }
        try {
            copyTemplate();
        } catch (IOException e) {
            throw new ExcelGenerateException("Copy template failure.", e);
        }
        if (writeWorkbook.getMandatoryUseInputStream() == null) {
            this.mandatoryUseInputStream = Boolean.FALSE;
        } else {
            this.mandatoryUseInputStream = writeWorkbook.getMandatoryUseInputStream();
        }
        this.hasBeenInitializedSheetIndexMap = new HashMap<>();
        this.hasBeenInitializedSheetNameMap = new HashMap<>();
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
        this.cellStyleMap = MapUtils.newHashMap();
        this.fontMap = MapUtils.newHashMap();
        this.dataFormatMap = MapUtils.newHashMap();
    }

    private void copyTemplate() throws IOException {
        if (writeWorkbook.getTemplateFile() == null && writeWorkbook.getTemplateInputStream() == null) {
            return;
        }
        if (this.excelType == ExcelTypeEnum.CSV) {
            throw new ExcelGenerateException("csv cannot use template.");
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

    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }

    /**
     * create a cell style.
     *
     * @param writeCellStyle
     * @return
     */
    public CellStyle createCellStyle(WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = cellStyleMap.get(writeCellStyle);
        if (cellStyle != null) {
            return cellStyle;
        }
        cellStyle = StyleUtil.buildCellStyle(workbook, writeCellStyle);
        cellStyle.setDataFormat(createDataFormat(writeCellStyle.getDataFormatData()));
        cellStyle.setFont(createFont(writeCellStyle.getWriteFont()));
        cellStyleMap.put(writeCellStyle, cellStyle);
        return cellStyle;
    }

    /**
     * create a font.
     *
     * @param writeFont
     * @return
     */
    public Font createFont(WriteFont writeFont) {
        Font font = fontMap.get(writeFont);
        if (font != null) {
            return font;
        }
        font = StyleUtil.buildFont(workbook, writeFont);
        fontMap.put(writeFont, font);
        return font;
    }

    /**
     * create a data format.
     *
     * @param dataFormatData
     * @return
     */
    public Short createDataFormat(DataFormatData dataFormatData) {
        Short dataFormat = dataFormatMap.get(dataFormatData);
        if (dataFormat != null) {
            return dataFormat;
        }
        dataFormat = StyleUtil.buildDataFormat(workbook, dataFormatData);
        dataFormatMap.put(dataFormatData, dataFormat);
        return dataFormat;
    }

}
