package com.alibaba.excel.converters.date;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.ExcelColumnProperty;

/**
 * Date and number converter
 * 
 * @author zhuangjiaju
 */
public class DateNumberConverter implements Converter<Date> {

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Date convertToJavaData(CellData cellData, ExcelColumnProperty columnProperty) {
        return HSSFDateUtil.getJavaDate(cellData.getDoubleValue(), columnProperty.getUse1904windowing(),
            columnProperty.getTimeZone());
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelColumnProperty columnProperty) {
        return new CellData((double)(value.getTime()));
    }
}
