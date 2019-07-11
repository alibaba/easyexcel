package com.alibaba.excel.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author jipengfei
 */
public class StyleUtil {

    /**
     *
     * @param workbook
     * @return
     */
    public static CellStyle buildDefaultCellStyle(Workbook workbook) {
        CellStyle newCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        return newCellStyle;
    }

    /**
     * Build style
     * 
     * @param workbook
     * @param cs
     * @return
     */
    public static CellStyle buildHeadCellStyle(Workbook workbook, com.alibaba.excel.metadata.CellStyle cs) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (cs == null) {
            return cellStyle;
        }
        return buildCellStyle(workbook, cellStyle, cs.getFont(), cs.getIndexedColors());
    }

    /**
     *
     * @param workbook
     * @param f
     * @param indexedColors
     * @return
     */
    public static CellStyle buildHeadCellStyle(Workbook workbook, com.alibaba.excel.metadata.Font f,
        IndexedColors indexedColors) {
        CellStyle cellStyle = workbook.createCellStyle();
        return buildCellStyle(workbook, cellStyle, f, indexedColors);
    }

    /**
     * Build style
     *
     * @param workbook
     * @param cs
     * @return
     */
    public static CellStyle buildContentCellStyle(Workbook workbook, com.alibaba.excel.metadata.CellStyle cs) {
        CellStyle cellStyle = buildDefaultCellStyle(workbook);
        if (cs == null) {
            return cellStyle;
        }
        return buildCellStyle(workbook, cellStyle, cs.getFont(), cs.getIndexedColors());
    }

    /**
     *
     * @param workbook
     * @param f
     * @param indexedColors
     * @return
     */
    public static CellStyle buildContentCellStyle(Workbook workbook, com.alibaba.excel.metadata.Font f,
        IndexedColors indexedColors) {
        CellStyle cellStyle = buildDefaultCellStyle(workbook);
        return buildCellStyle(workbook, cellStyle, f, indexedColors);
    }

    /**
     *
     * @param workbook
     * @param f
     * @param indexedColors
     * @return
     */
    private static CellStyle buildCellStyle(Workbook workbook, CellStyle cellStyle, com.alibaba.excel.metadata.Font f,
        IndexedColors indexedColors) {
        if (f != null) {
            Font font = workbook.createFont();
            font.setFontName(f.getFontName());
            font.setFontHeightInPoints(f.getFontHeightInPoints());
            font.setBold(f.isBold());
            cellStyle.setFont(font);
        }
        if (indexedColors != null) {
            cellStyle.setFillForegroundColor(indexedColors.getIndex());
        }
        return cellStyle;
    }
}
