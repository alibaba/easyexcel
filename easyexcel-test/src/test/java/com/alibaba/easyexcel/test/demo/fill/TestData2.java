package com.alibaba.easyexcel.test.demo.fill;

import java.util.Date;

import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class TestData2 {
    @ContentFontStyle (color = Font.COLOR_RED)
    private String name;
    @ContentFontStyle(color =  Font.COLOR_RED)
    private double number;
    private Date date;
}
