package com.alibaba.easyexcel.test.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.typeconvertor.JsonTypeConvertor;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

public class ReadModel3 extends BaseReadModel {

    @ExcelProperty(index = 2)
    private Integer mm;

    @ExcelProperty(index = 3)
    private BigDecimal money;

    @ExcelProperty(index = 4)
    private Long times;

    @ExcelProperty(index = 5)
    private Double activityCode;

    @ExcelProperty(index = 6,format = "yyyy-MM-dd")
    private Date date;

    @ExcelProperty(index = 7)
    private String lx;

    @ExcelProperty(index = 8, convertor = JsonTypeConvertor.class)
    private JSONObject data;

    @ExcelProperty(index = 18)
    private String kk;


    public Integer getMm() {
        return mm;
    }

    public void setMm(Integer mm) {
        this.mm = mm;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Double getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(Double activityCode) {
        this.activityCode = activityCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getKk() {
        return kk;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JavaModel{" +
            "str='" + str + '\'' +
            ", ff=" + ff +
            ", mm=" + mm +
            ", money=" + money +
            ", times=" + times +
            ", activityCode=" + activityCode +
            ", date=" + date +
            ", lx='" + lx + '\'' +
            ", name='" + data.toJSONString() + '\'' +
            ", kk='" + kk + '\'' +
            '}';
    }
}
