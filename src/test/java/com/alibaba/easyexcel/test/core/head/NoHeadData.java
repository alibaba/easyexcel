package com.alibaba.easyexcel.test.core.head;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class NoHeadData {
    @ExcelProperty("字符串")
    private String string;
}
