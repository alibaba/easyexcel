package com.alibaba.excel.util;

import java.util.Locale;

import java.math.BigDecimal;

import com.alibaba.excel.metadata.format.DataFormatter;
import com.alibaba.excel.metadata.GlobalConfiguration;

/**
 * Convert number data, including date.
 *
 * @author Jiaju Zhuang
 **/
public class NumberDataFormatterUtils {

    /**
     * Cache DataFormatter.
     */
    private static final ThreadLocal<DataFormatter> DATA_FORMATTER_THREAD_LOCAL = new ThreadLocal<DataFormatter>();

    /**
     * Format number data.
     *
     * @param data
     * @param dataFormat          Not null.
     * @param dataFormatString
     * @param globalConfiguration
     * @return
     */
    public static String format(BigDecimal data, Short dataFormat, String dataFormatString,
        GlobalConfiguration globalConfiguration) {
        if (globalConfiguration == null) {
            return format(data, dataFormat, dataFormatString, null, null, null);
        }
        return format(data, dataFormat, dataFormatString, globalConfiguration.getUse1904windowing(),
            globalConfiguration.getLocale(), globalConfiguration.getUseScientificFormat());
    }

    /**
     * Format number data.
     *
     * @param data
     * @param dataFormat          Not null.
     * @param dataFormatString
     * @param use1904windowing
     * @param locale
     * @param useScientificFormat
     * @return
     */
    public static String format(Double data, Short dataFormat, String dataFormatString, Boolean use1904windowing,
        Locale locale, Boolean useScientificFormat) {
        DataFormatter dataFormatter = DATA_FORMATTER_THREAD_LOCAL.get();
        if (dataFormatter == null) {
            dataFormatter = new DataFormatter(use1904windowing, locale, useScientificFormat);
            DATA_FORMATTER_THREAD_LOCAL.set(dataFormatter);
        }
        return dataFormatter.format(data, dataFormat, dataFormatString);
    }

    public static void removeThreadLocalCache() {
        DATA_FORMATTER_THREAD_LOCAL.remove();
    }
}
