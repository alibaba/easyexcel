package com.alibaba.easyexcel.test.temp.issue2407;

import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Font;

//CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2407
@Getter
@Setter
@EqualsAndHashCode
public class FillData {
    @ContentFontStyle(underline = Font.U_SINGLE)
    private String name;
    @ContentFontStyle(underline = Font.U_SINGLE)
    private double number;
}
