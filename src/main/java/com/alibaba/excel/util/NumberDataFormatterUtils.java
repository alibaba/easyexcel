package com.alibaba.excel.util;

import java.math.BigDecimal;
import java.util.Locale;

import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.format.DataFormatter;

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
     * @param data Data read from a file
     * @param dataFormat The index of the date format (Not null).
     * @param dataFormatString The date format of the data
     * @param globalConfiguration The configuration
     * @return A formatted String
     */
    public static String format(BigDecimal data, Short dataFormat, String dataFormatString,
        GlobalConfiguration globalConfiguration) {
        if (globalConfiguration == null) {
            return format(data, dataFormat, dataFormatString, null, null, null);
        }
        //CS304 Issue link: https://github.com/alibaba/easyexcel/issues/2322
        if (dataFormatString.equals("yyyy-m-d h:mm")) {
            dataFormatString = "yyyy-mm-dd hh:mm";
        }
        else if (dataFormatString.equals("yyyy-m-d h:mm:ss")) {
            dataFormatString = "yyyy-mm-dd hh:mm:ss";
        }
        else if (dataFormatString.equals("yyyy-m-d")) {
            dataFormatString = "yyyy-mm-dd";
        }
        else if (dataFormatString.equals("yyyy-mm-d h:mm")) {
            dataFormatString = "yyyy-mm-dd hh:mm";
        }
        else if (dataFormatString.equals("yyyy-mm-d h:mm:ss")) {
            dataFormatString = "yyyy-mm-dd hh:mm:ss";
        }
        else if (dataFormatString.equals("yyyy-mm-d")) {
            dataFormatString = "yyyy-mm-dd";
        }
        else if (dataFormatString.equals("yyyy-m-dd h:mm")) {
            dataFormatString = "yyyy-mm-dd hh:mm";
        }
        else if (dataFormatString.equals("yyyy-m-dd h:mm:ss")) {
            dataFormatString = "yyyy-mm-dd hh:mm:ss";
        }
        else if (dataFormatString.equals("yyyy-m-dd")) {
            dataFormatString = "yyyy-mm-dd";
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
    public static String format(BigDecimal data, Short dataFormat, String dataFormatString, Boolean use1904windowing,
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
