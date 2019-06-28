package com.alibaba.excel.converters;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.util.TypeUtil;

public class DateConverter implements Converter {
    private final AnalysisContext context;

    public DateConverter(AnalysisContext context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "date-converter";
    }
    @Override
    public boolean support(ExcelColumnProperty columnProperty) {
        return Date.class.equals(columnProperty.getField().getType());
    }

    @Override
    public Object convert(String value, ExcelColumnProperty columnProperty) {
        if (value.contains("-") || value.contains("/") || value.contains(":")) {
            return TypeUtil.getSimpleDateFormatDate(value, columnProperty.getFormat());
        } else {
            Double d = Double.parseDouble(value);
            return HSSFDateUtil.getJavaDate(d, context != null ? context.use1904WindowDate() : false);
        }
    }

    @Override
    public Cell convert(Cell cell, Object value, ExcelColumnProperty columnProperty) {
        Date d = (Date)value;
        if (columnProperty != null && StringUtils.isEmpty(columnProperty.getFormat()) == false) {
            cell.setCellValue(TypeUtil.formatDate(d, columnProperty.getFormat()));
        } else {
            cell.setCellValue(d);
        }
        
        return cell;
    }
    
    @Override
    public boolean support(Object cellValue) {
        return cellValue instanceof Date;
    }
}
