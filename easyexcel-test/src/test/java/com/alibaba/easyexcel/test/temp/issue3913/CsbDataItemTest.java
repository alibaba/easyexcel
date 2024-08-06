package com.alibaba.easyexcel.test.temp.issue3913;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class CsbDataItemTest {
    @ExcelProperty("业务对象")
    private String field1 = "云订单";

    @ExcelProperty("数据标准IT编码")
    private String A;

    @ExcelProperty("长度")
    private Integer field3;

    @ExcelProperty("精度")
    private Integer D;


}
