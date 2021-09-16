package com.alibaba.easyexcel.test.demo.Test1702;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class Date1702 {
    @ExcelProperty(index = 0)
    private String str;
    @ExcelProperty(index = 1)
    private Date date;
    @ExcelProperty(index = 2)
    private double r;
    public void setStr(String str) {
        this.str = str;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setR(double r) {
        this.r = r;
    }

    public String getStr() {
        return str;
    }

    public Date getDate(){
        return date;
    }
    public double getR(){
        return r;
    }
}

