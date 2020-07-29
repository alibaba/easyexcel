package com.alibaba.excel.metadata.property;

import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
public class FontProperty {
    /**
     * The name for the font (i.e. Arial)
     */
    private String fontName;
    /**
     * Height in the familiar unit of measure - points
     */
    private Short fontHeightInPoints;
    /**
     * Whether to use italics or not
     */
    private Boolean italic;
    /**
     * Whether to use a strikeout horizontal line through the text or not
     */
    private Boolean strikeout;
    /**
     * The color for the font
     *
     * @see Font#COLOR_NORMAL
     * @see Font#COLOR_RED
     * @see HSSFPalette#getColor(short)
     * @see IndexedColors
     */
    private Short color;
    /**
     * Set normal,super or subscript.
     *
     * @see Font#SS_NONE
     * @see Font#SS_SUPER
     * @see Font#SS_SUB
     */
    private Short typeOffset;
    /**
     * set type of text underlining to use
     *
     * @see Font#U_NONE
     * @see Font#U_SINGLE
     * @see Font#U_DOUBLE
     * @see Font#U_SINGLE_ACCOUNTING
     * @see Font#U_DOUBLE_ACCOUNTING
     */

    private Byte underline;
    /**
     * Set character-set to use.
     *
     * @see FontCharset
     * @see Font#ANSI_CHARSET
     * @see Font#DEFAULT_CHARSET
     * @see Font#SYMBOL_CHARSET
     */
    private Integer charset;
    /**
     * Bold
     */
    private Boolean bold;

    public static FontProperty build(HeadFontStyle headFontStyle) {
        if (headFontStyle == null) {
            return null;
        }
        FontProperty styleProperty = new FontProperty();
        styleProperty.setFontName(headFontStyle.fontName());
        styleProperty.setFontHeightInPoints(headFontStyle.fontHeightInPoints());
        styleProperty.setItalic(headFontStyle.italic());
        styleProperty.setStrikeout(headFontStyle.strikeout());
        styleProperty.setColor(headFontStyle.color());
        styleProperty.setTypeOffset(headFontStyle.typeOffset());
        styleProperty.setUnderline(headFontStyle.underline());
        styleProperty.setCharset(headFontStyle.charset());
        styleProperty.setBold(headFontStyle.bold());
        return styleProperty;
    }

    public static FontProperty build(ContentFontStyle contentFontStyle) {
        if (contentFontStyle == null) {
            return null;
        }
        FontProperty styleProperty = new FontProperty();
        styleProperty.setFontName(contentFontStyle.fontName());
        styleProperty.setFontHeightInPoints(contentFontStyle.fontHeightInPoints());
        styleProperty.setItalic(contentFontStyle.italic());
        styleProperty.setStrikeout(contentFontStyle.strikeout());
        styleProperty.setColor(contentFontStyle.color());
        styleProperty.setTypeOffset(contentFontStyle.typeOffset());
        styleProperty.setUnderline(contentFontStyle.underline());
        styleProperty.setCharset(contentFontStyle.charset());
        styleProperty.setBold(contentFontStyle.bold());
        return styleProperty;
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

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getStrikeout() {
        return strikeout;
    }

    public void setStrikeout(Boolean strikeout) {
        this.strikeout = strikeout;
    }

    public Short getColor() {
        return color;
    }

    public void setColor(Short color) {
        this.color = color;
    }

    public Short getTypeOffset() {
        return typeOffset;
    }

    public void setTypeOffset(Short typeOffset) {
        this.typeOffset = typeOffset;
    }

    public Byte getUnderline() {
        return underline;
    }

    public void setUnderline(Byte underline) {
        this.underline = underline;
    }

    public Integer getCharset() {
        return charset;
    }

    public void setCharset(Integer charset) {
        this.charset = charset;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }
}
