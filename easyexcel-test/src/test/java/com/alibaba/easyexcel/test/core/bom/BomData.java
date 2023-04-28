package com.alibaba.easyexcel.test.core.bom;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BomData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年纪")
    private Long age;
}
