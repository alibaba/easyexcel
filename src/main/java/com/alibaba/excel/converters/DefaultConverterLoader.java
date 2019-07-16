package com.alibaba.excel.converters;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.string.StringStringConverter;

/**
 * Load default handler
 * 
 * @author zhuangjiaju
 */
public class DefaultConverterLoader {
    /**
     * Load default write converter
     *
     * @return
     */
    public static Map<Class, Converter> loadDefaultWriteConverter() {
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>();
        putConverter(converterMap, new DateStringConverter());
        putConverter(converterMap, new BigDecimalNumberConverter());
        putConverter(converterMap, new StringStringConverter());
        return converterMap;
    }

    private static void putConverter(Map<Class, Converter> converterMap, Converter converter) {
        converterMap.put(converter.supportJavaTypeKey(), converter);
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
