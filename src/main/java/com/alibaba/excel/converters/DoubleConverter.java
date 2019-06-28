package com.alibaba.excel.converters;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.TypeUtil;

public class DoubleConverter implements Converter {
    @Override
    public String getName() {
        return "double-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        Field field = columnProperty.getField();
        return Double.class.equals(field.getType()) || double.class.equals(field.getType());
    }
    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        return TypeUtil.formatFloat(value);
    }
    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        cell.setCellValue(Double.parseDouble(value.toString()));
        return cell;
    }
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof Double || double.class.equals(cellValue.getClass());
    }
}
