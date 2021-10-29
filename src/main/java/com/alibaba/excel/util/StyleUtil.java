package com.alibaba.excel.util;

import java.util.Optional;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.RichTextStringData.IntervalFont;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * @author jipengfei
 */
@Slf4j
public class StyleUtil {

    private StyleUtil() {}

    /**
     * Build  cell style
     *
     * @param workbook
     * @param originCellStyle
     * @param writeCellStyle
     * @return
     */
    public static CellStyle buildCellStyle(Workbook workbook, CellStyle originCellStyle,
        WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (originCellStyle != null) {
            cellStyle.cloneStyleFrom(originCellStyle);
        }
        if (writeCellStyle == null) {
            return cellStyle;
        }
        buildCellStyle(cellStyle, writeCellStyle);
        return cellStyle;
    }

    private static void buildCellStyle(CellStyle cellStyle, WriteCellStyle writeCellStyle) {
        if (writeCellStyle.getHidden() != null) {
            cellStyle.setHidden(writeCellStyle.getHidden());
        }
        if (writeCellStyle.getLocked() != null) {
            cellStyle.setLocked(writeCellStyle.getLocked());
        }
        if (writeCellStyle.getQuotePrefix() != null) {
            cellStyle.setQuotePrefixed(writeCellStyle.getQuotePrefix());
        }
        if (writeCellStyle.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(writeCellStyle.getHorizontalAlignment());
        }
        if (writeCellStyle.getWrapped() != null) {
            cellStyle.setWrapText(writeCellStyle.getWrapped());
        }
        if (writeCellStyle.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(writeCellStyle.getVerticalAlignment());
        }
        if (writeCellStyle.getRotation() != null) {
            cellStyle.setRotation(writeCellStyle.getRotation());
        }
        if (writeCellStyle.getIndent() != null) {
            cellStyle.setIndention(writeCellStyle.getIndent());
        }
        if (writeCellStyle.getBorderLeft() != null) {
            cellStyle.setBorderLeft(writeCellStyle.getBorderLeft());
        }
        if (writeCellStyle.getBorderRight() != null) {
            cellStyle.setBorderRight(writeCellStyle.getBorderRight());
        }
        if (writeCellStyle.getBorderTop() != null) {
            cellStyle.setBorderTop(writeCellStyle.getBorderTop());
        }
        if (writeCellStyle.getBorderBottom() != null) {
            cellStyle.setBorderBottom(writeCellStyle.getBorderBottom());
        }
        if (writeCellStyle.getLeftBorderColor() != null) {
            cellStyle.setLeftBorderColor(writeCellStyle.getLeftBorderColor());
        }
        if (writeCellStyle.getRightBorderColor() != null) {
            cellStyle.setRightBorderColor(writeCellStyle.getRightBorderColor());
        }
        if (writeCellStyle.getTopBorderColor() != null) {
            cellStyle.setTopBorderColor(writeCellStyle.getTopBorderColor());
        }
        if (writeCellStyle.getBottomBorderColor() != null) {
            cellStyle.setBottomBorderColor(writeCellStyle.getBottomBorderColor());
        }
        if (writeCellStyle.getFillPatternType() != null) {
            cellStyle.setFillPattern(writeCellStyle.getFillPatternType());
        }
        if (writeCellStyle.getFillBackgroundColor() != null) {
            cellStyle.setFillBackgroundColor(writeCellStyle.getFillBackgroundColor());
        }
        if (writeCellStyle.getFillForegroundColor() != null) {
            cellStyle.setFillForegroundColor(writeCellStyle.getFillForegroundColor());
        }
        if (writeCellStyle.getShrinkToFit() != null) {
            cellStyle.setShrinkToFit(writeCellStyle.getShrinkToFit());
        }
    }

    public static short buildDataFormat(Workbook workbook, DataFormatData dataFormatData) {
        if (dataFormatData == null) {
            return BuiltinFormats.GENERAL;
        }
        if (dataFormatData.getIndex() != null && dataFormatData.getIndex() >= 0) {
            return dataFormatData.getIndex();
        }
        if (StringUtils.isNotBlank(dataFormatData.getFormat())) {
            if (log.isDebugEnabled()) {
                log.info("create new data fromat:{}", dataFormatData);
            }
            DataFormat dataFormatCreate = workbook.createDataFormat();
            return dataFormatCreate.getFormat(dataFormatData.getFormat());
        }
        return BuiltinFormats.GENERAL;
    }

