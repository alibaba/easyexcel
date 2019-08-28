package com.alibaba.excel.converters.date;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Date and number converter
 *
 * @author Jiaju Zhuang
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
    public Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return HSSFDateUtil.getJavaDate(cellData.getDoubleValue(), globalConfiguration.getUse1904windowing(), null);
        } else {
            return HSSFDateUtil.getJavaDate(cellData.getDoubleValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null);
        }
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new CellData((double)(value.getTime()));
    }
}
