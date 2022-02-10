package com.alibaba.easyexcel.test.temp.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.BooleanEnum;

import lombok.Data;

@Data
public class TempWriteData {

    @ExcelProperty("     换行\r\n \\ \r\n的名字")
    @HeadStyle(wrapped = BooleanEnum.TRUE)
    @ContentStyle(wrapped = BooleanEnum.TRUE)
    private String name;
}