    public static Font buildFont(Workbook workbook, Font originFont, WriteFont writeFont) {
        if (log.isDebugEnabled()) {
            log.info("create new font:{},{}", writeFont, originFont);
        }
        if (writeFont == null && originFont == null) {
            return null;
        }
        Font font = createFont(workbook, originFont, writeFont);
        if (writeFont == null || font == null) {
            return font;
        }
        if (writeFont.getFontName() != null) {
            font.setFontName(writeFont.getFontName());
        }
        if (writeFont.getFontHeightInPoints() != null) {
            font.setFontHeightInPoints(writeFont.getFontHeightInPoints());
        }
        if (writeFont.getItalic() != null) {
            font.setItalic(writeFont.getItalic());
        }
        if (writeFont.getStrikeout() != null) {
            font.setStrikeout(writeFont.getStrikeout());
        }
        if (writeFont.getColor() != null) {
            font.setColor(writeFont.getColor());
        }
        if (writeFont.getTypeOffset() != null) {
            font.setTypeOffset(writeFont.getTypeOffset());
        }
        if (writeFont.getUnderline() != null) {
            font.setUnderline(writeFont.getUnderline());
        }
        if (writeFont.getCharset() != null) {
            font.setCharSet(writeFont.getCharset());
        }
        if (writeFont.getBold() != null) {
            font.setBold(writeFont.getBold());
        }
        return font;
    }

    private static Font createFont(Workbook workbook, Font originFont, WriteFont writeFont) {
        Font font = workbook.createFont();
        if (originFont == null) {
            return font;
        }
        if (originFont instanceof XSSFFont) {
            XSSFFont xssfFont = (XSSFFont)font;
            XSSFFont xssfOriginFont = ((XSSFFont)originFont);
            xssfFont.setFontName(xssfOriginFont.getFontName());
            xssfFont.setFontHeightInPoints(xssfOriginFont.getFontHeightInPoints());
            xssfFont.setItalic(xssfOriginFont.getItalic());
            xssfFont.setStrikeout(xssfOriginFont.getStrikeout());
            // Colors cannot be overwritten
            if (writeFont == null || writeFont.getColor() == null) {
                xssfFont.setColor(Optional.of(xssfOriginFont)
                    .map(XSSFFont::getXSSFColor)
                    .map(XSSFColor::getRGB)
                    .map(rgb -> new XSSFColor(rgb, null))
                    .orElse(null));
            }
            xssfFont.setTypeOffset(xssfOriginFont.getTypeOffset());
            xssfFont.setUnderline(xssfOriginFont.getUnderline());
            xssfFont.setCharSet(xssfOriginFont.getCharSet());
            xssfFont.setBold(xssfOriginFont.getBold());
            return xssfFont;
        } else if (originFont instanceof HSSFFont) {
            HSSFFont hssfFont = (HSSFFont)font;
            HSSFFont hssfOriginFont = (HSSFFont)originFont;
            hssfFont.setFontName(hssfOriginFont.getFontName());
            hssfFont.setFontHeightInPoints(hssfOriginFont.getFontHeightInPoints());
            hssfFont.setItalic(hssfOriginFont.getItalic());
            hssfFont.setStrikeout(hssfOriginFont.getStrikeout());
            hssfFont.setColor(hssfOriginFont.getColor());
            hssfFont.setTypeOffset(hssfOriginFont.getTypeOffset());
            hssfFont.setUnderline(hssfOriginFont.getUnderline());
            hssfFont.setCharSet(hssfOriginFont.getCharSet());
            hssfFont.setBold(hssfOriginFont.getBold());
            return hssfFont;
        }
        return font;
    }

    public static RichTextString buildRichTextString(WriteWorkbookHolder writeWorkbookHolder,
        RichTextStringData richTextStringData) {
        if (richTextStringData == null) {
            return null;
        }
        RichTextString richTextString;
        if (writeWorkbookHolder.getExcelType() == ExcelTypeEnum.XLSX) {
            richTextString = new XSSFRichTextString(richTextStringData.getTextString());
        } else {
            richTextString = new HSSFRichTextString(richTextStringData.getTextString());
        }
        if (richTextStringData.getWriteFont() != null) {
            richTextString.applyFont(writeWorkbookHolder.createFont(richTextStringData.getWriteFont(), null, true));
        }
        if (CollectionUtils.isNotEmpty(richTextStringData.getIntervalFontList())) {
            for (IntervalFont intervalFont : richTextStringData.getIntervalFontList()) {
                richTextString.applyFont(intervalFont.getStartIndex(), intervalFont.getEndIndex(),
                    writeWorkbookHolder.createFont(intervalFont.getWriteFont(), null, true));
            }
        }
        return richTextString;
    }

    public static HyperlinkType getHyperlinkType(HyperlinkData.HyperlinkType hyperlinkType) {
        if (hyperlinkType == null) {
            return HyperlinkType.NONE;
        }
        return hyperlinkType.getValue();
    }

    public static int getCoordinate(Integer coordinate) {
        if (coordinate == null) {
            return 0;
        }
        return Units.toEMU(coordinate);
    }

    public static int getCellCoordinate(Integer currentCoordinate, Integer absoluteCoordinate,
        Integer relativeCoordinate) {
        if (absoluteCoordinate != null && absoluteCoordinate > 0) {
            return absoluteCoordinate;
        }
        if (relativeCoordinate != null) {
            return currentCoordinate + relativeCoordinate;
        }
        return currentCoordinate;
    }

}


