package com.alibaba.excel.metadata;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author jipengfei
 */
public class Font {

    /**
     */
    private String fontName;

    /**
     */
    private short fontHeightInPoints;

    /**
     */
    private boolean bold;

    /**
     *
     */
    private IndexedColors color=IndexedColors.BLACK;

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    public void setFontHeightInPoints(short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public IndexedColors getColor() {
        return color;
    }

    public void setColor(IndexedColors color) {
        this.color = color;
    }
}
