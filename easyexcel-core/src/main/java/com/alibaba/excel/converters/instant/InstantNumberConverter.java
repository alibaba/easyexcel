package com.alibaba.excel.converters.instant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeNumberConverter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * instant and number converter
 * decorate LocalDateTimeNumber
 *
 * @author gongxuanzhangmelt@gmail.com
 */
public class InstantNumberConverter implements Converter<Instant> {

    private final LocalDateTimeNumberConverter delegateConverter;

    public InstantNumberConverter(LocalDateTimeNumberConverter delegateConverter) {
        this.delegateConverter = delegateConverter;
    }

    @Override
    public Class<?> supportJavaTypeKey() {
        return Instant.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Instant convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        LocalDateTime localDateTime = delegateConverter.convertToJavaData(cellData, contentProperty,
            globalConfiguration);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Instant value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        LocalDateTime delegateTime = LocalDateTime.ofInstant(value, ZoneId.systemDefault());
        return delegateConverter.convertToExcelData(delegateTime, contentProperty, globalConfiguration);
    }
}
