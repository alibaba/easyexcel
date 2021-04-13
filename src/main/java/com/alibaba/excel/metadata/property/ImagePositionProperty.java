package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.write.style.ImagePosition;

/**
 * Keep the image position information from an annotation.
 *
 * @author Pengliang Zhao
 */
public class ImagePositionProperty {

    /**
     * The x coordinate within the first cell.
     */
    private int dx1;

    /**
     * The y coordinate within the first cell.
     */
    private int dy1;

    /**
     * The x coordinate within the second cell.
     */
    private int dx2;

    /**
     * The y coordinate within the second cell
     */
    private int dy2;

    /**
     * 0-based column of the first cell.
     */
    private short col1;

    /**
     * 0-based row of the first cell.
     */
    private int row1;

    /**
     * 0-based column of the second cell.
     */
    private short col2;

    /**
     * 0-based row of the second cell.
     */
    private int row2;

    public static ImagePositionProperty build(ImagePosition imagePosition) {
        if (imagePosition == null) {
            return null;
        }
        return new ImagePositionProperty(imagePosition.dx1(), imagePosition.dy1(), imagePosition.dx2(),
            imagePosition.dy2(), imagePosition.col1(), imagePosition.row1(), imagePosition.col2(),
            imagePosition.row2());
    }

    public ImagePositionProperty(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2) {
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
        this.col1 = col1;
        this.row1 = row1;
        this.col2 = col2;
        this.row2 = row2;
    }

    public int getDx1() {
        return dx1;
    }

    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }

    public int getDy1() {
        return dy1;
    }

    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }

    public int getDx2() {
        return dx2;
    }

    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }

    public int getDy2() {
        return dy2;
    }

    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }

    public short getCol1() {
        return col1;
    }

    public void setCol1(short col1) {
        this.col1 = col1;
    }

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public short getCol2() {
        return col2;
    }

    public void setCol2(short col2) {
        this.col2 = col2;
    }

    public int getRow2() {
        return row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }
}
