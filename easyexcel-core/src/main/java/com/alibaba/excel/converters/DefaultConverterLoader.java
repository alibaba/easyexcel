package com.alibaba.excel.converters;

import java.util.Map;

import com.alibaba.excel.converters.ConverterKeyBuild.ConverterKey;
import com.alibaba.excel.converters.bigdecimal.BigDecimalBooleanConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.bigdecimal.BigDecimalStringConverter;
import com.alibaba.excel.converters.biginteger.BigIntegerBooleanConverter;
import com.alibaba.excel.converters.biginteger.BigIntegerNumberConverter;
import com.alibaba.excel.converters.biginteger.BigIntegerStringConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanBooleanConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanNumberConverter;
import com.alibaba.excel.converters.booleanconverter.BooleanStringConverter;
import com.alibaba.excel.converters.bytearray.BoxingByteArrayImageConverter;
import com.alibaba.excel.converters.bytearray.ByteArrayImageConverter;
import com.alibaba.excel.converters.byteconverter.ByteBooleanConverter;
import com.alibaba.excel.converters.byteconverter.ByteNumberConverter;
import com.alibaba.excel.converters.byteconverter.ByteStringConverter;
import com.alibaba.excel.converters.date.DateDateConverter;
import com.alibaba.excel.converters.date.DateNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleBooleanConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleNumberConverter;
import com.alibaba.excel.converters.doubleconverter.DoubleStringConverter;
import com.alibaba.excel.converters.file.FileImageConverter;
import com.alibaba.excel.converters.floatconverter.FloatBooleanConverter;
import com.alibaba.excel.converters.floatconverter.FloatNumberConverter;
import com.alibaba.excel.converters.floatconverter.FloatStringConverter;
import com.alibaba.excel.converters.inputstream.InputStreamImageConverter;
import com.alibaba.excel.converters.integer.IntegerBooleanConverter;
import com.alibaba.excel.converters.integer.IntegerNumberConverter;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.localdate.LocalDateDateConverter;
import com.alibaba.excel.converters.localdate.LocalDateNumberConverter;
import com.alibaba.excel.converters.localdate.LocalDateStringConverter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeNumberConverter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.alibaba.excel.converters.longconverter.LongBooleanConverter;
import com.alibaba.excel.converters.longconverter.LongNumberConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.converters.shortconverter.ShortBooleanConverter;
import com.alibaba.excel.converters.shortconverter.ShortNumberConverter;
import com.alibaba.excel.converters.shortconverter.ShortStringConverter;
import com.alibaba.excel.converters.string.StringBooleanConverter;
import com.alibaba.excel.converters.string.StringErrorConverter;
import com.alibaba.excel.converters.string.StringNumberConverter;
import com.alibaba.excel.converters.string.StringStringConverter;
import com.alibaba.excel.converters.url.UrlImageConverter;
import com.alibaba.excel.util.MapUtils;

/**
 * Load default handler
 *
 * @author Jiaju Zhuang
 */
public class DefaultConverterLoader {
    private static Map<ConverterKey, Converter<?>> defaultWriteConverter;
    private static Map<ConverterKey, Converter<?>> allConverter;

    static {
        initDefaultWriteConverter();
        initAllConverter();
    }

