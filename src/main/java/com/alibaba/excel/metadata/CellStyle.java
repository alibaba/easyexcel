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
    private Font simpleFont;

    public IndexedColors getIndexedColors() {
        return indexedColors;
    }

    public void setIndexedColors(IndexedColors indexedColors) {
        this.indexedColors = indexedColors;
    }

    public Font getSimpleFont() {
        return simpleFont;
    }

    public void setSimpleFont(Font simpleFont) {
        this.simpleFont = simpleFont;
    }
}
