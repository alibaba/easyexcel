package com.alibaba.excel.annotation.write.style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Custom header styles.
 *
 * @author Jiaju Zhuang
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HeadFontStyle {

    /**
     * The name for the font (i.e. Arial)
     */
    String fontName() default "宋体";

    /**
     * Height in the familiar unit of measure - points
     */
    short fontHeightInPoints() default 14;

    /**
     * Whether to use italics or not
     */
    boolean italic() default false;

    /**
     * Whether to use a strikeout horizontal line through the text or not
     */
    boolean strikeout() default false;

    /**
     * The color for the font
     *
     * @see Font#COLOR_NORMAL
     * @see Font#COLOR_RED
     * @see HSSFPalette#getColor(short)
     * @see IndexedColors
     */
    short color() default -1;

    /**
     * Set normal,super or subscript.
     *
     * @see Font#SS_NONE
     * @see Font#SS_SUPER
     * @see Font#SS_SUB
     */
    short typeOffset() default -1;

    /**
     * set type of text underlining to use
     *
     * @see Font#U_NONE
     * @see Font#U_SINGLE
     * @see Font#U_DOUBLE
     * @see Font#U_SINGLE_ACCOUNTING
     * @see Font#U_DOUBLE_ACCOUNTING
     */

    byte underline() default -1;

    /**
     * Set character-set to use.
     *
     * @see FontCharset
     * @see Font#ANSI_CHARSET
     * @see Font#DEFAULT_CHARSET
     * @see Font#SYMBOL_CHARSET
     */
    int charset() default -1;

    /**
     * Bold
     */
    boolean bold() default true;
}
