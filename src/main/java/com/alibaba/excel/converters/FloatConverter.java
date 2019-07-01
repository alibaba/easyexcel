package com.alibaba.excel.converters;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.TypeUtil;

public class FloatConverter implements Converter {
    @Override
    public String getName() {
        return "float-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        return Float.class.equals(columnProperty.getField().getType());
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
        return cellValue instanceof Float || float.class.equals(cellValue.getClass());
    }
}
