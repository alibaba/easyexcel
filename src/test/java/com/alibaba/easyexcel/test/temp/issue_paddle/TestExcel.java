package com.alibaba.easyexcel.test.temp.issue_paddle;

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
    @ExcelProperty("No.")
    private Integer id;

    @ExcelProperty("Time")
    private String time;

    @ExcelProperty("Status")
    private String status;

    @ExcelProperty("Note")
    private String note;

    @ExcelProperty("Weight")
    private String Weight;

    @ExcelProperty("Price")
    private String Price;


}
