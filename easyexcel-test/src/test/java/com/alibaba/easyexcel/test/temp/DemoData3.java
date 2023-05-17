package com.alibaba.easyexcel.test.temp;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DemoData3 {
    @ExcelProperty("日期时间标题")
    private LocalDateTime localDateTime;
}
