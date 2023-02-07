package com.alibaba.excel.converters.string;

import java.math.BigDecimal;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.NumberDataFormatterUtils;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.excel.util.StringUtils;

import org.apache.poi.ss.usermodel.DateUtil;

/**
 * String and number converter
 *
 * @author Jiaju Zhuang
 */
public class StringNumberConverter implements Converter<String> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        // If there are "DateTimeFormat", read as date
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            return DateUtils.format(cellData.getNumberValue(),
                contentProperty.getDateTimeFormatProperty().getUse1904windowing(),
                contentProperty.getDateTimeFormatProperty().getFormat());
        }
        // If there are "NumberFormat", read as number
        if (contentProperty != null && contentProperty.getNumberFormatProperty() != null) {
            return NumberUtils.format(cellData.getNumberValue(), contentProperty);
        }
        // Excel defines formatting
        boolean hasDataFormatData = cellData.getDataFormatData() != null
            && cellData.getDataFormatData().getIndex() != null && !StringUtils.isEmpty(
            cellData.getDataFormatData().getFormat());
        if (hasDataFormatData) {
            return NumberDataFormatterUtils.format(cellData.getNumberValue(),
                cellData.getDataFormatData().getIndex(), cellData.getDataFormatData().getFormat(), globalConfiguration);
        }
        // Default conversion number
        return NumberUtils.format(cellData.getNumberValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(new BigDecimal(value));
    }
}