    private static void initAllConverter() {
        allConverter = MapUtils.newHashMapWithExpectedSize(40);
        putAllConverter(new BigDecimalBooleanConverter());
        putAllConverter(new BigDecimalNumberConverter());
        putAllConverter(new BigDecimalStringConverter());

        putAllConverter(new BigIntegerBooleanConverter());
        putAllConverter(new BigIntegerNumberConverter());
        putAllConverter(new BigIntegerStringConverter());

        putAllConverter(new BooleanBooleanConverter());
        putAllConverter(new BooleanNumberConverter());
        putAllConverter(new BooleanStringConverter());

        putAllConverter(new ByteBooleanConverter());
        putAllConverter(new ByteNumberConverter());
        putAllConverter(new ByteStringConverter());

        putAllConverter(new DateNumberConverter());
        putAllConverter(new DateStringConverter());

        putAllConverter(new LocalDateNumberConverter());
        putAllConverter(new LocalDateStringConverter());

        putAllConverter(new LocalDateTimeNumberConverter());
        putAllConverter(new LocalDateTimeStringConverter());

        putAllConverter(new DoubleBooleanConverter());
        putAllConverter(new DoubleNumberConverter());
        putAllConverter(new DoubleStringConverter());

        putAllConverter(new FloatBooleanConverter());
        putAllConverter(new FloatNumberConverter());
        putAllConverter(new FloatStringConverter());

        putAllConverter(new IntegerBooleanConverter());
        putAllConverter(new IntegerNumberConverter());
        putAllConverter(new IntegerStringConverter());

        putAllConverter(new LongBooleanConverter());
        putAllConverter(new LongNumberConverter());
        putAllConverter(new LongStringConverter());

        putAllConverter(new ShortBooleanConverter());
        putAllConverter(new ShortNumberConverter());
        putAllConverter(new ShortStringConverter());

        putAllConverter(new StringBooleanConverter());
        putAllConverter(new StringNumberConverter());
        putAllConverter(new StringStringConverter());
        putAllConverter(new StringErrorConverter());
    }

    private static void initDefaultWriteConverter() {
        defaultWriteConverter = MapUtils.newHashMapWithExpectedSize(40);
        putWriteConverter(new BigDecimalNumberConverter());
        putWriteConverter(new BigIntegerNumberConverter());
        putWriteConverter(new BooleanBooleanConverter());
        putWriteConverter(new ByteNumberConverter());
        putWriteConverter(new DateDateConverter());
        putWriteConverter(new LocalDateTimeDateConverter());
        putWriteConverter(new LocalDateDateConverter());
        putWriteConverter(new DoubleNumberConverter());
        putWriteConverter(new FloatNumberConverter());
        putWriteConverter(new IntegerNumberConverter());
        putWriteConverter(new LongNumberConverter());
        putWriteConverter(new ShortNumberConverter());
        putWriteConverter(new StringStringConverter());
        putWriteConverter(new FileImageConverter());
        putWriteConverter(new InputStreamImageConverter());
        putWriteConverter(new ByteArrayImageConverter());
        putWriteConverter(new BoxingByteArrayImageConverter());
        putWriteConverter(new UrlImageConverter());

        // In some cases, it must be converted to string
        putWriteStringConverter(new BigDecimalStringConverter());
        putWriteStringConverter(new BigIntegerStringConverter());
        putWriteStringConverter(new BooleanStringConverter());
        putWriteStringConverter(new ByteStringConverter());
        putWriteStringConverter(new DateStringConverter());
        putWriteStringConverter(new LocalDateStringConverter());
        putWriteStringConverter(new LocalDateTimeStringConverter());
        putWriteStringConverter(new DoubleStringConverter());
        putWriteStringConverter(new FloatStringConverter());
        putWriteStringConverter(new IntegerStringConverter());
        putWriteStringConverter(new LongStringConverter());
        putWriteStringConverter(new ShortStringConverter());
        putWriteStringConverter(new StringStringConverter());
    }

    /**
     * Load default write converter
     *
     * @return
     */
    public static Map<ConverterKey, Converter<?>> loadDefaultWriteConverter() {
        return defaultWriteConverter;
    }

    private static void putWriteConverter(Converter<?> converter) {
        defaultWriteConverter.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
    }

    private static void putWriteStringConverter(Converter<?> converter) {
        defaultWriteConverter.put(
            ConverterKeyBuild.buildKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey()), converter);
    }

    /**
     * Load default read converter
     *
     * @return
     */
    public static Map<ConverterKey, Converter<?>> loadDefaultReadConverter() {
        return loadAllConverter();
    }

    /**
     * Load all converter
     *
     * @return
     */
    public static Map<ConverterKey, Converter<?>> loadAllConverter() {
        return allConverter;
    }

    private static void putAllConverter(Converter<?> converter) {
        allConverter.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey()),
            converter);
    }
}
