package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * Custom content styles
 *
 * @author Jiaju Zhuang
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ContentStyle {
    /**
     * Set the data format (must be a valid format). Built in formats are defined at {@link BuiltinFormats}.
     */
    short dataFormat() default -1;

    /**
     * Set the cell's using this style to be hidden
     */
    boolean hidden() default false;

    /**
     * Set the cell's using this style to be locked
     */
    boolean locked() default false;

    /**
     * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which
     * looks like a number or a formula shouldn't be treated as on. Turning this on is somewhat (but not completely, see
     * {@link IgnoredErrorType}) like prefixing the cell value with a ' in Excel
     */
    boolean quotePrefix() default false;

    /**
     * Set the type of horizontal alignment for the cell
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.GENERAL;

    /**
     * Set whether the text should be wrapped. Setting this flag to <code>true</code> make all content visible within a
     * cell by displaying it on multiple lines
     *
     */
    boolean wrapped() default false;

    /**
     * Set the type of vertical alignment for the cell
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;

    /**
     * Set the degree of rotation for the text in the cell.
     *
     * Note: HSSF uses values from -90 to 90 degrees, whereas XSSF uses values from 0 to 180 degrees. The
     * implementations of this method will map between these two value-ranges accordingly, however the corresponding
     * getter is returning values in the range mandated by the current type of Excel file-format that this CellStyle is
     * applied to.
     */
    short rotation() default -1;

    /**
     * Set the number of spaces to indent the text in the cell
     */
    short indent() default -1;

    /**
     * Set the type of border to use for the left border of the cell
     */
    BorderStyle borderLeft() default BorderStyle.NONE;

    /**
     * Set the type of border to use for the right border of the cell
     */
    BorderStyle borderRight() default BorderStyle.NONE;

    /**
     * Set the type of border to use for the top border of the cell
     */
    BorderStyle borderTop() default BorderStyle.NONE;

    /**
     * Set the type of border to use for the bottom border of the cell
     */
    BorderStyle borderBottom() default BorderStyle.NONE;

    /**
     * Set the color to use for the left border
     *
     * @see IndexedColors
     */
    short leftBorderColor() default -1;

    /**
     * Set the color to use for the right border
     *
     * @see IndexedColors
     *
     */
    short rightBorderColor() default -1;

    /**
     * Set the color to use for the top border
     *
     * @see IndexedColors
     *
     */
    short topBorderColor() default -1;

    /**
     * Set the color to use for the bottom border
     *
     * @see IndexedColors
     *
     */
    short bottomBorderColor() default -1;

    /**
     * Setting to one fills the cell with the foreground color... No idea about other values
     *
     * @see FillPatternType#SOLID_FOREGROUND
     */
    FillPatternType fillPatternType() default FillPatternType.NO_FILL;

    /**
     * Set the background fill color.
     *
     * @see IndexedColors
     *
     */
    short fillBackgroundColor() default -1;

    /**
     * Set the foreground fill color <i>Note: Ensure Foreground color is set prior to background color.</i>
     *
     * @see IndexedColors
     *
     */
    short fillForegroundColor() default -1;

    /**
     * Controls if the Cell should be auto-sized to shrink to fit if the text is too long
     */
    boolean shrinkToFit() default false;

}
