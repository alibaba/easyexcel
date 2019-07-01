package com.alibaba.excel.converters;

import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.ExcelColumnProperty;

public interface Converter {
    String getName();
    boolean support(ExcelColumnProperty columnProperty);
    boolean support(Object cellValue);
    Object convert(String value, ExcelColumnProperty columnProperty);
    Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty);
}
