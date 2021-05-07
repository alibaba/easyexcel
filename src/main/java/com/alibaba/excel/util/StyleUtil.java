package com.alibaba.excel.util;

import com.alibaba.excel.constant.BuiltinFormats;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.RichTextStringData.IntervalFont;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * @author jipengfei
 */
public class StyleUtil {

    private StyleUtil() {}

    /**
     * Build  cell style
     *
     * @param workbook
     * @param writeCellStyle
     * @return
     */
    public static CellStyle buildCellStyle(Workbook workbook, WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = workbook.createCellStyle();
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
            DataFormat dataFormatCreate = workbook.createDataFormat();
            return dataFormatCreate.getFormat(dataFormatData.getFormat());
        }
        return BuiltinFormats.GENERAL;
    }

    public static Font buildFont(Workbook workbook, WriteFont writeFont) {
        Font font = workbook.createFont();
        if (writeFont == null) {
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
            richTextString.applyFont(writeWorkbookHolder.createFont(richTextStringData.getWriteFont()));
        }
        if (CollectionUtils.isNotEmpty(richTextStringData.getIntervalFontList())) {
            for (IntervalFont intervalFont : richTextStringData.getIntervalFontList()) {
                richTextString.applyFont(intervalFont.getStartIndex(), intervalFont.getEndIndex(),
                    writeWorkbookHolder.createFont(intervalFont.getWriteFont()));
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


