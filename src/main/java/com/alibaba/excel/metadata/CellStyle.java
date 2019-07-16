package com.alibaba.excel.metadata;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Simple cell style
 * 
 * @author zhuangjiaju
 */
public class CellStyle {
    /**
     * 表头背景颜色
     */
    private IndexedColors indexedColors;
    /**
     * 表头字体样式
     */
    private Font font;

    public CellStyle() {

    }

    public CellStyle(String fontName, Short fontHeightInPoints, Boolean bold, IndexedColors indexedColors) {
        Font font = new Font();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontHeightInPoints);
        font.setBold(bold);
        this.font = font;
        this.indexedColors = indexedColors;
    }

    public IndexedColors getIndexedColors() {
        return indexedColors;
    }

    public void setIndexedColors(IndexedColors indexedColors) {
        this.indexedColors = indexedColors;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
