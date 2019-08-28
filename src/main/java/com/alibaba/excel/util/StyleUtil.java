package com.alibaba.excel.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;

/**
 * @author jipengfei
 */
public class StyleUtil {

    private StyleUtil() {}

    /**
     * @param workbook
     * @return
     */
    public static CellStyle buildDefaultCellStyle(Workbook workbook) {
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        newCellStyle.setBorderTop(BorderStyle.THIN);
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        newCellStyle.setBorderRight(BorderStyle.THIN);
        return newCellStyle;
    }

    /**
     * Build head cell style
     *
     * @param workbook
     * @param writeCellStyle
     * @return
     */
    public static CellStyle buildHeadCellStyle(Workbook workbook, WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = buildDefaultCellStyle(workbook);
        if (writeCellStyle == null) {
            return cellStyle;
        }
        buildCellStyle(workbook, cellStyle, writeCellStyle, true);
        return cellStyle;
    }

    /**
     * Build content cell style
     *
     * @param workbook
     * @param writeCellStyle
     * @return
     */
    public static CellStyle buildContentCellStyle(Workbook workbook, WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (writeCellStyle == null) {
            return cellStyle;
        }
        buildCellStyle(workbook, cellStyle, writeCellStyle, false);
        return cellStyle;
    }

    private static void buildCellStyle(Workbook workbook, CellStyle cellStyle, WriteCellStyle writeCellStyle,
        boolean isHead) {
        buildFont(workbook, cellStyle, writeCellStyle.getWriteFont(), isHead);
        if (writeCellStyle.getDataFormat() != null) {
            cellStyle.setDataFormat(writeCellStyle.getDataFormat());
        }
        if (writeCellStyle.getHidden() != null) {
            cellStyle.setHidden(writeCellStyle.getHidden());
        }
        if (writeCellStyle.getLocked() != null) {
            cellStyle.setLocked(writeCellStyle.getLocked());
        }
        if (writeCellStyle.getQuotePrefix() != null) {
            cellStyle.setQuotePrefixed(writeCellStyle.getQuotePrefix());
        }
        if (writeCellStyle.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(writeCellStyle.getHorizontalAlignment());
        }
        if (writeCellStyle.getWrapped() != null) {
            cellStyle.setWrapText(writeCellStyle.getWrapped());
        }
        if (writeCellStyle.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(writeCellStyle.getVerticalAlignment());
        }
        if (writeCellStyle.getRotation() != null) {
            cellStyle.setRotation(writeCellStyle.getRotation());
        }
        if (writeCellStyle.getIndent() != null) {
            cellStyle.setIndention(writeCellStyle.getIndent());
        }
        if (writeCellStyle.getBorderLeft() != null) {
            cellStyle.setBorderLeft(writeCellStyle.getBorderLeft());
        }
        if (writeCellStyle.getBorderRight() != null) {
            cellStyle.setBorderRight(writeCellStyle.getBorderRight());
        }
        if (writeCellStyle.getBorderTop() != null) {
            cellStyle.setBorderTop(writeCellStyle.getBorderTop());
        }
        if (writeCellStyle.getBorderBottom() != null) {
            cellStyle.setBorderBottom(writeCellStyle.getBorderBottom());
        }
        if (writeCellStyle.getLeftBorderColor() != null) {
            cellStyle.setLeftBorderColor(writeCellStyle.getLeftBorderColor());
        }
        if (writeCellStyle.getRightBorderColor() != null) {
            cellStyle.setRightBorderColor(writeCellStyle.getRightBorderColor());
        }
        if (writeCellStyle.getTopBorderColor() != null) {
            cellStyle.setTopBorderColor(writeCellStyle.getTopBorderColor());
        }
        if (writeCellStyle.getBottomBorderColor() != null) {
            cellStyle.setBottomBorderColor(writeCellStyle.getBottomBorderColor());
        }
        if (writeCellStyle.getFillPatternType() != null) {
            cellStyle.setFillPattern(writeCellStyle.getFillPatternType());
        }
        if (writeCellStyle.getFillBackgroundColor() != null) {
            cellStyle.setFillBackgroundColor(writeCellStyle.getFillBackgroundColor());
        }
        if (writeCellStyle.getFillForegroundColor() != null) {
            cellStyle.setFillForegroundColor(writeCellStyle.getFillForegroundColor());
        }
        if (writeCellStyle.getShrinkToFit() != null) {
            cellStyle.setShrinkToFit(writeCellStyle.getShrinkToFit());
        }
    }

    private static void buildFont(Workbook workbook, CellStyle cellStyle, WriteFont writeFont, boolean isHead) {
        Font font = null;
        if (isHead) {
            font = workbook.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short)14);
            font.setBold(true);
            cellStyle.setFont(font);
        }
        if (writeFont == null) {
            return;
        }
        if (!isHead) {
            font = workbook.createFont();
            cellStyle.setFont(font);
        }
        if (writeFont.getFontName() != null) {
            font.setFontName(writeFont.getFontName());
        }
        if (writeFont.getFontHeightInPoints() != null) {
            font.setFontHeightInPoints(writeFont.getFontHeightInPoints());
        }
        if (writeFont.getItalic() != null) {
            font.setItalic(writeFont.getItalic());
        }
        if (writeFont.getStrikeout() != null) {
            font.setStrikeout(writeFont.getStrikeout());
        }
        if (writeFont.getColor() != null) {
            font.setColor(writeFont.getColor());
        }
        if (writeFont.getTypeOffset() != null) {
            font.setTypeOffset(writeFont.getTypeOffset());
        }
        if (writeFont.getUnderline() != null) {
            font.setUnderline(writeFont.getUnderline());
        }
        if (writeFont.getCharset() != null) {
            font.setCharSet(writeFont.getCharset());
        }
        if (writeFont.getBold() != null) {
            font.setBold(writeFont.getBold());
        }
    }

}
