package com.alibaba.excel.converters.date;

import java.text.ParseException;
import java.util.Date;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.DateUtils;

/**
 * Date and string converter
 *
 * @author zhuangjiaju
 */
public class DateStringConverter implements Converter<Date> {
    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) throws ParseException {
        return DateUtils.parseDate(cellData.getStringValue(), columnProperty.getFormat());
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelColumnProperty columnProperty) {
        return new CellData(DateUtils.format(value, columnProperty.getFormat()));
    }
}
