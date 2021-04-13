package com.alibaba.excel.annotation.write.style;


import org.apache.poi.ss.usermodel.ClientAnchor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * This annotation is used to set the position of a picture.
 * See {@link ClientAnchor}
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ImagePosition {

    /**
     * The x coordinate within the first cell.
     */
    int dx1();

    /**
     * The y coordinate within the first cell.
     */
    int dy1();

    /**
     * The x coordinate within the second cell.
     */
    int dx2();

    /**
     * The y coordinate within the second cell
     */
    int dy2();

    /**
     * 0-based column of the first cell.
     */
    short col1();

    /**
     * 0-based row of the first cell.
     */
    int row1();

    /**
     * 0-based column of the second cell.
     */
    short col2();

    /**
     * 0-based row of the second cell.
     */
    int row2();
}
