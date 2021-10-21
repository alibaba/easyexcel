package com.alibaba.easyexcel.test.core;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class StyleTestUtils {

    public static byte[] getFillForegroundColor(Cell cell) {
        if (cell instanceof XSSFCell) {
            return ((XSSFCell)cell).getCellStyle().getFillForegroundColorColor().getRGB();
        } else {
            return short2byte(((HSSFCell)cell).getCellStyle().getFillForegroundColorColor().getTriplet());
        }
    }

    public static byte[] getFontColor(Cell cell, Workbook workbook) {
        if (cell instanceof XSSFCell) {
            return ((XSSFCell)cell).getCellStyle().getFont().getXSSFColor().getRGB();
        } else {
            return short2byte(((HSSFCell)cell).getCellStyle().getFont(workbook).getHSSFColor((HSSFWorkbook)workbook)
                .getTriplet());
        }
    }

    public static short getFontHeightInPoints(Cell cell, Workbook workbook) {
        if (cell instanceof XSSFCell) {
            return ((XSSFCell)cell).getCellStyle().getFont().getFontHeightInPoints();
        } else {
            return ((HSSFCell)cell).getCellStyle().getFont(workbook).getFontHeightInPoints();
        }
    }

    private static byte[] short2byte(short[] shorts) {
        byte[] bytes = new byte[shorts.length];
        for (int i = 0; i < shorts.length; i++) {
            bytes[i] = (byte)shorts[i];
        }
        return bytes;
    }
}
