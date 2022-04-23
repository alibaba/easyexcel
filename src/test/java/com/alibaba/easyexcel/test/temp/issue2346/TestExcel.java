package com.alibaba.easyexcel.test.temp.issue2346;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestExcel {

//    @ExcelProperty("A")
//    private String a;
//
//    @ExcelProperty("B")
//    private String b;
    @ExcelProperty("序号")
    private Integer id;

    @ExcelProperty("记账日期")
    private String BookkeepingDate;

    @ExcelProperty("发生时间")
    private String time;

    @ExcelProperty("摘要")
    private String abstr;

    @ExcelProperty("借方发生额")
    private String debitIncurred;

    @ExcelProperty("贷方发生额")
    private String creditsIncurred;


}
