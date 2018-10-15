package com.alibaba.excel.support;

/**
 *
 * @author jipengfei
 */
public enum ExcelTypeEnum {
    XLS(".xls"),
    XLSX(".xlsx");
    //    CSV(".csv");
    private String value;

    private ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
