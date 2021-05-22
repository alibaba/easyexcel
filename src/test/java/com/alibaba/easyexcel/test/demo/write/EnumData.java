package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EnumData {
    @ExcelProperty(value = "Desc", converter = EnumConverter.class)
    private NoticeTypeEnum notice;
    @ExcelProperty(value = "Amount")
    private Double doubleAmount;
}
