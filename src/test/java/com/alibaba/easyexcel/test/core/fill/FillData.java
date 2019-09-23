package com.alibaba.easyexcel.test.core.fill;

import com.alibaba.excel.annotation.format.NumberFormat;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class FillData {
    private String name;
    @NumberFormat("0#")
    private double number;
}
