package com.alibaba.excel.converters;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;

public class BooleanConverter implements Converter {
    @Override
    public String getName() {
        return "boolean-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        Field field = columnProperty.getField();
        return Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType());
    }
    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        String valueLower = value.toLowerCase();
        if (valueLower.equals("true") || valueLower.equals("false")) {
            return Boolean.parseBoolean(value.toLowerCase());
        }
        Integer integer = Integer.parseInt(value);
        if (integer == 0) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        cell.setCellValue(String.valueOf(value));
        return cell;
    }
    
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof Boolean || boolean.class.equals(cellValue.getClass());
    }
}
