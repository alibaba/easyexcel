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

import com.alibaba.excel.enums.CacheLocationEnum;
import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString.Exclude;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
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
     * charset.
     * Only work on the CSV file
     */
    private Charset charset;

    /**
     * Set the encoding prefix in the csv file, otherwise the office may open garbled characters.
     * Default true.
     */
    private Boolean withBom;

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
    private Map<Short, Map<WriteCellStyle, CellStyle>> cellStyleIndexMap;
    /**
     * Used to font.
     */
    private Map<WriteFont, Font> fontMap;
    /**
     * Used to data format.
     */
    private Map<DataFormatData, Short> dataFormatMap;

    /**
     * handler context
     */
    @Exclude
    private WorkbookWriteHandlerContext workbookWriteHandlerContext;

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

        if (writeWorkbook.getWithBom() == null) {
            this.withBom = Boolean.TRUE;
        } else {
            this.withBom = writeWorkbook.getWithBom();
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

        // init handler
        initHandler(writeWorkbook, null);

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
        this.cellStyleIndexMap = MapUtils.newHashMap();
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
     * @param originCellStyle
     * @return
     */
    public CellStyle createCellStyle(WriteCellStyle writeCellStyle, CellStyle originCellStyle) {
        if (writeCellStyle == null) {
            return originCellStyle;
        }

        short styleIndex = -1;
        Font originFont = null;
        boolean useCache = true;
        if (originCellStyle != null) {
            styleIndex = originCellStyle.getIndex();
            if (originCellStyle instanceof XSSFCellStyle) {
                originFont = ((XSSFCellStyle)originCellStyle).getFont();
            } else if (originCellStyle instanceof HSSFCellStyle) {
                originFont = ((HSSFCellStyle)originCellStyle).getFont(workbook);
            }
            useCache = false;
        }

        Map<WriteCellStyle, CellStyle> cellStyleMap = cellStyleIndexMap.computeIfAbsent(styleIndex,
            key -> MapUtils.newHashMap());
        CellStyle cellStyle = cellStyleMap.get(writeCellStyle);
        if (cellStyle != null) {
            return cellStyle;
        }
        if (log.isDebugEnabled()) {
            log.info("create new style:{},{}", writeCellStyle, originCellStyle);
        }
        WriteCellStyle tempWriteCellStyle = new WriteCellStyle();
        WriteCellStyle.merge(writeCellStyle, tempWriteCellStyle);

        cellStyle = StyleUtil.buildCellStyle(workbook, originCellStyle, tempWriteCellStyle);
        Short dataFormat = createDataFormat(tempWriteCellStyle.getDataFormatData(), useCache);
        if (dataFormat != null) {
            cellStyle.setDataFormat(dataFormat);
        }
        Font font = createFont(tempWriteCellStyle.getWriteFont(), originFont, useCache);
        if (font != null) {
            cellStyle.setFont(font);
        }
        cellStyleMap.put(tempWriteCellStyle, cellStyle);
        return cellStyle;
    }

    /**
     * create a font.
     *
     * @param writeFont
     * @param originFont
     * @param useCache
     * @return
     */
    public Font createFont(WriteFont writeFont, Font originFont, boolean useCache) {
        if (!useCache) {
            return StyleUtil.buildFont(workbook, originFont, writeFont);
        }
        WriteFont tempWriteFont = new WriteFont();
        WriteFont.merge(writeFont, tempWriteFont);

        Font font = fontMap.get(tempWriteFont);
        if (font != null) {
            return font;
        }
        font = StyleUtil.buildFont(workbook, originFont, tempWriteFont);
        fontMap.put(tempWriteFont, font);
        return font;
    }

    /**
     * create a data format.
     *
     * @param dataFormatData
     * @param useCache
     * @return
     */
    public Short createDataFormat(DataFormatData dataFormatData, boolean useCache) {
        if (dataFormatData == null) {
            return null;
        }
        if (!useCache) {
            return StyleUtil.buildDataFormat(workbook, dataFormatData);
        }
        DataFormatData tempDataFormatData = new DataFormatData();
        DataFormatData.merge(dataFormatData, tempDataFormatData);

        Short dataFormat = dataFormatMap.get(tempDataFormatData);
        if (dataFormat != null) {
            return dataFormat;
        }
        dataFormat = StyleUtil.buildDataFormat(workbook, tempDataFormatData);
        dataFormatMap.put(tempDataFormatData, dataFormat);
        return dataFormat;
    }

}
