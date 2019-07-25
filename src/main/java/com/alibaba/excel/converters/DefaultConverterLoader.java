package com.alibaba.excel.converters;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.converters.bigdecimal.BigDecimalBooleanConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanBooleanConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanNumberConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanStringConverter;
import com.alibaba.excel.converters.byteconverter.ByteBooleanConverter;
import com.alibaba.excel.converters.byteconverter.ByteNumberConverter;
import com.alibaba.excel.converters.byteconverter.ByteStringConverter;
import com.alibaba.excel.converters.date.DateNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleNumberConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleStringConverter;
import com.alibaba.excel.converters.floatconverter.FloatNumberConverter;
import com.alibaba.excel.converters.floatconverter.FloatStringConverter;
import com.alibaba.excel.converters.integer.IntegerNumberConverter;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongNumberConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.converters.shortconverter.ShortNumberConverter;
import com.alibaba.excel.converters.shortconverter.ShortStringConverter;
import com.alibaba.excel.converters.string.StringErrorConverter;
import com.alibaba.excel.converters.string.StringNumberConverter;
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
    public static Map<String, Converter> loadDefaultWriteConverter() {
        Map<String, Converter> converterMap = new HashMap<String, Converter>(16);
        putWriteConverter(converterMap, new BigDecimalNumberConverter());
        putWriteConverter(converterMap, new BooleanBooleanConverter());
        putWriteConverter(converterMap, new ByteNumberConverter());
        putWriteConverter(converterMap, new DateStringConverter());
        putWriteConverter(converterMap, new DoubleNumberConverter());
        putWriteConverter(converterMap, new FloatNumberConverter());
        putWriteConverter(converterMap, new IntegerNumberConverter());
        putWriteConverter(converterMap, new LongNumberConverter());
        putWriteConverter(converterMap, new ShortNumberConverter());
        putWriteConverter(converterMap, new StringStringConverter());
        return converterMap;
    }

    private static void putWriteConverter(Map<String, Converter> converterMap, Converter converter) {
        converterMap.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
    }

    /**
     * Load default read converter
     *
     * @return
     */
    public static Map<String, Converter> loadDefaultReadConverter() {
        Map<String, Converter> converterMap = new HashMap<String, Converter>(64);
        putReadConverter(converterMap, new BigDecimalBooleanConverter());
        putReadConverter(converterMap, new BigDecimalNumberConverter());
        putReadConverter(converterMap, new BigDecimalStringConverter());

        putReadConverter(converterMap, new BooleanBooleanConverter());
        putReadConverter(converterMap, new BooleanNumberConverter());
        putReadConverter(converterMap, new BooleanStringConverter());

        putReadConverter(converterMap, new ByteBooleanConverter());
        putReadConverter(converterMap, new ByteNumberConverter());
        putReadConverter(converterMap, new ByteStringConverter());

        putReadConverter(converterMap, new DateNumberConverter());
        putReadConverter(converterMap, new DateStringConverter());

        putReadConverter(converterMap, new DoubleNumberConverter());
        putReadConverter(converterMap, new DoubleNumberConverter());
        putReadConverter(converterMap, new DoubleStringConverter());

        putReadConverter(converterMap, new FloatNumberConverter());
        putReadConverter(converterMap, new FloatNumberConverter());
        putReadConverter(converterMap, new FloatStringConverter());

        putReadConverter(converterMap, new IntegerNumberConverter());
        putReadConverter(converterMap, new IntegerNumberConverter());
        putReadConverter(converterMap, new IntegerStringConverter());

        putReadConverter(converterMap, new LongNumberConverter());
        putReadConverter(converterMap, new LongNumberConverter());
        putReadConverter(converterMap, new LongStringConverter());

        putReadConverter(converterMap, new ShortNumberConverter());
        putReadConverter(converterMap, new ShortNumberConverter());
        putReadConverter(converterMap, new ShortStringConverter());

        putReadConverter(converterMap, new StringNumberConverter());
        putReadConverter(converterMap, new StringNumberConverter());
        putReadConverter(converterMap, new StringStringConverter());
        putReadConverter(converterMap, new StringErrorConverter());
        return converterMap;
    }

    private static void putReadConverter(Map<String, Converter> converterMap, Converter converter) {
        converterMap.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey()),
            converter);
    }
}
