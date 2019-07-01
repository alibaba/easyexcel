package com.alibaba.excel.converters;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.TypeUtil;

public class StringConverter implements Converter {
    @Override
    public String getName() {
        return "string-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        return String.class.equals(columnProperty.getField().getType());
    }
    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        return TypeUtil.formatFloat(value);
    }
    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        cell.setCellValue(String.valueOf(value));
        return cell;
    }
    
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof String;
    }
}
