package com.alibaba.excel.converters.integer;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Integer and number converter
 *
 * @author Jiaju Zhuang
 */
public class IntegerNumberConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Integer convertToJavaData(CellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().intValue();
    }

    @Override
    public CellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
        WriteHolder currentWriteHolder) {
        return NumberUtils.formatToCellData(value, contentProperty, currentWriteHolder);
    }

}
