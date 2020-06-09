/*
 * ==================================================================== Licensed to the Apache Software Foundation (ASF)
 * under one or more contributor license agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * 2012 - Alfresco Software, Ltd. Alfresco Software has modified source of this file The details of changes as svn diff
 * can be found in svn at location root/projects/3rd-party/src
 * ====================================================================
 */
package com.alibaba.excel.metadata.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
import org.apache.poi.ss.usermodel.FractionFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.util.DateUtils;

/**
 * Written with reference to {@link org.apache.poi.ss.usermodel.DataFormatter}.Made some optimizations for date
 * conversion.
 * <p>
 * This is a non-thread-safe class.
 *
 * @author Jiaju Zhuang
 */
public class DataFormatter {
    /** For logging any problems we find */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataFormatter.class);
    private static final String defaultFractionWholePartFormat = "#";
    private static final String defaultFractionFractionPartFormat = "#/##";
    /** Pattern to find a number format: "0" or "#" */
    private static final Pattern numPattern = Pattern.compile("[0#]+");

    /** Pattern to find days of week as text "ddd...." */
    private static final Pattern daysAsText = Pattern.compile("([d]{3,})", Pattern.CASE_INSENSITIVE);

    /** Pattern to find "AM/PM" marker */
    private static final Pattern amPmPattern =
        Pattern.compile("(([AP])[M/P]*)|(([上下])[午/下]*)", Pattern.CASE_INSENSITIVE);

    /** Pattern to find formats with condition ranges e.g. [>=100] */
    private static final Pattern rangeConditionalPattern =
        Pattern.compile(".*\\[\\s*(>|>=|<|<=|=)\\s*[0-9]*\\.*[0-9].*");

    /**
     * A regex to find locale patterns like [$$-1009] and [$?-452]. Note that we don't currently process these into
     * locales
     */
    private static final Pattern localePatternGroup = Pattern.compile("(\\[\\$[^-\\]]*-[0-9A-Z]+])");

    /**
     * A regex to match the colour formattings rules. Allowed colours are: Black, Blue, Cyan, Green, Magenta, Red,
     * White, Yellow, "Color n" (1<=n<=56)
     */
    private static final Pattern colorPattern = Pattern.compile(
        "(\\[BLACK])|(\\[BLUE])|(\\[CYAN])|(\\[GREEN])|" + "(\\[MAGENTA])|(\\[RED])|(\\[WHITE])|(\\[YELLOW])|"
            + "(\\[COLOR\\s*\\d])|(\\[COLOR\\s*[0-5]\\d])|(\\[DBNum(1|2|3)])|(\\[\\$-\\d{0,3}])",
        Pattern.CASE_INSENSITIVE);

    /**
     * A regex to identify a fraction pattern. This requires that replaceAll("\\?", "#") has already been called
     */
    private static final Pattern fractionPattern = Pattern.compile("(?:([#\\d]+)\\s+)?(#+)\\s*/\\s*([#\\d]+)");

    /**
     * A regex to strip junk out of fraction formats
     */
    private static final Pattern fractionStripper = Pattern.compile("(\"[^\"]*\")|([^ ?#\\d/]+)");

    /**
     * A regex to detect if an alternate grouping character is used in a numeric format
     */
    private static final Pattern alternateGrouping = Pattern.compile("([#0]([^.#0])[#0]{3})");

    /**
     * Cells formatted with a date or time format and which contain invalid date or time values show 255 pound signs
     * ("#").
     */
    private static final String invalidDateTimeString;
    static {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 255; i++)
            buf.append('#');
        invalidDateTimeString = buf.toString();
    }

    /**
     * The decimal symbols of the locale used for formatting values.
     */
    private DecimalFormatSymbols decimalSymbols;

    /**
     * The date symbols of the locale used for formatting values.
     */
    private DateFormatSymbols dateSymbols;
    /** A default format to use when a number pattern cannot be parsed. */
    private Format defaultNumFormat;
    /**
     * A map to cache formats. Map<String,Format> formats
     */
    private final Map<String, Format> formats = new HashMap<String, Format>();

    /** stores the locale valid it the last formatting call */
    private Locale locale;
    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     *
     * @return
     */
    private Boolean use1904windowing;
    /**
     * Whether to use scientific Format.
     *
     * default is false
     */
    private Boolean useScientificFormat;

    /**
     * Creates a formatter using the given locale.
     *
     */
    public DataFormatter(GlobalConfiguration globalConfiguration) {
        if (globalConfiguration == null) {
            this.use1904windowing = Boolean.FALSE;
            this.locale = Locale.getDefault();
            this.useScientificFormat = Boolean.FALSE;
        } else {
            this.use1904windowing =
                globalConfiguration.getUse1904windowing() != null ? globalConfiguration.getUse1904windowing()
                    : Boolean.FALSE;
            this.locale =
                globalConfiguration.getLocale() != null ? globalConfiguration.getLocale() : Locale.getDefault();
            this.useScientificFormat =
                globalConfiguration.getUseScientificFormat() != null ? globalConfiguration.getUseScientificFormat()
                    : Boolean.FALSE;
        }
        this.dateSymbols = DateFormatSymbols.getInstance(this.locale);
        this.decimalSymbols = DecimalFormatSymbols.getInstance(this.locale);
    }

    private Format getFormat(Integer dataFormat, String dataFormatString) {
        // See if we already have it cached
        Format format = formats.get(dataFormatString);
        if (format != null) {
            return format;
        }
        // Is it one of the special built in types, General or @?
        if ("General".equalsIgnoreCase(dataFormatString) || "@".equals(dataFormatString)) {
            format = getDefaultFormat();
            addFormat(dataFormatString, format);
            return format;
        }

        // Build a formatter, and cache it
        format = createFormat(dataFormat, dataFormatString);
        addFormat(dataFormatString, format);
        return format;
    }

    private Format createFormat(Integer dataFormat, String dataFormatString) {
        String formatStr = dataFormatString;

        Format format = checkSpecialConverter(formatStr);
        if (format != null) {
            return format;
        }

        // Remove colour formatting if present
        Matcher colourM = colorPattern.matcher(formatStr);
        while (colourM.find()) {
            String colour = colourM.group();

            // Paranoid replacement...
            int at = formatStr.indexOf(colour);
            if (at == -1)
                break;
            String nFormatStr = formatStr.substring(0, at) + formatStr.substring(at + colour.length());
            if (nFormatStr.equals(formatStr))
                break;

            // Try again in case there's multiple
            formatStr = nFormatStr;
            colourM = colorPattern.matcher(formatStr);
        }

        // Strip off the locale information, we use an instance-wide locale for everything
        Matcher m = localePatternGroup.matcher(formatStr);
        while (m.find()) {
            String match = m.group();
            String symbol = match.substring(match.indexOf('$') + 1, match.indexOf('-'));
            if (symbol.indexOf('$') > -1) {
                symbol = symbol.substring(0, symbol.indexOf('$')) + '\\' + symbol.substring(symbol.indexOf('$'));
            }
            formatStr = m.replaceAll(symbol);
            m = localePatternGroup.matcher(formatStr);
        }

        // Check for special cases
        if (formatStr == null || formatStr.trim().length() == 0) {
            return getDefaultFormat();
        }

        if ("General".equalsIgnoreCase(formatStr) || "@".equals(formatStr)) {
            return getDefaultFormat();
        }

        if (DateUtils.isADateFormat(dataFormat, formatStr)) {
            return createDateFormat(formatStr);
        }
        // Excel supports fractions in format strings, which Java doesn't
        if (formatStr.contains("#/") || formatStr.contains("?/")) {
            String[] chunks = formatStr.split(";");
            for (String chunk1 : chunks) {
                String chunk = chunk1.replaceAll("\\?", "#");
                Matcher matcher = fractionStripper.matcher(chunk);
                chunk = matcher.replaceAll(" ");
                chunk = chunk.replaceAll(" +", " ");
                Matcher fractionMatcher = fractionPattern.matcher(chunk);
                // take the first match
                if (fractionMatcher.find()) {
                    String wholePart = (fractionMatcher.group(1) == null) ? "" : defaultFractionWholePartFormat;
                    return new FractionFormat(wholePart, fractionMatcher.group(3));
                }
            }

            // Strip custom text in quotes and escaped characters for now as it can cause performance problems in
            // fractions.
            // String strippedFormatStr = formatStr.replaceAll("\\\\ ", " ").replaceAll("\\\\.",
            // "").replaceAll("\"[^\"]*\"", " ").replaceAll("\\?", "#");
            return new FractionFormat(defaultFractionWholePartFormat, defaultFractionFractionPartFormat);
        }

        if (numPattern.matcher(formatStr).find()) {
            return createNumberFormat(formatStr);
        }
        return getDefaultFormat();
    }

    private Format checkSpecialConverter(String dataFormatString) {
        if ("00000\\-0000".equals(dataFormatString) || "00000-0000".equals(dataFormatString)) {
            return new ZipPlusFourFormat();
        }
        if ("[<=9999999]###\\-####;\\(###\\)\\ ###\\-####".equals(dataFormatString)
            || "[<=9999999]###-####;(###) ###-####".equals(dataFormatString)
            || "###\\-####;\\(###\\)\\ ###\\-####".equals(dataFormatString)
            || "###-####;(###) ###-####".equals(dataFormatString)) {
            return new PhoneFormat();
        }
        if ("000\\-00\\-0000".equals(dataFormatString) || "000-00-0000".equals(dataFormatString)) {
            return new SSNFormat();
        }
        return null;
    }

    private Format createDateFormat(String pFormatStr) {
        String formatStr = pFormatStr;
        formatStr = formatStr.replaceAll("\\\\-", "-");
        formatStr = formatStr.replaceAll("\\\\,", ",");
        formatStr = formatStr.replaceAll("\\\\\\.", "."); // . is a special regexp char
        formatStr = formatStr.replaceAll("\\\\ ", " ");
        formatStr = formatStr.replaceAll("\\\\/", "/"); // weird: m\\/d\\/yyyy
        formatStr = formatStr.replaceAll(";@", "");
        formatStr = formatStr.replaceAll("\"/\"", "/"); // "/" is escaped for no reason in: mm"/"dd"/"yyyy
        formatStr = formatStr.replace("\"\"", "'"); // replace Excel quoting with Java style quoting
        formatStr = formatStr.replaceAll("\\\\T", "'T'"); // Quote the T is iso8601 style dates
        formatStr = formatStr.replace("\"", "");

        boolean hasAmPm = false;
        Matcher amPmMatcher = amPmPattern.matcher(formatStr);
        while (amPmMatcher.find()) {
            formatStr = amPmMatcher.replaceAll("@");
            hasAmPm = true;
            amPmMatcher = amPmPattern.matcher(formatStr);
        }
        formatStr = formatStr.replaceAll("@", "a");

        Matcher dateMatcher = daysAsText.matcher(formatStr);
        if (dateMatcher.find()) {
            String match = dateMatcher.group(0).toUpperCase(Locale.ROOT).replaceAll("D", "E");
            formatStr = dateMatcher.replaceAll(match);
        }

        // Convert excel date format to SimpleDateFormat.
        // Excel uses lower and upper case 'm' for both minutes and months.
        // From Excel help:
        /*
            The "m" or "mm" code must appear immediately after the "h" or"hh"
            code or immediately before the "ss" code; otherwise, Microsoft
            Excel displays the month instead of minutes."
          */
        StringBuilder sb = new StringBuilder();
        char[] chars = formatStr.toCharArray();
        boolean mIsMonth = true;
        List<Integer> ms = new ArrayList<Integer>();
        boolean isElapsed = false;
        for (int j = 0; j < chars.length; j++) {
            char c = chars[j];
            if (c == '\'') {
                sb.append(c);
                j++;

                // skip until the next quote
                while (j < chars.length) {
                    c = chars[j];
                    sb.append(c);
                    if (c == '\'') {
                        break;
                    }
                    j++;
                }
            } else if (c == '[' && !isElapsed) {
                isElapsed = true;
                mIsMonth = false;
                sb.append(c);
            } else if (c == ']' && isElapsed) {
                isElapsed = false;
                sb.append(c);
            } else if (isElapsed) {
                if (c == 'h' || c == 'H') {
                    sb.append('H');
                } else if (c == 'm' || c == 'M') {
                    sb.append('m');
                } else if (c == 's' || c == 'S') {
                    sb.append('s');
                } else {
                    sb.append(c);
                }
            } else if (c == 'h' || c == 'H') {
                mIsMonth = false;
                if (hasAmPm) {
                    sb.append('h');
                } else {
                    sb.append('H');
                }
            } else if (c == 'm' || c == 'M') {
                if (mIsMonth) {
                    sb.append('M');
                    ms.add(Integer.valueOf(sb.length() - 1));
                } else {
                    sb.append('m');
                }
            } else if (c == 's' || c == 'S') {
                sb.append('s');
                // if 'M' precedes 's' it should be minutes ('m')
                for (int index : ms) {
                    if (sb.charAt(index) == 'M') {
                        sb.replace(index, index + 1, "m");
                    }
                }
                mIsMonth = true;
                ms.clear();
            } else if (Character.isLetter(c)) {
                mIsMonth = true;
                ms.clear();
                if (c == 'y' || c == 'Y') {
                    sb.append('y');
                } else if (c == 'd' || c == 'D') {
                    sb.append('d');
                } else {
                    sb.append(c);
                }
            } else {
                if (Character.isWhitespace(c)) {
                    ms.clear();
                }
                sb.append(c);
            }
        }
        formatStr = sb.toString();

        try {
            return new ExcelStyleDateFormatter(formatStr, dateSymbols);
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("Formatting failed for format {}, falling back", formatStr, iae);
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            return getDefaultFormat();
        }

    }

    private String cleanFormatForNumber(String formatStr) {
        StringBuilder sb = new StringBuilder(formatStr);
        // If they requested spacers, with "_",
        // remove those as we don't do spacing
        // If they requested full-column-width
        // padding, with "*", remove those too
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '_' || c == '*') {
                if (i > 0 && sb.charAt((i - 1)) == '\\') {
                    // It's escaped, don't worry
                    continue;
                }
                if (i < sb.length() - 1) {
                    // Remove the character we're supposed
                    // to match the space of / pad to the
                    // column width with
                    sb.deleteCharAt(i + 1);
                }
                // Remove the _ too
                sb.deleteCharAt(i);
                i--;
            }
        }

        // Now, handle the other aspects like
        // quoting and scientific notation
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            // remove quotes and back slashes
            if (c == '\\' || c == '"') {
                sb.deleteCharAt(i);
                i--;

                // for scientific/engineering notation
            } else if (c == '+' && i > 0 && sb.charAt(i - 1) == 'E') {
                sb.deleteCharAt(i);
                i--;
            }
        }

        return sb.toString();
    }

    private static class InternalDecimalFormatWithScale extends Format {

        private static final Pattern endsWithCommas = Pattern.compile("(,+)$");
        private BigDecimal divider;
        private static final BigDecimal ONE_THOUSAND = new BigDecimal(1000);
        private final DecimalFormat df;

        private static String trimTrailingCommas(String s) {
            return s.replaceAll(",+$", "");
        }

        public InternalDecimalFormatWithScale(String pattern, DecimalFormatSymbols symbols) {
            df = new DecimalFormat(trimTrailingCommas(pattern), symbols);
            setExcelStyleRoundingMode(df);
            Matcher endsWithCommasMatcher = endsWithCommas.matcher(pattern);
            if (endsWithCommasMatcher.find()) {
                String commas = (endsWithCommasMatcher.group(1));
                BigDecimal temp = BigDecimal.ONE;
                for (int i = 0; i < commas.length(); ++i) {
                    temp = temp.multiply(ONE_THOUSAND);
                }
                divider = temp;
            } else {
                divider = null;
            }
        }

        private Object scaleInput(Object obj) {
            if (divider != null) {
                if (obj instanceof BigDecimal) {
                    obj = ((BigDecimal)obj).divide(divider, RoundingMode.HALF_UP);
                } else if (obj instanceof Double) {
                    obj = (Double)obj / divider.doubleValue();
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            return obj;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            obj = scaleInput(obj);
            return df.format(obj, toAppendTo, pos);
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            throw new UnsupportedOperationException();
        }
    }

    private Format createNumberFormat(String formatStr) {
        String format = cleanFormatForNumber(formatStr);
        DecimalFormatSymbols symbols = decimalSymbols;

        // Do we need to change the grouping character?
        // eg for a format like #'##0 which wants 12'345 not 12,345
        Matcher agm = alternateGrouping.matcher(format);
        if (agm.find()) {
            char grouping = agm.group(2).charAt(0);
            // Only replace the grouping character if it is not the default
            // grouping character for the US locale (',') in order to enable
            // correct grouping for non-US locales.
            if (grouping != ',') {
                symbols = DecimalFormatSymbols.getInstance(locale);

                symbols.setGroupingSeparator(grouping);
                String oldPart = agm.group(1);
                String newPart = oldPart.replace(grouping, ',');
                format = format.replace(oldPart, newPart);
            }
        }

        try {
            return new InternalDecimalFormatWithScale(format, symbols);
        } catch (IllegalArgumentException iae) {
            LOGGER.debug("Formatting failed for format {}, falling back", formatStr, iae);
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            return getDefaultFormat();
        }
    }

    private Format getDefaultFormat() {
        // for numeric cells try user supplied default
        if (defaultNumFormat != null) {
            return defaultNumFormat;
            // otherwise use general format
        }
        defaultNumFormat = new ExcelGeneralNumberFormat(locale, useScientificFormat);
        return defaultNumFormat;
    }

    /**
     * Performs Excel-style date formatting, using the supplied Date and format
     */
    private String performDateFormatting(Date d, Format dateFormat) {
        Format df = dateFormat != null ? dateFormat : getDefaultFormat();
        return df.format(d);
    }

    /**
     * Returns the formatted value of an Excel date as a <tt>String</tt> based on the cell's <code>DataFormat</code>.
     * i.e. "Thursday, January 02, 2003" , "01/02/2003" , "02-Jan" , etc.
     * <p>
     * If any conditional format rules apply, the highest priority with a number format is used. If no rules contain a
     * number format, or no rules apply, the cell's style format is used. If the style does not have a format, the
     * default date format is applied.
     *
     * @param data
     *            to format
     * @param dataFormat
     * @param dataFormatString
     * @return Formatted value
     */
    private String getFormattedDateString(Double data, Integer dataFormat, String dataFormatString) {
        Format dateFormat = getFormat(dataFormat, dataFormatString);
        if (dateFormat instanceof ExcelStyleDateFormatter) {
            // Hint about the raw excel value
            ((ExcelStyleDateFormatter)dateFormat).setDateToBeFormatted(data);
        }
        return performDateFormatting(DateUtil.getJavaDate(data, use1904windowing), dateFormat);
    }

    /**
     * Returns the formatted value of an Excel number as a <tt>String</tt> based on the cell's <code>DataFormat</code>.
     * Supported formats include currency, percents, decimals, phone number, SSN, etc.: "61.54%", "$100.00", "(800)
     * 555-1234".
     * <p>
     * Format comes from either the highest priority conditional format rule with a specified format, or from the cell
     * style.
     *
     * @param data
     *            to format
     * @param dataFormat
     * @param dataFormatString
     * @return a formatted number string
     */
    private String getFormattedNumberString(Double data, Integer dataFormat, String dataFormatString) {
        Format numberFormat = getFormat(dataFormat, dataFormatString);
        String formatted = numberFormat.format(data);
        return formatted.replaceFirst("E(\\d)", "E+$1"); // to match Excel's E-notation
    }

    /**
     * Format data.
     *
     * @param data
     * @param dataFormat
     * @param dataFormatString
     * @return
     */
    public String format(Double data, Integer dataFormat, String dataFormatString) {
        if (DateUtils.isADateFormat(dataFormat, dataFormatString)) {
            return getFormattedDateString(data, dataFormat, dataFormatString);
        }
        return getFormattedNumberString(data, dataFormat, dataFormatString);
    }

    /**
     * <p>
     * Sets a default number format to be used when the Excel format cannot be parsed successfully. <b>Note:</b> This is
     * a fall back for when an error occurs while parsing an Excel number format pattern. This will not affect cells
     * with the <em>General</em> format.
     * </p>
     * <p>
     * The value that will be passed to the Format's format method (specified by <code>java.text.Format#format</code>)
     * will be a double value from a numeric cell. Therefore the code in the format method should expect a
     * <code>Number</code> value.
     * </p>
     *
     * @param format
     *            A Format instance to be used as a default
     * @see Format#format
     */
    public void setDefaultNumberFormat(Format format) {
        for (Map.Entry<String, Format> entry : formats.entrySet()) {
            if (entry.getValue() == defaultNumFormat) {
                entry.setValue(format);
            }
        }
        defaultNumFormat = format;
    }

    /**
     * Adds a new format to the available formats.
     * <p>
     * The value that will be passed to the Format's format method (specified by <code>java.text.Format#format</code>)
     * will be a double value from a numeric cell. Therefore the code in the format method should expect a
     * <code>Number</code> value.
     * </p>
     *
     * @param excelFormatStr
     *            The data format string
     * @param format
     *            A Format instance
     */
    public void addFormat(String excelFormatStr, Format format) {
        formats.put(excelFormatStr, format);
    }

    // Some custom formats

    /**
     * @return a <tt>DecimalFormat</tt> with parseIntegerOnly set <code>true</code>
     */
    private static DecimalFormat createIntegerOnlyFormat(String fmt) {
        DecimalFormatSymbols dsf = DecimalFormatSymbols.getInstance(Locale.ROOT);
        DecimalFormat result = new DecimalFormat(fmt, dsf);
        result.setParseIntegerOnly(true);
        return result;
    }

    /**
     * Enables excel style rounding mode (round half up) on the Decimal Format given.
     */
    public static void setExcelStyleRoundingMode(DecimalFormat format) {
        setExcelStyleRoundingMode(format, RoundingMode.HALF_UP);
    }

    /**
     * Enables custom rounding mode on the given Decimal Format.
     *
     * @param format
     *            DecimalFormat
     * @param roundingMode
     *            RoundingMode
     */
    public static void setExcelStyleRoundingMode(DecimalFormat format, RoundingMode roundingMode) {
        format.setRoundingMode(roundingMode);
    }

    /**
     * Format class for Excel's SSN format. This class mimics Excel's built-in SSN formatting.
     *
     * @author James May
     */
    @SuppressWarnings("serial")
    private static final class SSNFormat extends Format {
        private static final DecimalFormat df = createIntegerOnlyFormat("000000000");

        private SSNFormat() {
            // enforce singleton
        }

        /** Format a number as an SSN */
        public static String format(Number num) {
            String result = df.format(num);
            return result.substring(0, 3) + '-' + result.substring(3, 5) + '-' + result.substring(5, 9);
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(format((Number)obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return df.parseObject(source, pos);
        }
    }

    /**
     * Format class for Excel Zip + 4 format. This class mimics Excel's built-in formatting for Zip + 4.
     *
     * @author James May
     */
    @SuppressWarnings("serial")
    private static final class ZipPlusFourFormat extends Format {
        private static final DecimalFormat df = createIntegerOnlyFormat("000000000");

        private ZipPlusFourFormat() {
            // enforce singleton
        }

        /** Format a number as Zip + 4 */
        public static String format(Number num) {
            String result = df.format(num);
            return result.substring(0, 5) + '-' + result.substring(5, 9);
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(format((Number)obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return df.parseObject(source, pos);
        }
    }

    /**
     * Format class for Excel phone number format. This class mimics Excel's built-in phone number formatting.
     *
     * @author James May
     */
    @SuppressWarnings("serial")
    private static final class PhoneFormat extends Format {
        private static final DecimalFormat df = createIntegerOnlyFormat("##########");

        private PhoneFormat() {
            // enforce singleton
        }

        /** Format a number as a phone number */
        public static String format(Number num) {
            String result = df.format(num);
            StringBuilder sb = new StringBuilder();
            String seg1, seg2, seg3;
            int len = result.length();
            if (len <= 4) {
                return result;
            }

            seg3 = result.substring(len - 4, len);
            seg2 = result.substring(Math.max(0, len - 7), len - 4);
            seg1 = result.substring(Math.max(0, len - 10), Math.max(0, len - 7));

            if (seg1.trim().length() > 0) {
                sb.append('(').append(seg1).append(") ");
            }
            if (seg2.trim().length() > 0) {
                sb.append(seg2).append('-');
            }
            sb.append(seg3);
            return sb.toString();
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(format((Number)obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return df.parseObject(source, pos);
        }
    }

}
