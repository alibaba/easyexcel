package com.alibaba.easyexcel.test.temp.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.BooleanEnum;

import lombok.Data;

@Data
//@Accessors(chain = true)
public class TempWriteData {
    private String name1;

    @ExcelProperty("     换行\r\n \\ \r\n的名字")
    @HeadStyle(wrapped = BooleanEnum.TRUE)
    @ContentStyle(wrapped = BooleanEnum.TRUE)
    private String name;
}
