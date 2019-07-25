package com.alibaba.excel.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelExecutor;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.util.StringUtils;

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

    public AnalysisContextImpl(ReadWorkbook readWorkbook) {
        if (readWorkbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        readWorkbookHolder = new ReadWorkbookHolder(readWorkbook);
        currentReadHolder = readWorkbookHolder;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'AnalysisContextImpl' complete");
        }
    }

    @Override
    public void currentSheet(ExcelExecutor excelExecutor, ReadSheet readSheet) {
        if (readSheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        readSheetHolder = new ReadSheetHolder(readSheet, readWorkbookHolder);
        currentReadHolder = readSheetHolder;
        selectSheet(excelExecutor);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Began to read：{}", readSheet);
        }
    }

    private void selectSheet(ExcelExecutor excelExecutor) {
        if (readSheetHolder.getSheetNo() != null && readSheetHolder.getSheetNo() >= 0) {
            for (ReadSheet readSheetExcel : excelExecutor.sheetList()) {
                if (readSheetExcel.getSheetNo().equals(readSheetHolder.getSheetNo())) {
                    readSheetHolder.setSheetName(readSheetExcel.getSheetName());
                    return;
                }
            }
            throw new ExcelAnalysisException("Can not find sheet:" + readSheetHolder.getSheetNo());
        }
        if (!StringUtils.isEmpty(readSheetHolder.getSheetName())) {
            for (ReadSheet readSheetExcel : excelExecutor.sheetList()) {
                String sheetName = readSheetExcel.getSheetName();
                if (sheetName == null) {
                    continue;
                }
                if (readSheetHolder.globalConfiguration().getAutoTrim()) {
                    sheetName = sheetName.trim();
                }
                if (sheetName.equals(readSheetHolder.getSheetName())) {
                    readSheetHolder.setSheetNo(readSheetHolder.getSheetNo());
                    return;
                }
            }
        }
        ReadSheet readSheetExcel = excelExecutor.sheetList().get(0);
        readSheetHolder.setSheetNo(readSheetExcel.getSheetNo());
        readSheetHolder.setSheetName(readSheetExcel.getSheetName());
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
        return readSheetHolder;
    }

    @Override
    public Object getCustom() {
        return readWorkbookHolder.getCustomObject();
    }
}
