package com.alibaba.excel.converters;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;

/**
 * Build default handler
 * 
 * @author zhuangjiaju
 */
public class DefaultConverterBuilder {
    /**
     * Load default wirte converter
     *
     * @return
     */
    public static Map<Class, Converter> loadDefaultWriteConverter() {
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>();
        DateStringConverter dateStringConverter = new DateStringConverter();
        converterMap.put(dateStringConverter.supportJavaTypeKey(), dateStringConverter);
        BigDecimalNumberConverter bigDecimalNumberConverter = new BigDecimalNumberConverter();
        converterMap.put(bigDecimalNumberConverter.supportJavaTypeKey(), bigDecimalNumberConverter);
        return converterMap;
    }

    /**
     * Load default read converter
     *
     * @return
     */
    public static Map<ConverterKey, Converter> loadDefaultReadConverter() {
        Map<ConverterKey, Converter> converterMap = new HashMap<ConverterKey, Converter>();
        return converterMap;
    }
}
