package com.alibaba.excel.context;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import com.alibaba.excel.read.processor.AnalysisEventProcessor;
import com.alibaba.excel.read.processor.DefaultAnalysisEventProcessor;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 *
 * @author jipengfei
 */
public class AnalysisContextImpl implements AnalysisContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisContextImpl.class);
    /**
     * The Workbook currently written
     */
    private ReadWorkbookHolder readWorkbookHolder;
    /**
     * Current sheet holder
     */
    private ReadSheetHolder readSheetHolder;
    /**
     * Current row holder
     */
    private ReadRowHolder readRowHolder;
    /**
     * Configuration of currently operated cell
     */
    private ReadHolder currentReadHolder;
    /**
     * Event processor
     */
    private AnalysisEventProcessor analysisEventProcessor;

    public AnalysisContextImpl(ReadWorkbook readWorkbook, ExcelTypeEnum actualExcelType) {
        if (readWorkbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        switch (actualExcelType) {
            case XLS:
                readWorkbookHolder = new XlsReadWorkbookHolder(readWorkbook);
                break;
            case XLSX:
                readWorkbookHolder = new XlsxReadWorkbookHolder(readWorkbook);
                break;
            default:
                break;
        }
        currentReadHolder = readWorkbookHolder;
        analysisEventProcessor = new DefaultAnalysisEventProcessor();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'AnalysisContextImpl' complete");
        }
    }

    @Override
    public void currentSheet(ReadSheet readSheet) {
        switch (readWorkbookHolder.getExcelType()) {
            case XLS:
                readSheetHolder = new XlsReadSheetHolder(readSheet, readWorkbookHolder);
                break;
            case XLSX:
                readSheetHolder = new XlsxReadSheetHolder(readSheet, readWorkbookHolder);
                break;
            default:
                break;
        }
        currentReadHolder = readSheetHolder;
        if (readWorkbookHolder.getHasReadSheet().contains(readSheetHolder.getSheetNo())) {
            throw new ExcelAnalysisException("Cannot read sheet repeatedly.");
        }
        readWorkbookHolder.getHasReadSheet().add(readSheetHolder.getSheetNo());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Began to read：{}", readSheetHolder);
        }
    }

    @Override
    public ReadWorkbookHolder readWorkbookHolder() {
        return readWorkbookHolder;
    }

    @Override
    public ReadSheetHolder readSheetHolder() {
        return readSheetHolder;
    }

    @Override
    public ReadRowHolder readRowHolder() {
        return readRowHolder;
    }

    @Override
    public void readRowHolder(ReadRowHolder readRowHolder) {
        this.readRowHolder = readRowHolder;
    }

    @Override
    public ReadHolder currentReadHolder() {
        return currentReadHolder;
    }

    @Override
    public Object getCustom() {
        return readWorkbookHolder.getCustomObject();
    }

    @Override
    public AnalysisEventProcessor analysisEventProcessor() {
        return analysisEventProcessor;
    }

    @Override
    public List<ReadSheet> readSheetList() {
        return null;
    }

    @Override
    public void readSheetList(List<ReadSheet> readSheetList) {

    }

    @Override
    public Sheet getCurrentSheet() {
        Sheet sheet = new Sheet(readSheetHolder.getSheetNo() + 1);
        sheet.setSheetName(readSheetHolder.getSheetName());
        sheet.setHead(readSheetHolder.getHead());
        sheet.setClazz(readSheetHolder.getClazz());
        sheet.setHeadLineMun(readSheetHolder.getHeadRowNumber());
        return sheet;
    }

    @Override
    public ExcelTypeEnum getExcelType() {
        return readWorkbookHolder.getExcelType();
    }

    @Override
    public InputStream getInputStream() {
        return readWorkbookHolder.getInputStream();
    }

    @Override
    public Integer getCurrentRowNum() {
        return readRowHolder.getRowIndex();
    }

    @Override
    public Integer getTotalCount() {
        return readSheetHolder.getTotal();
    }

    @Override
    public Object getCurrentRowAnalysisResult() {
        return readRowHolder.getCurrentRowAnalysisResult();
    }

    @Override
    public void interrupt() {
        throw new ExcelAnalysisException("interrupt error");
    }
}
