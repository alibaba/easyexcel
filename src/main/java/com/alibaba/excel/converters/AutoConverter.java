package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * An empty converter.It's automatically converted by type.
 *
 * @author Jiaju Zhuang
 */
public class AutoConverter implements Converter {

    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Object convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return null;
    }

    @Override
    public CellData convertToExcelData(Object value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return null;
    }
}
