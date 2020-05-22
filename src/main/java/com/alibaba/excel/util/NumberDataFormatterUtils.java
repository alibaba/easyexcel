package com.alibaba.excel.util;

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
    public static String format(Double data, Integer dataFormat, String dataFormatString,
        GlobalConfiguration globalConfiguration) {
        DataFormatter dataFormatter = DATA_FORMATTER_THREAD_LOCAL.get();
        if (dataFormatter == null) {
            dataFormatter = new DataFormatter(globalConfiguration);
            DATA_FORMATTER_THREAD_LOCAL.set(dataFormatter);
        }
        return dataFormatter.format(data, dataFormat, dataFormatString);

    }

    public static void removeThreadLocalCache() {
        DATA_FORMATTER_THREAD_LOCAL.remove();
    }
}
