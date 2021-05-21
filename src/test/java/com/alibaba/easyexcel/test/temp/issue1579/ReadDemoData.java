package com.alibaba.easyexcel.test.temp.issue1579;

import lombok.Data;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class ReadDemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
