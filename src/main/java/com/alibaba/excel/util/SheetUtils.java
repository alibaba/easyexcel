package com.alibaba.excel.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.read.metadata.ReadSheet;

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
     * @param parameterReadSheetList
     *            parameters
     * @param readAll
     * @return
     */
    public static ReadSheet match(ReadSheet readSheet, List<ReadSheet> parameterReadSheetList, Boolean readAll,
        GlobalConfiguration globalConfiguration) {
        if (readAll) {
            return readSheet;
        }
        for (ReadSheet parameterReadSheet : parameterReadSheetList) {
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
                        || (parameterReadSheet.getAutoTrim() == null && globalConfiguration.getAutoTrim());
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
