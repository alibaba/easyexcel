package com.alibaba.easyexcel.test.core.repetition;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class RepetitionData {
    @ExcelProperty("字符串")
    private String string;
}
