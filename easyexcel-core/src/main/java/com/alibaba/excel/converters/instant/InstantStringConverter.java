package com.alibaba.excel.converters.instant;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeStringConverter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Instant and string converter
 * delegate LocalDateTimeStringConverter
 *
 * @author gongxuanzhang
 */
public class InstantStringConverter implements Converter<Instant> {

    private final LocalDateTimeStringConverter delegate;

    public InstantStringConverter(LocalDateTimeStringConverter localDateTimeStringConverter) {
        delegate = localDateTimeStringConverter;
    }

    @Override
    public Class<?> supportJavaTypeKey() {
        return Instant.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Instant convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws ParseException {
        LocalDateTime localDateTime = delegate.convertToJavaData(cellData, contentProperty, globalConfiguration);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Instant value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        LocalDateTime delegateTime = LocalDateTime.ofInstant(value, ZoneId.systemDefault());
        return delegate.convertToExcelData(delegateTime, contentProperty, globalConfiguration);
    }
}
