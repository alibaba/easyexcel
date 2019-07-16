package com.alibaba.excel.converters.date;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

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
    public Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty) {
        if (contentProperty.getDateTimeFormatProperty() == null) {
            return HSSFDateUtil.getJavaDate(cellData.getDoubleValue(), false, null);
        } else {
            return HSSFDateUtil.getJavaDate(cellData.getDoubleValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null);
        }
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty) {
        return new CellData((double)(value.getTime()));
    }
}
