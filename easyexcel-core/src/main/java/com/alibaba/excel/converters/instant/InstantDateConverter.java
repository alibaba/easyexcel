package com.alibaba.excel.converters.instant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Instant and date converter
 * delegate LocalDateTimeDateConverter
 *
 * @author gongxuanzhang
 */
public class InstantDateConverter implements Converter<Instant> {

    private final LocalDateTimeDateConverter delegate;

    public InstantDateConverter(LocalDateTimeDateConverter delegate) {
        this.delegate = delegate;
    }

    @Override
    public Class<?> supportJavaTypeKey() {
        return Instant.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Instant value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        LocalDateTime delegateTime = LocalDateTime.ofInstant(value, ZoneId.systemDefault());
        return delegate.convertToExcelData(delegateTime, contentProperty, globalConfiguration);
    }
}
