package com.alibaba.excel.metadata.property;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;

/**
 * Configuration from annotations
 * 
 * @author zhuangjiaju
 */
public class CellStyleProperty {
    private String fontName;
    private Short fontHeightInPoints;
    private Boolean bold;
    private IndexedColors indexedColors;

    public CellStyleProperty(String fontName, Short fontHeightInPoints, Boolean bold, IndexedColors indexedColors) {
        this.fontName = fontName;
        this.fontHeightInPoints = fontHeightInPoints;
        this.bold = bold;
        this.indexedColors = indexedColors;
    }

    public static CellStyleProperty build(HeadStyle headStyle) {
        if (headStyle == null) {
            return null;
        }
        boolean isDefault = "宋体".equals(headStyle.fontName()) && headStyle.fontHeightInPoints() == 14
            && headStyle.bold() && IndexedColors.GREY_25_PERCENT.equals(headStyle.indexedColors());
        if (isDefault) {
            return null;
        }
        return new CellStyleProperty(headStyle.fontName(), headStyle.fontHeightInPoints(), headStyle.bold(),
            headStyle.indexedColors());
    }

    public static CellStyleProperty build(ContentStyle contentStyle) {
        if (contentStyle == null) {
            return null;
        }
        boolean isDefault = "宋体".equals(contentStyle.fontName()) && contentStyle.fontHeightInPoints() == 14
            && contentStyle.bold() && IndexedColors.WHITE1.equals(contentStyle.indexedColors());
        if (isDefault) {
            return null;
        }
        return new CellStyleProperty(contentStyle.fontName(), contentStyle.fontHeightInPoints(), contentStyle.bold(),
            contentStyle.indexedColors());
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    public void setFontHeightInPoints(Short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public IndexedColors getIndexedColors() {
        return indexedColors;
    }

    public void setIndexedColors(IndexedColors indexedColors) {
        this.indexedColors = indexedColors;
    }

}
