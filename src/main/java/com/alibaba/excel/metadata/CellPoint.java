package com.alibaba.excel.metadata;

/**
 * 用来定位连续的文字相同的起点和重点的单元格坐标对象
 * @author Lucas
 * @date 2020-1-17
 * @since 1.0.0L
 */
public class CellPoint {
    private int startX;
    private int endX;
    private int startY;
    private int endY;
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
