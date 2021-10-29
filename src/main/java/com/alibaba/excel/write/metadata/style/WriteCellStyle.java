package com.alibaba.excel.write.metadata.style;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.util.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * Cell style when writing
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class WriteCellStyle {
    /**
     * Set the data format (must be a valid format). Built in formats are defined at {@link BuiltinFormats}.
     */
    private DataFormatData dataFormatData;

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
     */
    private Short rightBorderColor;

    /**
     * Set the color to use for the top border
     *
     * @see IndexedColors
     */
    private Short topBorderColor;
    /**
     * Set the color to use for the bottom border
     *
     * @see IndexedColors
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
     */
    private Short fillBackgroundColor;

    /**
     * Set the foreground fill color <i>Note: Ensure Foreground color is set prior to background color.</i>
     *
     * @see IndexedColors
     */
    private Short fillForegroundColor;
    /**
     * Controls if the Cell should be auto-sized to shrink to fit if the text is too long
     */
    private Boolean shrinkToFit;

    /**
     * The source is not empty merge the data to the target.
     *
     * @param source source
     * @param target target
     */
    public static void merge(WriteCellStyle source, WriteCellStyle target) {
        if (source == null || target == null) {
            return;
        }
        if (source.getDataFormatData() != null) {
            if (target.getDataFormatData() == null) {
                target.setDataFormatData(source.getDataFormatData());
            } else {
                DataFormatData.merge(source.getDataFormatData(), target.getDataFormatData());
            }
        }
        if (source.getWriteFont() != null) {
            if (target.getWriteFont() == null) {
                target.setWriteFont(source.getWriteFont());
            } else {
                WriteFont.merge(source.getWriteFont(), target.getWriteFont());
            }
        }
        if (source.getHidden() != null) {
            target.setHidden(source.getHidden());
        }
        if (source.getLocked() != null) {
            target.setLocked(source.getLocked());
        }
        if (source.getQuotePrefix() != null) {
            target.setQuotePrefix(source.getQuotePrefix());
        }
        if (source.getHorizontalAlignment() != null) {
            target.setHorizontalAlignment(source.getHorizontalAlignment());
        }
        if (source.getWrapped() != null) {
            target.setWrapped(source.getWrapped());
        }
        if (source.getVerticalAlignment() != null) {
            target.setVerticalAlignment(source.getVerticalAlignment());
        }
        if (source.getRotation() != null) {
            target.setRotation(source.getRotation());
        }
        if (source.getIndent() != null) {
            target.setIndent(source.getIndent());
        }
        if (source.getBorderLeft() != null) {
            target.setBorderLeft(source.getBorderLeft());
        }
        if (source.getBorderRight() != null) {
            target.setBorderRight(source.getBorderRight());
        }
        if (source.getBorderTop() != null) {
            target.setBorderTop(source.getBorderTop());
        }
        if (source.getBorderBottom() != null) {
            target.setBorderBottom(source.getBorderBottom());
        }
        if (source.getLeftBorderColor() != null) {
            target.setLeftBorderColor(source.getLeftBorderColor());
        }
        if (source.getRightBorderColor() != null) {
            target.setRightBorderColor(source.getRightBorderColor());
        }
        if (source.getTopBorderColor() != null) {
            target.setTopBorderColor(source.getTopBorderColor());
        }
        if (source.getBottomBorderColor() != null) {
            target.setBottomBorderColor(source.getBottomBorderColor());
        }
        if (source.getFillPatternType() != null) {
            target.setFillPatternType(source.getFillPatternType());
        }
        if (source.getFillBackgroundColor() != null) {
            target.setFillBackgroundColor(source.getFillBackgroundColor());
        }
        if (source.getFillForegroundColor() != null) {
            target.setFillForegroundColor(source.getFillForegroundColor());
        }
        if (source.getShrinkToFit() != null) {
            target.setShrinkToFit(source.getShrinkToFit());
        }
    }

    /**
     * The source is not empty merge the data to the target.
     *
     * @param styleProperty styleProperty
     * @param fontProperty  fontProperty
     */
    public static WriteCellStyle build(StyleProperty styleProperty, FontProperty fontProperty) {
        if (styleProperty == null && fontProperty == null) {
            return null;
        }
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        buildStyleProperty(styleProperty, writeCellStyle);
        buildFontProperty(fontProperty, writeCellStyle);
        return writeCellStyle;
    }

    private static void buildFontProperty(FontProperty fontProperty, WriteCellStyle writeCellStyle) {
        if (fontProperty == null) {
            return;
        }
        if (writeCellStyle.getWriteFont() == null) {
            writeCellStyle.setWriteFont(new WriteFont());
        }
        WriteFont writeFont = writeCellStyle.getWriteFont();

        if (StringUtils.isNotBlank(fontProperty.getFontName())) {
            writeFont.setFontName(fontProperty.getFontName());
        }
        if (fontProperty.getFontHeightInPoints() != null) {
            writeFont.setFontHeightInPoints(fontProperty.getFontHeightInPoints());
        }
        if (fontProperty.getItalic() != null) {
            writeFont.setItalic(fontProperty.getItalic());
        }
        if (fontProperty.getStrikeout() != null) {
            writeFont.setStrikeout(fontProperty.getStrikeout());
        }
        if (fontProperty.getColor() != null) {
            writeFont.setColor(fontProperty.getColor());
        }
        if (fontProperty.getTypeOffset() != null) {
            writeFont.setTypeOffset(fontProperty.getTypeOffset());
        }
        if (fontProperty.getUnderline() != null) {
            writeFont.setUnderline(fontProperty.getUnderline());
        }
        if (fontProperty.getCharset() != null) {
            writeFont.setCharset(fontProperty.getCharset());
        }
        if (fontProperty.getBold() != null) {
            writeFont.setBold(fontProperty.getBold());
        }
    }

    private static void buildStyleProperty(StyleProperty styleProperty, WriteCellStyle writeCellStyle) {
        if (styleProperty == null) {
            return;
        }
        if (styleProperty.getDataFormatData() != null) {
            if (writeCellStyle.getDataFormatData() == null) {
                writeCellStyle.setDataFormatData(styleProperty.getDataFormatData());
            } else {
                DataFormatData.merge(styleProperty.getDataFormatData(), writeCellStyle.getDataFormatData());
            }
        }
        if (styleProperty.getHidden() != null) {
            writeCellStyle.setHidden(styleProperty.getHidden());
        }
        if (styleProperty.getLocked() != null) {
            writeCellStyle.setLocked(styleProperty.getLocked());
        }
        if (styleProperty.getQuotePrefix() != null) {
            writeCellStyle.setQuotePrefix(styleProperty.getQuotePrefix());
        }
        if (styleProperty.getHorizontalAlignment() != null) {
            writeCellStyle.setHorizontalAlignment(styleProperty.getHorizontalAlignment());
        }
        if (styleProperty.getWrapped() != null) {
            writeCellStyle.setWrapped(styleProperty.getWrapped());
        }
        if (styleProperty.getVerticalAlignment() != null) {
            writeCellStyle.setVerticalAlignment(styleProperty.getVerticalAlignment());
        }
        if (styleProperty.getRotation() != null) {
            writeCellStyle.setRotation(styleProperty.getRotation());
        }
        if (styleProperty.getIndent() != null) {
            writeCellStyle.setIndent(styleProperty.getIndent());
        }
        if (styleProperty.getBorderLeft() != null) {
            writeCellStyle.setBorderLeft(styleProperty.getBorderLeft());
        }
        if (styleProperty.getBorderRight() != null) {
            writeCellStyle.setBorderRight(styleProperty.getBorderRight());
        }
        if (styleProperty.getBorderTop() != null) {
            writeCellStyle.setBorderTop(styleProperty.getBorderTop());
        }
        if (styleProperty.getBorderBottom() != null) {
            writeCellStyle.setBorderBottom(styleProperty.getBorderBottom());
        }
        if (styleProperty.getLeftBorderColor() != null) {
            writeCellStyle.setLeftBorderColor(styleProperty.getLeftBorderColor());
        }
        if (styleProperty.getRightBorderColor() != null) {
            writeCellStyle.setRightBorderColor(styleProperty.getRightBorderColor());
        }
        if (styleProperty.getTopBorderColor() != null) {
            writeCellStyle.setTopBorderColor(styleProperty.getTopBorderColor());
        }
        if (styleProperty.getBottomBorderColor() != null) {
            writeCellStyle.setBottomBorderColor(styleProperty.getBottomBorderColor());
        }
        if (styleProperty.getFillPatternType() != null) {
            writeCellStyle.setFillPatternType(styleProperty.getFillPatternType());
        }
        if (styleProperty.getFillBackgroundColor() != null) {
            writeCellStyle.setFillBackgroundColor(styleProperty.getFillBackgroundColor());
        }
        if (styleProperty.getFillForegroundColor() != null) {
            writeCellStyle.setFillForegroundColor(styleProperty.getFillForegroundColor());
        }
        if (styleProperty.getShrinkToFit() != null) {
            writeCellStyle.setShrinkToFit(styleProperty.getShrinkToFit());
        }

    }

}
