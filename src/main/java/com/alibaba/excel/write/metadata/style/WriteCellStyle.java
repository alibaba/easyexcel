package com.alibaba.excel.write.metadata.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.util.StringUtils;

/**
 * Cell style when writing
 *
 * @author Jiaju Zhuang
 */
public class WriteCellStyle {
    /**
     * Set the data format (must be a valid format). Built in formats are defined at {@link BuiltinFormats}.
     */
    private Short dataFormat;
    /**
     * Set the font for this style
     */
    private WriteFont writeFont;
    /**
     * Set the cell's using this style to be hidden
     */
    private Boolean hidden;

    /**
     * Set the cell's using this style to be locked
     */
    private Boolean locked;
    /**
     * Turn on or off "Quote Prefix" or "123 Prefix" for the style, which is used to tell Excel that the thing which
     * looks like a number or a formula shouldn't be treated as on. Turning this on is somewhat (but not completely, see
     * {@link IgnoredErrorType}) like prefixing the cell value with a ' in Excel
     */
    private Boolean quotePrefix;
    /**
     * Set the type of horizontal alignment for the cell
     */
    private HorizontalAlignment horizontalAlignment;
    /**
     * Set whether the text should be wrapped. Setting this flag to <code>true</code> make all content visible within a
     * cell by displaying it on multiple lines
     *
     */
    private Boolean wrapped;
    /**
     * Set the type of vertical alignment for the cell
     */
    private VerticalAlignment verticalAlignment;
    /**
     * Set the degree of rotation for the text in the cell.
     *
     * Note: HSSF uses values from -90 to 90 degrees, whereas XSSF uses values from 0 to 180 degrees. The
     * implementations of this method will map between these two value-ranges accordingly, however the corresponding
     * getter is returning values in the range mandated by the current type of Excel file-format that this CellStyle is
     * applied to.
     */
    private Short rotation;
    /**
     * Set the number of spaces to indent the text in the cell
     */
    private Short indent;
    /**
     * Set the type of border to use for the left border of the cell
     */
    private BorderStyle borderLeft;
    /**
     * Set the type of border to use for the right border of the cell
     */
    private BorderStyle borderRight;
    /**
     * Set the type of border to use for the top border of the cell
     */
    private BorderStyle borderTop;

    /**
     * Set the type of border to use for the bottom border of the cell
     */
    private BorderStyle borderBottom;
    /**
     * Set the color to use for the left border
     *
     * @see IndexedColors
     */
    private Short leftBorderColor;

    /**
     * Set the color to use for the right border
     *
     * @see IndexedColors
     *
     */
    private Short rightBorderColor;

    /**
     * Set the color to use for the top border
     *
     * @see IndexedColors
     *
     */
    private Short topBorderColor;
    /**
     * Set the color to use for the bottom border
     *
     * @see IndexedColors
     *
     */
    private Short bottomBorderColor;
    /**
     * Setting to one fills the cell with the foreground color... No idea about other values
     *
     * @see FillPatternType#SOLID_FOREGROUND
     */
    private FillPatternType fillPatternType;

    /**
     * Set the background fill color.
     *
     * @see IndexedColors
     *
     */
    private Short fillBackgroundColor;

    /**
     * Set the foreground fill color <i>Note: Ensure Foreground color is set prior to background color.</i>
     *
     * @see IndexedColors
     *
     */
    private Short fillForegroundColor;
    /**
     * Controls if the Cell should be auto-sized to shrink to fit if the text is too long
     */
    private Boolean shrinkToFit;

