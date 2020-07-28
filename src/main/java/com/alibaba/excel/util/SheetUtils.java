package com.alibaba.excel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

/**
 * Sheet utils
 *
 * @author Jiaju Zhuang
 */
public class SheetUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SheetUtils.class);

    private SheetUtils() {}

    /**
     * Match the parameters to the actual sheet
     *
     * @param readSheet
     *            actual sheet
     * @param analysisContext
     * @return
     */
    public static ReadSheet match(ReadSheet readSheet, AnalysisContext analysisContext) {
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();
        if (readWorkbookHolder.getReadAll()) {
            return readSheet;
        }
        for (ReadSheet parameterReadSheet : readWorkbookHolder.getParameterSheetDataList()) {
            if (parameterReadSheet == null) {
                continue;
            }
            if (parameterReadSheet.getSheetNo() == null && parameterReadSheet.getSheetName() == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("The first is read by default.");
                }
                parameterReadSheet.setSheetNo(0);
            }
            boolean match = (parameterReadSheet.getSheetNo() != null
                && parameterReadSheet.getSheetNo().equals(readSheet.getSheetNo()));
            if (!match) {
                String parameterSheetName = parameterReadSheet.getSheetName();
                if (!StringUtils.isEmpty(parameterSheetName)) {
                    boolean autoTrim = (parameterReadSheet.getAutoTrim() != null && parameterReadSheet.getAutoTrim())
                        || (parameterReadSheet.getAutoTrim() == null
                            && analysisContext.readWorkbookHolder().getGlobalConfiguration().getAutoTrim());
                    if (autoTrim) {
                        parameterSheetName = parameterSheetName.trim();
                    }
                    match = parameterSheetName.equals(readSheet.getSheetName());
                }
            }
            if (match) {
                readSheet.copyBasicParameter(parameterReadSheet);
                return readSheet;
            }
        }
        return null;
    }

}
