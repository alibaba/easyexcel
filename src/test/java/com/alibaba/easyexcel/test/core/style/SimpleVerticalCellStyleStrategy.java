package com.alibaba.easyexcel.test.core.style;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;

/**
 * @author Pengliang Zhao
 */
public class SimpleVerticalCellStyleStrategy extends AbstractVerticalCellStyleStrategy {
    private StyleProperty styleProperty = StyleProperty.build(StyleOtherData.class.getAnnotation(ContentStyle.class));
    private FontProperty fontProperty = FontProperty.build(StyleOtherData.class.getAnnotation(ContentFontStyle.class));

    @Override
    protected WriteCellStyle headCellStyle(Head head) {
        WriteCellStyle writeCellStyle = WriteCellStyle.build(styleProperty, fontProperty);
        if(head.getColumnIndex() == 0) {
            writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            WriteFont writeFont = new WriteFont();
            writeFont.setItalic(true);
            writeFont.setStrikeout(true);
            writeFont.setTypeOffset(Font.SS_NONE);
            writeFont.setUnderline(Font.U_DOUBLE);
            writeFont.setBold(true);
            writeFont.setCharset((int)Font.DEFAULT_CHARSET);
        } else {
            writeCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        }
        return writeCellStyle;
    }

    @Override
    protected WriteCellStyle contentCellStyle(Head head) {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        if (head.getColumnIndex() == 0) {
            writeCellStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        } else {
            writeCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
        }
        return writeCellStyle;
    }
}
