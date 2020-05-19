package com.alibaba.easyexcel.test.core.style;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

/**
 * @author Pengliang Zhao
 */
@Data
public class AnnotationStyleData {
    @ContentStyle(dataFormat = (short)1, rotation = (short)1, indent = (short)1, leftBorderColor = (short)1,
        rightBorderColor = (short)1, topBorderColor = (short)1, bottomBorderColor = (short)1,
        fillForegroundColor = (short)1, fillBackgroundColor = (short)1)
    @ContentFontStyle(fontHeightInPoints = (short)1, color = (short)1, typeOffset = (short)1, charset = (short)1)
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("字符串1")
    private String string1;
}
