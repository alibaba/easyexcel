package com.alibaba.easyexcel.test.temp;

import com.alibaba.excel.annotation.format.NumberFormat;

import lombok.Data;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class LockData {
    @NumberFormat("#.##%")
    private Double string0;
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private String string5;
    private String string6;
    private String string7;
    private String string8;

}
