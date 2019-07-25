package com.alibaba.easyexcel.test.core.order;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author zhuangjiaju
 */
public class OrderData {
    @ExcelProperty(value = "第一个", index = 1)
    private String index1;
    @ExcelProperty(value = "第10个", index = 10)
    private String index10;

    public String getIndex1() {
        return index1;
    }

    public void setIndex1(String index1) {
        this.index1 = index1;
    }

    public String getIndex10() {
        return index10;
    }

    public void setIndex10(String index10) {
        this.index10 = index10;
    }
}
