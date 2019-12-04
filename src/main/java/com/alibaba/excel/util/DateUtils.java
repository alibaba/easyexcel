package com.alibaba.excel.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.formula.ConditionalFormattingEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelNumberFormat;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;

/**
 * Date utils
 *
 * @author Jiaju Zhuang
 **/
public class DateUtils {



    public static final String DATE_FORMAT_10 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_14 = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_17 = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FORMAT_19 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_19_FORWARD_SLASH = "yyyy/MM/dd HH:mm:ss";
    private static final String MINUS = "-";

    private DateUtils() {}

    /**
     * convert string to date
     *
     * @param dateString
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString, String dateFormat) throws ParseException {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = switchDateFormat(dateString);
        }
        return new SimpleDateFormat(dateFormat).parse(dateString);
    }

    /**
     * convert string to date
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        return parseDate(dateString, switchDateFormat(dateString));
    }

    /**
     * switch date format
     *
     * @param dateString
     * @return
     */
    private static String switchDateFormat(String dateString) {
        int length = dateString.length();
        switch (length) {
            case 19:
                if (dateString.contains(MINUS)) {
                    return DATE_FORMAT_19;
                } else {
                    return DATE_FORMAT_19_FORWARD_SLASH;
                }
            case 17:
                return DATE_FORMAT_17;
            case 14:
                return DATE_FORMAT_14;
            case 10:
                return DATE_FORMAT_10;
            default:
                throw new IllegalArgumentException("can not find date format for：" + dateString);
        }
    }

    /**
     * Format date
     * <p>
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * Format date
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String format(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = DATE_FORMAT_19;
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

//
//    /**
//     * Determine if it is a date format.
//     *
//     * @param dataFormat
//     * @param dataFormatString
//     * @return
//     */
//    public static boolean isDateFormatted(Integer dataFormat, String dataFormatString) {
//        if (cell == null) {
//            return false;
//        }
//        boolean isDate = false;
//
//        double d = cell.getNumericCellValue();
//        if (DateUtil.isValidExcelDate(d)) {
//            ExcelNumberFormat nf = ExcelNumberFormat.from(cell, cfEvaluator);
//            if (nf == null) {
//                return false;
//            }
//            bDate = isADateFormat(nf);
//        }
//        return bDate;
//    }
//
//    private String getFormattedDateString(Cell cell, ConditionalFormattingEvaluator cfEvaluator) {
//        if (cell == null) {
//            return null;
//        }
//        Format dateFormat = getFormat(cell, cfEvaluator);
//        synchronized (dateFormat) {
//            if(dateFormat instanceof ExcelStyleDateFormatter) {
//                // Hint about the raw excel value
//                ((ExcelStyleDateFormatter)dateFormat).setDateToBeFormatted(
//                    cell.getNumericCellValue()
//                );
//            }
//            Date d = cell.getDateCellValue();
//            return performDateFormatting(d, dateFormat);
//        }
//    }
//
//
//    public static boolean isADateFormat(int formatIndex, String formatString) {
//        // First up, is this an internal date format?
//        if (isInternalDateFormat(formatIndex)) {
//            return true;
//        }
//        if (StringUtils.isEmpty(formatString)) {
//            return false;
//        }
//
//        // check the cache first
//        if (isCached(formatString, formatIndex)) {
//            return lastCachedResult.get();
//        }
//
//        String fs = formatString;
//        /*if (false) {
//            // Normalize the format string. The code below is equivalent
//            // to the following consecutive regexp replacements:
//
//             // Translate \- into just -, before matching
//             fs = fs.replaceAll("\\\\-","-");
//             // And \, into ,
//             fs = fs.replaceAll("\\\\,",",");
//             // And \. into .
//             fs = fs.replaceAll("\\\\\\.",".");
//             // And '\ ' into ' '
//             fs = fs.replaceAll("\\\\ "," ");
//
//             // If it end in ;@, that's some crazy dd/mm vs mm/dd
//             //  switching stuff, which we can ignore
//             fs = fs.replaceAll(";@", "");
//
//             // The code above was reworked as suggested in bug 48425:
//             // simple loop is more efficient than consecutive regexp replacements.
//        }*/
//        final int length = fs.length();
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            char c = fs.charAt(i);
//            if (i < length - 1) {
//                char nc = fs.charAt(i + 1);
//                if (c == '\\') {
//                    switch (nc) {
//                        case '-':
//                        case ',':
//                        case '.':
//                        case ' ':
//                        case '\\':
//                            // skip current '\' and continue to the next char
//                            continue;
//                    }
//                } else if (c == ';' && nc == '@') {
//                    i++;
//                    // skip ";@" duplets
//                    continue;
//                }
//            }
//            sb.append(c);
//        }
//        fs = sb.toString();
//
//        // short-circuit if it indicates elapsed time: [h], [m] or [s]
//        if (date_ptrn4.matcher(fs).matches()) {
//            cache(formatString, formatIndex, true);
//            return true;
//        }
//        // If it starts with [DBNum1] or [DBNum2] or [DBNum3]
//        // then it could be a Chinese date
//        fs = date_ptrn5.matcher(fs).replaceAll("");
//        // If it starts with [$-...], then could be a date, but
//        // who knows what that starting bit is all about
//        fs = date_ptrn1.matcher(fs).replaceAll("");
//        // If it starts with something like [Black] or [Yellow],
//        // then it could be a date
//        fs = date_ptrn2.matcher(fs).replaceAll("");
//        // You're allowed something like dd/mm/yy;[red]dd/mm/yy
//        // which would place dates before 1900/1904 in red
//        // For now, only consider the first one
//        final int separatorIndex = fs.indexOf(';');
//        if (0 < separatorIndex && separatorIndex < fs.length() - 1) {
//            fs = fs.substring(0, separatorIndex);
//        }
//
//        // Ensure it has some date letters in it
//        // (Avoids false positives on the rest of pattern 3)
//        if (!date_ptrn3a.matcher(fs).find()) {
//            return false;
//        }
//
//        // If we get here, check it's only made up, in any case, of:
//        // y m d h s - \ / , . : [ ] T
//        // optionally followed by AM/PM
//
//        boolean result = date_ptrn3b.matcher(fs).matches();
//        cache(formatString, formatIndex, result);
//        return result;
//    }
//
//    /**
//     * Given a format ID this will check whether the format represents an internal excel date format or not.
//     *
//     * @see #isADateFormat(int, java.lang.String)
//     */
//    public static boolean isInternalDateFormat(int format) {
//        switch (format) {
//            // Internal Date Formats as described on page 427 in
//            // Microsoft Excel Dev's Kit...
//            // 14-22
//            case 0x0e:
//            case 0x0f:
//            case 0x10:
//            case 0x11:
//            case 0x12:
//            case 0x13:
//            case 0x14:
//            case 0x15:
//            case 0x16:
//                // 27-36
//            case 0x1b:
//            case 0x1c:
//            case 0x1d:
//            case 0x1e:
//            case 0x1f:
//            case 0x20:
//            case 0x21:
//            case 0x22:
//            case 0x23:
//            case 0x24:
//                // 45-47
//            case 0x2d:
//            case 0x2e:
//            case 0x2f:
//                // 50-58
//            case 0x32:
//            case 0x33:
//            case 0x34:
//            case 0x35:
//            case 0x36:
//            case 0x37:
//            case 0x38:
//            case 0x39:
//            case 0x3a:
//                return true;
//        }
//        return false;
//    }
}
