package com.alibaba.easyexcel.test.demo.read;

import java.util.Date;

import lombok.Data;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author Jiaju Zhuang
 **/
@Data
public class ExceptionDemoData {
    /**
     * 用日期去接字符串 肯定报错
     */
    private Date date;
}
