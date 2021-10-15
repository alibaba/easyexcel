package com.alibaba.easyexcel.test.core.fill.style;

import java.util.Date;

import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class FillStyleAnnotatedData {
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 13)
    @ContentFontStyle(bold = BooleanEnum.TRUE, color = 19)
    private String name;
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
    @ContentFontStyle(bold = BooleanEnum.TRUE, color = 16)
    private Double number;
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
    @ContentFontStyle(bold = BooleanEnum.TRUE, color = 18)
    private Date date;
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 12)
    @ContentFontStyle(bold = BooleanEnum.TRUE, color = 18)
    private String empty;
}
