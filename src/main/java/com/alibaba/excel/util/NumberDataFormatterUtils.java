//package com.alibaba.excel.util;
//
//import java.text.Format;
//
//import org.apache.poi.ss.format.CellFormat;
//import org.apache.poi.ss.formula.ConditionalFormattingEvaluator;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.DateUtil;
//import org.apache.poi.ss.usermodel.ExcelNumberFormat;
//import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
//import org.apache.poi.util.POILogger;
//
///**
// * Convert number data, including date.
// *
// * @author Jiaju Zhuang
// **/
//public class NumberDataFormatterUtils {
//
//    /**
//     *
//     * @param data
//     *            Not null.
//     * @param dataFormatString
//     *            Not null.
//     * @return
//     */
//    public String format(Double data, Integer dataFormat, String dataFormatString) {
//
//        if (DateUtil.isCellDateFormatted(cell, cfEvaluator)) {
//            return getFormattedDateString(cell, cfEvaluator);
//        }
//        return getFormattedNumberString(cell, cfEvaluator);
//
//    }
//
//    private String getFormattedDateString(Double data,String dataFormatString) {
//
//
//        if (cell == null) {
//            return null;
//        }
//        Format dateFormat = getFormat(cell, cfEvaluator);
//        synchronized (dateFormat) {
//            if (dateFormat instanceof ExcelStyleDateFormatter) {
//                // Hint about the raw excel value
//                ((ExcelStyleDateFormatter)dateFormat).setDateToBeFormatted(cell.getNumericCellValue());
//            }
//            Date d = cell.getDateCellValue();
//            return performDateFormatting(d, dateFormat);
//        }
//    }
//
//
//    /**
//     * Return a Format for the given cell if one exists, otherwise try to
//     * create one. This method will return <code>null</code> if the any of the
//     * following is true:
//     * <ul>
//     * <li>the cell's style is null</li>
//     * <li>the style's data format string is null or empty</li>
//     * <li>the format string cannot be recognized as either a number or date</li>
//     * </ul>
//     *
//     * @param cell The cell to retrieve a Format for
//     * @return A Format for the format String
//     */
//    private Format getFormat(Cell cell, ConditionalFormattingEvaluator cfEvaluator) {
//        if (cell == null) return null;
//
//        ExcelNumberFormat numFmt = ExcelNumberFormat.from(cell, cfEvaluator);
//
//        if ( numFmt == null) {
//            return null;
//        }
//
//        int formatIndex = numFmt.getIdx();
//        String formatStr = numFmt.getFormat();
//        if(formatStr == null || formatStr.trim().length() == 0) {
//            return null;
//        }
//        return getFormat(cell.getNumericCellValue(), formatIndex, formatStr, isDate1904(cell));
//    }
//
//    private boolean isDate1904(Cell cell) {
//        if ( cell != null && cell.getSheet().getWorkbook() instanceof Date1904Support) {
//            return ((Date1904Support)cell.getSheet().getWorkbook()).isDate1904();
//
//        }
//        return false;
//    }
//
//    private Format getFormat(double cellValue, int formatIndex, String formatStrIn, boolean use1904Windowing) {
//        localeChangedObservable.checkForLocaleChange();
//
//        // Might be better to separate out the n p and z formats, falling back to p when n and z are not set.
//        // That however would require other code to be re factored.
//        // String[] formatBits = formatStrIn.split(";");
//        // int i = cellValue > 0.0 ? 0 : cellValue < 0.0 ? 1 : 2;
//        // String formatStr = (i < formatBits.length) ? formatBits[i] : formatBits[0];
//
//        String formatStr = formatStrIn;
//
//        // Excel supports 2+ part conditional data formats, eg positive/negative/zero,
//        //  or (>1000),(>0),(0),(negative). As Java doesn't handle these kinds
//        //  of different formats for different ranges, just +ve/-ve, we need to
//        //  handle these ourselves in a special way.
//        // For now, if we detect 2+ parts, we call out to CellFormat to handle it
//        // TODO Going forward, we should really merge the logic between the two classes
//        if (formatStr.contains(";") &&
//            (formatStr.indexOf(';') != formatStr.lastIndexOf(';')
//                || rangeConditionalPattern.matcher(formatStr).matches()
//            ) ) {
//            try {
//                // Ask CellFormat to get a formatter for it
//                CellFormat cfmt = CellFormat.getInstance(locale, formatStr);
//                // CellFormat requires callers to identify date vs not, so do so
//                Object cellValueO = Double.valueOf(cellValue);
//                if (DateUtil.isADateFormat(formatIndex, formatStr) &&
//                    // don't try to handle Date value 0, let a 3 or 4-part format take care of it
//                    ((Double)cellValueO).doubleValue() != 0.0) {
//                    cellValueO = DateUtil.getJavaDate(cellValue, use1904Windowing);
//                }
//                // Wrap and return (non-cachable - CellFormat does that)
//                return new DataFormatter.CellFormatResultWrapper( cfmt.apply(cellValueO) );
//            } catch (Exception e) {
//                logger.log(POILogger.WARN, "Formatting failed for format " + formatStr + ", falling back", e);
//            }
//        }
//
//        // Excel's # with value 0 will output empty where Java will output 0. This hack removes the # from the format.
//        if (emulateCSV && cellValue == 0.0 && formatStr.contains("#") && !formatStr.contains("0")) {
//            formatStr = formatStr.replaceAll("#", "");
//        }
//
//        // See if we already have it cached
//        Format format = formats.get(formatStr);
//        if (format != null) {
//            return format;
//        }
//
//        // Is it one of the special built in types, General or @?
//        if ("General".equalsIgnoreCase(formatStr) || "@".equals(formatStr)) {
//            return generalNumberFormat;
//        }
//
//        // Build a formatter, and cache it
//        format = createFormat(cellValue, formatIndex, formatStr);
//        formats.put(formatStr, format);
//        return format;
//    }
//
//}
