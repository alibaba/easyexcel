package com.alibaba.excel.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.holder.write.SheetHolder;
import com.alibaba.excel.metadata.holder.write.WorkbookHolder;

/**
 *
 * @author jipengfei
 */
public class AnalysisContextImpl implements AnalysisContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisContextImpl.class);
    /**
     * The Workbook currently written
     */
    private WorkbookHolder currentWorkbookHolder;
    /**
     * Current sheet holder
     */
    private SheetHolder currentSheetHolder;

    public AnalysisContextImpl(com.alibaba.excel.metadata.Workbook workbook) {
        if (workbook == null) {
            throw new IllegalArgumentException("Workbook argument cannot be null");
        }
        currentWorkbookHolder = WorkbookHolder.buildReadWorkbookHolder(workbook);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initialization 'AnalysisContextImpl' complete");
        }
    }

    @Override
    public void currentSheet(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet argument cannot be null");
        }
        if (sheet.getSheetNo() == null || sheet.getSheetNo() <= 0) {
            sheet.setSheetNo(0);
        }
        currentSheetHolder = SheetHolder.buildReadWorkSheetHolder(sheet, currentWorkbookHolder);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Began to read：{}", sheet);
        }
    }
}
