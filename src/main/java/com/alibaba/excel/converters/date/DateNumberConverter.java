package com.alibaba.excel.converters.date;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

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
            return DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                globalConfiguration.getUse1904windowing(), null);
        } else {
            return DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null);
        }
    }

    @Override
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(
                BigDecimal.valueOf(DateUtil.getExcelDate(value, globalConfiguration.getUse1904windowing())));
        } else {
            return new CellData(BigDecimal.valueOf(
                DateUtil.getExcelDate(value, contentProperty.getDateTimeFormatProperty().getUse1904windowing())));
        }
    }
}
