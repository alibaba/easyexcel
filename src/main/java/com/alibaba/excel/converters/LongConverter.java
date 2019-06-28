package com.alibaba.excel.converters;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;

public class LongConverter implements Converter {
    @Override
    public String getName() {
        return "long-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        Field field = columnProperty.getField();
        return Long.class.equals(field.getType()) || long.class.equals(field.getType());
    }
    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        return Long.parseLong(value);
    }
    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        cell.setCellValue(Double.parseDouble(value.toString()));
        return cell;
    }
    
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof Long || long.class.equals(cellValue.getClass());
    }
}
