package com.alibaba.excel.metadata;

/**
 * 字体样式
 *
 * @author jipengfei
 */
public class Font {

    /**
     * 字体名称，如：宋体、黑体
     */
    private String fontName;

    /**
     * 字体大小
     */
    private short fontHeightInPoints;

    /**
     * 是否加粗
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
