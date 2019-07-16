package com.alibaba.excel.converters;

import com.alibaba.excel.enums.CellDataTypeEnum;

/**
 * Converter unique key
 * 
 * @author zhuangjiaju
 */
public class ConverterKey {

    private Class javaTypeKey;
    private CellDataTypeEnum excelTypeKey;

    public ConverterKey(Class javaTypeKey, CellDataTypeEnum excelTypeKey) {
        if (javaTypeKey == null || excelTypeKey == null) {
            throw new IllegalArgumentException("All parameters can not be null");
        }
        this.javaTypeKey = javaTypeKey;
        this.excelTypeKey = excelTypeKey;
    }

    public static ConverterKey buildConverterKey(Class javaTypeKey, CellDataTypeEnum excelTypeKey) {
        return new ConverterKey(javaTypeKey, excelTypeKey);
    }

    public static ConverterKey buildConverterKey(Converter converter) {
        return buildConverterKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConverterKey that = (ConverterKey)o;
        if (javaTypeKey != null ? !javaTypeKey.equals(that.javaTypeKey) : that.javaTypeKey != null) {
            return false;
        }
        return excelTypeKey == that.excelTypeKey;
    }

    @Override
    public int hashCode() {
        int result = javaTypeKey != null ? javaTypeKey.hashCode() : 0;
        result = 31 * result + (excelTypeKey != null ? excelTypeKey.hashCode() : 0);
        return result;
    }
}
