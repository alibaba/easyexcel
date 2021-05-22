package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
@Data
public class EnumDataWithoutConverter {
    @ExcelProperty(value = "Desc")
    private Integer code;
    @ExcelProperty(value = "Amount")
    private Double doubleAmount;


}
