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
