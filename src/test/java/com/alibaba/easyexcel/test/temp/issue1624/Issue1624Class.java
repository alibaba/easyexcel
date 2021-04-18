package com.alibaba.easyexcel.test.temp.issue1624;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author Shilong Li (Lori)
 * @project easyexcel
 * @filename Issue1624Class
 * @date 2021/3/11 01:11
 */
public class Issue1624Class {
    @ExcelProperty(index = 0)
    protected String t1;
    @ExcelProperty(index = 1)
    protected String t2;
    @ExcelProperty(index = 2)
    protected String t3;
    @ExcelProperty(index = 3)
    protected String t4;
    @ExcelProperty(index = 4)
    protected String t5;
    @ExcelProperty(index = 5)
    protected String t6;


    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }

    public String getT5() {
        return t5;
    }

    public void setT5(String t5) {
        this.t5 = t5;
    }

    public String getT6() {
        return t6;
    }

    public void setT6(String t6) {
        this.t6 = t6;
    }

    @Override
    public String toString() {
        return "Issue1624Class{" +
            "t1='" + t1 + '\'' +
            ", t2='" + t2 + '\'' +
            ", t3='" + t3 + '\'' +
            ", t4='" + t4 + '\'' +
            ", t5='" + t5 + '\'' +
            '}';
    }
}