    public static WriteCellStyle build(StyleProperty styleProperty, FontProperty fontProperty) {
        if (styleProperty == null && fontProperty == null) {
            return null;
        }
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        if (styleProperty != null) {
            if (styleProperty.getDataFormat() >= 0) {
                writeCellStyle.setDataFormat(styleProperty.getDataFormat());
            }
            writeCellStyle.setHidden(styleProperty.getHidden());
            writeCellStyle.setLocked(styleProperty.getLocked());
            writeCellStyle.setQuotePrefix(styleProperty.getQuotePrefix());
            writeCellStyle.setHorizontalAlignment(styleProperty.getHorizontalAlignment());
            writeCellStyle.setWrapped(styleProperty.getWrapped());
            writeCellStyle.setVerticalAlignment(styleProperty.getVerticalAlignment());
            if (styleProperty.getRotation() >= 0) {
                writeCellStyle.setRotation(styleProperty.getRotation());
            }
            if (styleProperty.getIndent() >= 0) {
                writeCellStyle.setIndent(styleProperty.getIndent());
            }
            writeCellStyle.setBorderLeft(styleProperty.getBorderLeft());
            writeCellStyle.setBorderRight(styleProperty.getBorderRight());
            writeCellStyle.setBorderTop(styleProperty.getBorderTop());
            writeCellStyle.setBorderBottom(styleProperty.getBorderBottom());
            if (styleProperty.getLeftBorderColor() >= 0) {
                writeCellStyle.setLeftBorderColor(styleProperty.getLeftBorderColor());
            }
            if (styleProperty.getRightBorderColor() >= 0) {
                writeCellStyle.setRightBorderColor(styleProperty.getRightBorderColor());
            }
            if (styleProperty.getTopBorderColor() >= 0) {
                writeCellStyle.setTopBorderColor(styleProperty.getTopBorderColor());
            }
            if (styleProperty.getBottomBorderColor() >= 0) {
                writeCellStyle.setBottomBorderColor(styleProperty.getBottomBorderColor());
            }
            writeCellStyle.setFillPatternType(styleProperty.getFillPatternType());
            if (styleProperty.getFillBackgroundColor() >= 0) {
                writeCellStyle.setFillBackgroundColor(styleProperty.getFillBackgroundColor());
            }
            if (styleProperty.getFillForegroundColor() >= 0) {
                writeCellStyle.setFillForegroundColor(styleProperty.getFillForegroundColor());
            }
            writeCellStyle.setShrinkToFit(styleProperty.getShrinkToFit());
        }
        if (fontProperty != null) {
            WriteFont writeFont = new WriteFont();
            writeCellStyle.setWriteFont(writeFont);
            if (!StringUtils.isEmpty(fontProperty.getFontName())) {
                writeFont.setFontName(fontProperty.getFontName());
            }
            writeFont.setFontHeightInPoints(fontProperty.getFontHeightInPoints());
            writeFont.setItalic(fontProperty.getItalic());
            writeFont.setStrikeout(fontProperty.getStrikeout());
            if (fontProperty.getColor() >= 0) {
                writeFont.setColor(fontProperty.getColor());
            }
            if (fontProperty.getTypeOffset() >= 0) {
                writeFont.setTypeOffset(fontProperty.getTypeOffset());
            }
            if (fontProperty.getUnderline() >= 0) {
                writeFont.setUnderline(fontProperty.getUnderline());
            }
            if (fontProperty.getCharset() >= 0) {
                writeFont.setCharset(fontProperty.getCharset());
            }
            writeFont.setBold(fontProperty.getBold());
        }

        return writeCellStyle;
    }

    public Short getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(Short dataFormat) {
        this.dataFormat = dataFormat;
    }

    public WriteFont getWriteFont() {
        return writeFont;
    }

    public void setWriteFont(WriteFont writeFont) {
        this.writeFont = writeFont;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getQuotePrefix() {
        return quotePrefix;
    }

    public void setQuotePrefix(Boolean quotePrefix) {
        this.quotePrefix = quotePrefix;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public Boolean getWrapped() {
        return wrapped;
    }

    public void setWrapped(Boolean wrapped) {
        this.wrapped = wrapped;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public Short getRotation() {
        return rotation;
    }

    public void setRotation(Short rotation) {
        this.rotation = rotation;
    }

    public Short getIndent() {
        return indent;
    }

    public void setIndent(Short indent) {
        this.indent = indent;
    }

    public BorderStyle getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(BorderStyle borderLeft) {
        this.borderLeft = borderLeft;
    }

    public BorderStyle getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(BorderStyle borderRight) {
        this.borderRight = borderRight;
    }

    public BorderStyle getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(BorderStyle borderTop) {
        this.borderTop = borderTop;
    }

    public BorderStyle getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(BorderStyle borderBottom) {
        this.borderBottom = borderBottom;
    }

    public Short getLeftBorderColor() {
        return leftBorderColor;
    }

    public void setLeftBorderColor(Short leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public Short getRightBorderColor() {
        return rightBorderColor;
    }

    public void setRightBorderColor(Short rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public Short getTopBorderColor() {
        return topBorderColor;
    }

    public void setTopBorderColor(Short topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public Short getBottomBorderColor() {
        return bottomBorderColor;
    }

    public void setBottomBorderColor(Short bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public FillPatternType getFillPatternType() {
        return fillPatternType;
    }

    public void setFillPatternType(FillPatternType fillPatternType) {
        this.fillPatternType = fillPatternType;
    }

    public Short getFillBackgroundColor() {
        return fillBackgroundColor;
    }

    public void setFillBackgroundColor(Short fillBackgroundColor) {
        this.fillBackgroundColor = fillBackgroundColor;
    }

    public Short getFillForegroundColor() {
        return fillForegroundColor;
    }

    public void setFillForegroundColor(Short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }

    public Boolean getShrinkToFit() {
        return shrinkToFit;
    }

    public void setShrinkToFit(Boolean shrinkToFit) {
        this.shrinkToFit = shrinkToFit;
    }
}
