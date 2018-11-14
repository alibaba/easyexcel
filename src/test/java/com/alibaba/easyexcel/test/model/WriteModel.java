package com.alibaba.easyexcel.test.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

public class WriteModel extends BaseWriteModel {



    @ExcelProperty(value = {"表头3","表头3","表头3"},index = 2)
    private int p3;

    @ExcelProperty(value = {"表头1","表头4","表头4"},index = 3)
    private long p4;

    @ExcelProperty(value = {"表头5","表头51","表头52"},index = 4)
    private String p5;

    @ExcelProperty(value = {"表头6","表头61","表头611"},index = 5)
    private float p6;

    @ExcelProperty(value = {"表头6","表头61","表头612"},index = 6)
    private BigDecimal p7;

    @ExcelProperty(value = {"表头6","表头62","表头621"},index = 7)
    private Date p8;

    @ExcelProperty(value = {"表头6","表头62","表头622"},index = 8)
    private String p9;

    @ExcelProperty(value = {"表头6","表头62","表头622"},index = 9)
    private double p10;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public int getP3() {
        return p3;
    }

    public void setP3(int p3) {
        this.p3 = p3;
    }

    public long getP4() {
        return p4;
    }

    public void setP4(long p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public float getP6() {
        return p6;
    }

    public void setP6(float p6) {
        this.p6 = p6;
    }

    public BigDecimal getP7() {
        return p7;
    }

    public void setP7(BigDecimal p7) {
        this.p7 = p7;
    }

    public Date getP8() {
        return p8;
    }

    public void setP8(Date p8) {
        this.p8 = p8;
    }

    public String getP9() {
        return p9;
    }

    public void setP9(String p9) {
        this.p9 = p9;
    }

    public double getP10() {
        return p10;
    }

    public void setP10(double p10) {
        this.p10 = p10;
    }

    @Override
    public String toString() {
        return "JavaModel1{" +
            "p1='" + p1 + '\'' +
            ", p2='" + p2 + '\'' +
            ", p3=" + p3 +
            ", p4=" + p4 +
            ", p5='" + p5 + '\'' +
            ", p6=" + p6 +
            ", p7=" + p7 +
            ", p8=" + p8 +
            ", p9='" + p9 + '\'' +
            ", p10=" + p10 +
            '}';
    }
}
