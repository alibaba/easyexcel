package com.alibaba.easyexcel.test.temp.data;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class HeadType {


    /**
     * 任务id
     */
    @ExcelProperty("任务ID")
    private Integer id;


    @ExcelProperty(value = "备注1")
    private String firstRemark;

    @ExcelProperty(value = "备注2")
    private String secRemark;

}
