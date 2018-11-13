package com.alibaba.excel.metadata;

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
}
