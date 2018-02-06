package com.alibaba.excel.metadata;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author jipengfei
 */
public class TableStyle {

    /**
     * 表头背景颜色
     */
    private IndexedColors tableHeadBackGroundColor;

    /**
     * 表头字体样式
     */
    private Font tableHeadFont;

    /**
     * 表格内容字体样式
     */
    private Font tableContentFont;

    /**
     * 表格内容背景颜色
     */
    private IndexedColors tableContentBackGroundColor;

    public IndexedColors getTableHeadBackGroundColor() {
        return tableHeadBackGroundColor;
    }

    public void setTableHeadBackGroundColor(IndexedColors tableHeadBackGroundColor) {
        this.tableHeadBackGroundColor = tableHeadBackGroundColor;
    }

    public Font getTableHeadFont() {
        return tableHeadFont;
    }

    public void setTableHeadFont(Font tableHeadFont) {
        this.tableHeadFont = tableHeadFont;
    }

    public Font getTableContentFont() {
        return tableContentFont;
    }

    public void setTableContentFont(Font tableContentFont) {
        this.tableContentFont = tableContentFont;
    }

    public IndexedColors getTableContentBackGroundColor() {
        return tableContentBackGroundColor;
    }

    public void setTableContentBackGroundColor(IndexedColors tableContentBackGroundColor) {
        this.tableContentBackGroundColor = tableContentBackGroundColor;
    }
}
