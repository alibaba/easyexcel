package com.alibaba.excel.converters;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;

public class BigDecimalConverter implements Converter {
    @Override
    public String getName() {
        return "big-decimal-converter";
    }

    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        return BigDecimal.class.equals(columnProperty.getField().getType());
    }
    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        return new BigDecimal(value);
    }
    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        cell.setCellValue(Double.parseDouble(value.toString()));
        return cell;
    }
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof BigDecimal;
    }
    
}
