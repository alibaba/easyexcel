package com.alibaba.excel.support;

/**
 * 支持读写的数据格式
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
