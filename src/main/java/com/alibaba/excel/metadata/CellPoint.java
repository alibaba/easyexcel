package com.alibaba.excel.metadata;

/**
 * 用来定位连续的文字相同的起点和重点的单元格坐标对象
 * @author Lucas xie
 * @date 2020-1-17
 * @since 1.0.0L
 */
public class CellPoint {
    /**
     * 开始单元格x坐标
     * start cell x point
     */
    private int startX;
    /**
     * 结束单元格x坐标
     * end cell x point
     */
    private int endX;
    /**
     * 开始单元格y坐标
     * start cell y point
     */
    private int startY;
    /**
     * 结束单元格y坐标
     * end cell y point
     */
    private int endY;
    /**
     * 单元格内容，文本
     * cell content, text
     */
    private String text;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
