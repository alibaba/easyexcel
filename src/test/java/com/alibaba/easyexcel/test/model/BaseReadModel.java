package com.alibaba.easyexcel.test.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class BaseReadModel extends BaseRowModel {
    @ExcelProperty(index = 0)
    protected String str;

    @ExcelProperty(index = 1)
    protected Float ff;
    public String getStr() {
        return str;
    }


    public void setStr(String str) {
        this.str = str;
    }

    public Float getFf() {
        return ff;
    }

    public void setFf(Float ff) {
        this.ff = ff;
    }
}
