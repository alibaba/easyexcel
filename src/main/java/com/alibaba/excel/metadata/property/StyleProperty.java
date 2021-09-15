package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.metadata.style.WriteFont;

import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
@Data
public class StyleProperty {
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

    public static StyleProperty build(HeadStyle headStyle) {
        if (headStyle == null) {
            return null;
        }
        StyleProperty styleProperty = new StyleProperty();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setIndex(headStyle.dataFormat());
        styleProperty.setDataFormatData(dataFormatData);
        styleProperty.setHidden(headStyle.hidden());
        styleProperty.setLocked(headStyle.locked());
        styleProperty.setQuotePrefix(headStyle.quotePrefix());
        styleProperty.setHorizontalAlignment(headStyle.horizontalAlignment());
        styleProperty.setWrapped(headStyle.wrapped());
        styleProperty.setVerticalAlignment(headStyle.verticalAlignment());
        styleProperty.setRotation(headStyle.rotation());
        styleProperty.setIndent(headStyle.indent());
        styleProperty.setBorderLeft(headStyle.borderLeft());
        styleProperty.setBorderRight(headStyle.borderRight());
        styleProperty.setBorderTop(headStyle.borderTop());
        styleProperty.setBorderBottom(headStyle.borderBottom());
        styleProperty.setLeftBorderColor(headStyle.leftBorderColor());
        styleProperty.setRightBorderColor(headStyle.rightBorderColor());
        styleProperty.setTopBorderColor(headStyle.topBorderColor());
        styleProperty.setBottomBorderColor(headStyle.bottomBorderColor());
        styleProperty.setFillPatternType(headStyle.fillPatternType());
        styleProperty.setFillBackgroundColor(headStyle.fillBackgroundColor());
        styleProperty.setFillForegroundColor(headStyle.fillForegroundColor());
        styleProperty.setShrinkToFit(headStyle.shrinkToFit());
        return styleProperty;
    }

    public static StyleProperty build(ContentStyle contentStyle) {
        if (contentStyle == null) {
            return null;
        }
        StyleProperty styleProperty = new StyleProperty();
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setIndex(contentStyle.dataFormat());
        styleProperty.setDataFormatData(dataFormatData);
        styleProperty.setHidden(contentStyle.hidden());
        styleProperty.setLocked(contentStyle.locked());
        styleProperty.setQuotePrefix(contentStyle.quotePrefix());
        styleProperty.setHorizontalAlignment(contentStyle.horizontalAlignment());
        styleProperty.setWrapped(contentStyle.wrapped());
        styleProperty.setVerticalAlignment(contentStyle.verticalAlignment());
        styleProperty.setRotation(contentStyle.rotation());
        styleProperty.setIndent(contentStyle.indent());
        styleProperty.setBorderLeft(contentStyle.borderLeft());
        styleProperty.setBorderRight(contentStyle.borderRight());
        styleProperty.setBorderTop(contentStyle.borderTop());
        styleProperty.setBorderBottom(contentStyle.borderBottom());
        styleProperty.setLeftBorderColor(contentStyle.leftBorderColor());
        styleProperty.setRightBorderColor(contentStyle.rightBorderColor());
        styleProperty.setTopBorderColor(contentStyle.topBorderColor());
        styleProperty.setBottomBorderColor(contentStyle.bottomBorderColor());
        styleProperty.setFillPatternType(contentStyle.fillPatternType());
        styleProperty.setFillBackgroundColor(contentStyle.fillBackgroundColor());
        styleProperty.setFillForegroundColor(contentStyle.fillForegroundColor());
        styleProperty.setShrinkToFit(contentStyle.shrinkToFit());
        return styleProperty;
    }
}
