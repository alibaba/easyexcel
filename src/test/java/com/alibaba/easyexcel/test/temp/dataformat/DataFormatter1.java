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
package com.alibaba.easyexcel.test.temp.dataformat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.formula.ConditionalFormattingEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelGeneralNumberFormat;
import org.apache.poi.ss.usermodel.ExcelNumberFormat;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.FractionFormat;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;

/**
 * DataFormatter contains methods for formatting the value stored in an Cell. This can be useful for reports and GUI
 * presentations when you need to display data exactly as it appears in Excel. Supported formats include currency, SSN,
 * percentages, decimals, dates, phone numbers, zip codes, etc.
 * <p>
 * Internally, formats will be implemented using subclasses of {@link Format} such as {@link DecimalFormat} and
 * {@link java.text.SimpleDateFormat}. Therefore the formats used by this class must obey the same pattern rules as
 * these Format subclasses. This means that only legal number pattern characters ("0", "#", ".", "," etc.) may appear in
 * number formats. Other characters can be inserted <em>before</em> or <em> after</em> the number pattern to form a
 * prefix or suffix.
 * </p>
 * <p>
 * For example the Excel pattern <code>"$#,##0.00 "USD"_);($#,##0.00 "USD")"
 * </code> will be correctly formatted as "$1,000.00 USD" or "($1,000.00 USD)". However the pattern
 * <code>"00-00-00"</code> is incorrectly formatted by DecimalFormat as "000000--". For Excel formats that are not
 * compatible with DecimalFormat, you can provide your own custom {@link Format} implementation via
 * <code>DataFormatter.addFormat(String,Format)</code>. The following custom formats are already provided by this class:
 * </p>
 *
 * <pre>
 * <ul><li>SSN "000-00-0000"</li>
 *     <li>Phone Number "(###) ###-####"</li>
 *     <li>Zip plus 4 "00000-0000"</li>
 * </ul>
 * </pre>
 * <p>
 * If the Excel format pattern cannot be parsed successfully, then a default format will be used. The default number
 * format will mimic the Excel General format: "#" for whole numbers and "#.##########" for decimal numbers. You can
 * override the default format pattern with <code>
 * DataFormatter.setDefaultNumberFormat(Format)</code>. <b>Note:</b> the default format will only be used when a Format
 * cannot be created from the cell's data format string.
 *
 * <p>
 * Note that by default formatted numeric values are trimmed. Excel formats can contain spacers and padding and the
 * default behavior is to strip them off.
 * </p>
 * <p>
 * Example:
 * </p>
 * <p>
 * Consider a numeric cell with a value <code>12.343</code> and format <code>"##.##_ "</code>. The trailing underscore
 * and space ("_ ") in the format adds a space to the end and Excel formats this cell as <code>"12.34 "</code>, but
 * <code>DataFormatter</code> trims the formatted value and returns <code>"12.34"</code>.
 * </p>
 * You can enable spaces by passing the <code>emulateCSV=true</code> flag in the <code>DateFormatter</code> cosntructor.
 * If set to true, then the output tries to conform to what you get when you take an xls or xlsx in Excel and Save As
 * CSV file:
 * <ul>
 * <li>returned values are not trimmed</li>
 * <li>Invalid dates are formatted as 255 pound signs ("#")</li>
 * <li>simulate Excel's handling of a format string of all # when the value is 0. Excel will output "",
 * <code>DataFormatter</code> will output "0".
 * </ul>
 * <p>
 * Some formats are automatically "localized" by Excel, eg show as mm/dd/yyyy when loaded in Excel in some Locales but
 * as dd/mm/yyyy in others. These are always returned in the "default" (US) format, as stored in the file. Some format
 * strings request an alternate locale, eg <code>[$-809]d/m/yy h:mm AM/PM</code> which explicitly requests UK locale.
 * These locale directives are (currently) ignored. You can use {@link DateFormatConverter} to do some of this
 * localisation if you need it.
 */
public class DataFormatter1 implements Observer {
    private static final String defaultFractionWholePartFormat = "#";
    private static final String defaultFractionFractionPartFormat = "#/##";
    /** Pattern to find a number format: "0" or "#" */
    private static final Pattern numPattern = Pattern.compile("[0#]+");

    /** Pattern to find days of week as text "ddd...." */
    private static final Pattern daysAsText = Pattern.compile("([d]{3,})", Pattern.CASE_INSENSITIVE);

    /** Pattern to find "AM/PM" marker */
    private static final Pattern amPmPattern = Pattern.compile("((A|P)[M/P]*)", Pattern.CASE_INSENSITIVE);

    /** Pattern to find formats with condition ranges e.g. [>=100] */
    private static final Pattern rangeConditionalPattern =
        Pattern.compile(".*\\[\\s*(>|>=|<|<=|=)\\s*[0-9]*\\.*[0-9].*");

    /**
     * A regex to find locale patterns like [$$-1009] and [$?-452]. Note that we don't currently process these into
     * locales
     */
    private static final Pattern localePatternGroup = Pattern.compile("(\\[\\$[^-\\]]*-[0-9A-Z]+\\])");

    /**
     * A regex to match the colour formattings rules. Allowed colours are: Black, Blue, Cyan, Green, Magenta, Red,
     * White, Yellow, "Color n" (1<=n<=56)
     */
    private static final Pattern colorPattern = Pattern.compile("(\\[BLACK\\])|(\\[BLUE\\])|(\\[CYAN\\])|(\\[GREEN\\])|"
        + "(\\[MAGENTA\\])|(\\[RED\\])|(\\[WHITE\\])|(\\[YELLOW\\])|"
        + "(\\[COLOR\\s*\\d\\])|(\\[COLOR\\s*[0-5]\\d\\])", Pattern.CASE_INSENSITIVE);

    /**
     * A regex to identify a fraction pattern. This requires that replaceAll("\\?", "#") has already been called
     */
    private static final Pattern fractionPattern = Pattern.compile("(?:([#\\d]+)\\s+)?(#+)\\s*\\/\\s*([#\\d]+)");

    /**
     * A regex to strip junk out of fraction formats
     */
    private static final Pattern fractionStripper = Pattern.compile("(\"[^\"]*\")|([^ \\?#\\d\\/]+)");

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

    /**
     * A default date format, if no date format was given
     */
    private DateFormat defaultDateformat;

    /** <em>General</em> format for numbers. */
    private Format generalNumberFormat;

    /** A default format to use when a number pattern cannot be parsed. */
    private Format defaultNumFormat;

    /**
     * A map to cache formats. Map<String,Format> formats
     */
    private final Map<String, Format> formats = new HashMap<String, Format>();

    private final boolean emulateCSV;

    /** stores the locale valid it the last formatting call */
    private Locale locale;

    /** stores if the locale should change according to {@link LocaleUtil#getUserLocale()} */
    private boolean localeIsAdapting;

    private class LocaleChangeObservable extends Observable {
        void checkForLocaleChange() {
            checkForLocaleChange(LocaleUtil.getUserLocale());
        }

        void checkForLocaleChange(Locale newLocale) {
            if (!localeIsAdapting)
                return;
            if (newLocale.equals(locale))
                return;
            super.setChanged();
            notifyObservers(newLocale);
        }
    }

    /** the Observable to notify, when the locale has been changed */
    private final LocaleChangeObservable localeChangedObservable = new LocaleChangeObservable();

    /** For logging any problems we find */
    private static POILogger logger = POILogFactory.getLogger(DataFormatter.class);

    /**
     * Creates a formatter using the {@link Locale#getDefault() default locale}.
     */
    public DataFormatter1() {
        this(false);
    }

    /**
     * Creates a formatter using the {@link Locale#getDefault() default locale}.
     *
     * @param emulateCSV
     *            whether to emulate CSV output.
     */
    public DataFormatter1(boolean emulateCSV) {
        this(LocaleUtil.getUserLocale(), true, emulateCSV);
    }

    /**
     * Creates a formatter using the given locale.
     */
    public DataFormatter1(Locale locale) {
        this(locale, false);
    }

    /**
     * Creates a formatter using the given locale.
     *
     * @param emulateCSV
     *            whether to emulate CSV output.
     */
    public DataFormatter1(Locale locale, boolean emulateCSV) {
        this(locale, false, emulateCSV);
    }

    /**
     * Creates a formatter using the given locale.
     *
     * @param localeIsAdapting
     *            (true only if locale is not user-specified)
     * @param emulateCSV
     *            whether to emulate CSV output.
     */
    private DataFormatter1(Locale locale, boolean localeIsAdapting, boolean emulateCSV) {
        this.localeIsAdapting = true;
        localeChangedObservable.addObserver(this);
        // localeIsAdapting must be true prior to this first checkForLocaleChange call.
        localeChangedObservable.checkForLocaleChange(locale);
        // set localeIsAdapting so subsequent checks perform correctly
        // (whether a specific locale was provided to this DataFormatter or DataFormatter should
        // adapt to the current user locale as the locale changes)
        this.localeIsAdapting = localeIsAdapting;
        this.emulateCSV = emulateCSV;
    }

    /**
     * Return a Format for the given cell if one exists, otherwise try to create one. This method will return
     * <code>null</code> if the any of the following is true:
     * <ul>
     * <li>the cell's style is null</li>
     * <li>the style's data format string is null or empty</li>
     * <li>the format string cannot be recognized as either a number or date</li>
     * </ul>
     *
     * @param cell
     *            The cell to retrieve a Format for
     * @return A Format for the format String
     */
    private Format getFormat(Cell cell, ConditionalFormattingEvaluator cfEvaluator) {
        if (cell == null)
            return null;

        ExcelNumberFormat numFmt = ExcelNumberFormat.from(cell, cfEvaluator);

        if (numFmt == null) {
            return null;
        }

        int formatIndex = numFmt.getIdx();
        String formatStr = numFmt.getFormat();
        if (formatStr == null || formatStr.trim().length() == 0) {
            return null;
        }
        return getFormat(cell.getNumericCellValue(), formatIndex, formatStr);
    }

    private Format getFormat(double cellValue, int formatIndex, String formatStrIn) {
        localeChangedObservable.checkForLocaleChange();

        // Might be better to separate out the n p and z formats, falling back to p when n and z are not set.
        // That however would require other code to be re factored.
        // String[] formatBits = formatStrIn.split(";");
        // int i = cellValue > 0.0 ? 0 : cellValue < 0.0 ? 1 : 2;
        // String formatStr = (i < formatBits.length) ? formatBits[i] : formatBits[0];

        String formatStr = formatStrIn;

        // Excel supports 2+ part conditional data formats, eg positive/negative/zero,
        // or (>1000),(>0),(0),(negative). As Java doesn't handle these kinds
        // of different formats for different ranges, just +ve/-ve, we need to
        // handle these ourselves in a special way.
        // For now, if we detect 2+ parts, we call out to CellFormat to handle it
        // TODO Going forward, we should really merge the logic between the two classes
        if (formatStr.contains(";") && (formatStr.indexOf(';') != formatStr.lastIndexOf(';')
            || rangeConditionalPattern.matcher(formatStr).matches())) {
            try {
                // Ask CellFormat to get a formatter for it
                CellFormat cfmt = CellFormat.getInstance(locale, formatStr);
                // CellFormat requires callers to identify date vs not, so do so
                Object cellValueO = Double.valueOf(cellValue);
                if (DateUtil.isADateFormat(formatIndex, formatStr) &&
                // don't try to handle Date value 0, let a 3 or 4-part format take care of it
                    ((Double)cellValueO).doubleValue() != 0.0) {
                    cellValueO = DateUtil.getJavaDate(cellValue);
                }
                // Wrap and return (non-cachable - CellFormat does that)
                return new CellFormatResultWrapper(cfmt.apply(cellValueO));
            } catch (Exception e) {
                logger.log(POILogger.WARN, "Formatting failed for format " + formatStr + ", falling back", e);
            }
        }

        // Excel's # with value 0 will output empty where Java will output 0. This hack removes the # from the format.
        if (emulateCSV && cellValue == 0.0 && formatStr.contains("#") && !formatStr.contains("0")) {
            formatStr = formatStr.replaceAll("#", "");
        }

        // See if we already have it cached
        Format format = formats.get(formatStr);
        if (format != null) {
            return format;
        }

        // Is it one of the special built in types, General or @?
        if ("General".equalsIgnoreCase(formatStr) || "@".equals(formatStr)) {
            return generalNumberFormat;
        }

        // Build a formatter, and cache it
        format = createFormat(cellValue, formatIndex, formatStr);
        formats.put(formatStr, format);
        return format;
    }

    /**
     * Create and return a Format based on the format string from a cell's style. If the pattern cannot be parsed,
     * return a default pattern.
     *
     * @param cell
     *            The Excel cell
     * @return A Format representing the excel format. May return null.
     */
    public Format createFormat(Cell cell) {

        int formatIndex = cell.getCellStyle().getDataFormat();
        String formatStr = cell.getCellStyle().getDataFormatString();
        return createFormat(cell.getNumericCellValue(), formatIndex, formatStr);
    }

    private Format createFormat(double cellValue, int formatIndex, String sFormat) {
        localeChangedObservable.checkForLocaleChange();

        String formatStr = sFormat;

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
                symbol = symbol.substring(0, symbol.indexOf('$')) + '\\'
                    + symbol.substring(symbol.indexOf('$'), symbol.length());
            }
            formatStr = m.replaceAll(symbol);
            m = localePatternGroup.matcher(formatStr);
        }

        // Check for special cases
        if (formatStr == null || formatStr.trim().length() == 0) {
            return getDefaultFormat(cellValue);
        }

        if ("General".equalsIgnoreCase(formatStr) || "@".equals(formatStr)) {
            return generalNumberFormat;
        }

        if ("".equals("")||(DateUtil.isADateFormat(formatIndex, formatStr) && DateUtil.isValidExcelDate(cellValue))) {
            return createDateFormat(formatStr, cellValue);
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
            // System.out.println("formatStr: "+strippedFormatStr);
            return new FractionFormat(defaultFractionWholePartFormat, defaultFractionFractionPartFormat);
        }

        if (numPattern.matcher(formatStr).find()) {
            return createNumberFormat(formatStr, cellValue);
        }

        if (emulateCSV) {
            return new ConstantStringFormat(cleanFormatForNumber(formatStr));
        }
        // TODO - when does this occur?
        return null;
    }

    private Format createDateFormat(String pFormatStr, double cellValue) {
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
            logger.log(POILogger.DEBUG, "Formatting failed for format " + formatStr + ", falling back", iae);
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            return getDefaultFormat(cellValue);
        }

    }

    private String cleanFormatForNumber(String formatStr) {
        StringBuilder sb = new StringBuilder(formatStr);

        if (emulateCSV) {
            // Requested spacers with "_" are replaced by a single space.
            // Full-column-width padding "*" are removed.
            // Not processing fractions at this time. Replace ? with space.
            // This matches CSV output.
            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == '_' || c == '*' || c == '?') {
                    if (i > 0 && sb.charAt((i - 1)) == '\\') {
                        // It's escaped, don't worry
                        continue;
                    }
                    if (c == '?') {
                        sb.setCharAt(i, ' ');
                    } else if (i < sb.length() - 1) {
                        // Remove the character we're supposed
                        // to match the space of / pad to the
                        // column width with
                        if (c == '_') {
                            sb.setCharAt(i + 1, ' ');
                        } else {
                            sb.deleteCharAt(i + 1);
                        }
                        // Remove the character too
                        sb.deleteCharAt(i);
                        i--;
                    }
                }
            }
        } else {
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

        private static final String trimTrailingCommas(String s) {
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

    private Format createNumberFormat(String formatStr, double cellValue) {
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
            logger.log(POILogger.DEBUG, "Formatting failed for format " + formatStr + ", falling back", iae);
            // the pattern could not be parsed correctly,
            // so fall back to the default number format
            return getDefaultFormat(cellValue);
        }
    }

    /**
     * Returns a default format for a cell.
     *
     * @param cell
     *            The cell
     * @return a default format
     */
    public Format getDefaultFormat(Cell cell) {
        return getDefaultFormat(cell.getNumericCellValue());
    }

    private Format getDefaultFormat(double cellValue) {
        localeChangedObservable.checkForLocaleChange();

        // for numeric cells try user supplied default
        if (defaultNumFormat != null) {
            return defaultNumFormat;

            // otherwise use general format
        }
        return generalNumberFormat;
    }

    /**
     * Performs Excel-style date formatting, using the supplied Date and format
     */
    private String performDateFormatting(Date d, Format dateFormat) {
        return (dateFormat != null ? dateFormat : defaultDateformat).format(d);
    }

    /**
     * Returns the formatted value of an Excel date as a <tt>String</tt> based on the cell's <code>DataFormat</code>.
     * i.e. "Thursday, January 02, 2003" , "01/02/2003" , "02-Jan" , etc.
     * <p>
     * If any conditional format rules apply, the highest priority with a number format is used. If no rules contain a
     * number format, or no rules apply, the cell's style format is used. If the style does not have a format, the
     * default date format is applied.
     *
     * @param cell
     *            to format
     * @param cfEvaluator
     *            ConditionalFormattingEvaluator (if available)
     * @return Formatted value
     */
    private String getFormattedDateString(Cell cell, ConditionalFormattingEvaluator cfEvaluator) {
        Format dateFormat = getFormat(cell, cfEvaluator);
        if (dateFormat instanceof ExcelStyleDateFormatter) {
            // Hint about the raw excel value
            ((ExcelStyleDateFormatter)dateFormat).setDateToBeFormatted(cell.getNumericCellValue());
        }
        Date d = cell.getDateCellValue();
        return performDateFormatting(d, dateFormat);
    }

    /**
     * Returns the formatted value of an Excel number as a <tt>String</tt> based on the cell's <code>DataFormat</code>.
     * Supported formats include currency, percents, decimals, phone number, SSN, etc.: "61.54%", "$100.00", "(800)
     * 555-1234".
     * <p>
     * Format comes from either the highest priority conditional format rule with a specified format, or from the cell
     * style.
     *
     * @param cell
     *            The cell
     * @param cfEvaluator
     *            if available, or null
     * @return a formatted number string
     */
    private String getFormattedNumberString(Cell cell, ConditionalFormattingEvaluator cfEvaluator) {

        Format numberFormat = getFormat(cell, cfEvaluator);
        double d = cell.getNumericCellValue();
        if (numberFormat == null) {
            return String.valueOf(d);
        }
        String formatted = numberFormat.format(new Double(d));
        return formatted.replaceFirst("E(\\d)", "E+$1"); // to match Excel's E-notation
    }

    /**
     * Formats the given raw cell value, based on the supplied format index and string, according to excel style rules.
     *
     * @see #formatCellValue(Cell)
     */
    public String formatRawCellContents(double value, int formatIndex, String formatString) {
        return formatRawCellContents(value, formatIndex, formatString, false);
    }

    /**
     * Formats the given raw cell value, based on the supplied format index and string, according to excel style rules.
     *
     * @see #formatCellValue(Cell)
     */
    public String formatRawCellContents(double value, int formatIndex, String formatString, boolean use1904Windowing) {
        localeChangedObservable.checkForLocaleChange();

        // Is it a date?
        if (DateUtil.isADateFormat(formatIndex, formatString)) {
            if (DateUtil.isValidExcelDate(value)) {
                Format dateFormat = getFormat(value, formatIndex, formatString);
                if (dateFormat instanceof ExcelStyleDateFormatter) {
                    // Hint about the raw excel value
                    ((ExcelStyleDateFormatter)dateFormat).setDateToBeFormatted(value);
                }
                Date d = DateUtil.getJavaDate(value, use1904Windowing);
                return performDateFormatting(d, dateFormat);
            }
            // RK: Invalid dates are 255 #s.
            if (emulateCSV) {
                return invalidDateTimeString;
            }
        }

        // else Number
        Format numberFormat = getFormat(value, formatIndex, formatString);
        if (numberFormat == null) {
            return String.valueOf(value);
        }

        // When formatting 'value', double to text to BigDecimal produces more
        // accurate results than double to Double in JDK8 (as compared to
        // previous versions). However, if the value contains E notation, this
        // would expand the values, which we do not want, so revert to
        // original method.
        String result;
        final String textValue = NumberToTextConverter.toText(value);
        if (textValue.indexOf('E') > -1) {
            result = numberFormat.format(new Double(value));
        } else {
            result = numberFormat.format(new BigDecimal(textValue));
        }
        // Complete scientific notation by adding the missing +.
        if (result.indexOf('E') > -1 && !result.contains("E-")) {
            result = result.replaceFirst("E", "E+");
        }
        return result;
    }

    /**
     * <p>
     * Returns the formatted value of a cell as a <tt>String</tt> regardless of the cell type. If the Excel format
     * pattern cannot be parsed then the cell value will be formatted using a default format.
     * </p>
     * <p>
     * When passed a null or blank cell, this method will return an empty String (""). Formulas in formula type cells
     * will not be evaluated.
     * </p>
     *
     * @param cell
     *            The cell
     * @return the formatted cell value as a String
     */
    public String formatCellValue(Cell cell) {
        return formatCellValue(cell, null);
    }

    /**
     * <p>
     * Returns the formatted value of a cell as a <tt>String</tt> regardless of the cell type. If the Excel number
     * format pattern cannot be parsed then the cell value will be formatted using a default format.
     * </p>
     * <p>
     * When passed a null or blank cell, this method will return an empty String (""). Formula cells will be evaluated
     * using the given {@link FormulaEvaluator} if the evaluator is non-null. If the evaluator is null, then the formula
     * String will be returned. The caller is responsible for setting the currentRow on the evaluator
     * </p>
     *
     * @param cell
     *            The cell (can be null)
     * @param evaluator
     *            The FormulaEvaluator (can be null)
     * @return a string value of the cell
     */
    public String formatCellValue(Cell cell, FormulaEvaluator evaluator) {
        return formatCellValue(cell, evaluator, null);
    }

    /**
     * <p>
     * Returns the formatted value of a cell as a <tt>String</tt> regardless of the cell type. If the Excel number
     * format pattern cannot be parsed then the cell value will be formatted using a default format.
     * </p>
     * <p>
     * When passed a null or blank cell, this method will return an empty String (""). Formula cells will be evaluated
     * using the given {@link FormulaEvaluator} if the evaluator is non-null. If the evaluator is null, then the formula
     * String will be returned. The caller is responsible for setting the currentRow on the evaluator
     * </p>
     * <p>
     * When a ConditionalFormattingEvaluator is present, it is checked first to see if there is a number format to
     * apply. If multiple rules apply, the last one is used. If no ConditionalFormattingEvaluator is present, no rules
     * apply, or the applied rules do not define a format, the cell's style format is used.
     * </p>
     * <p>
     * The two evaluators should be from the same context, to avoid inconsistencies in cached values.
     * </p>
     *
     * @param cell
     *            The cell (can be null)
     * @param evaluator
     *            The FormulaEvaluator (can be null)
     * @param cfEvaluator
     *            ConditionalFormattingEvaluator (can be null)
     * @return a string value of the cell
     */
    public String formatCellValue(Cell cell, FormulaEvaluator evaluator, ConditionalFormattingEvaluator cfEvaluator) {
        localeChangedObservable.checkForLocaleChange();

        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellTypeEnum();
        if (cellType == CellType.FORMULA) {
            if (evaluator == null) {
                return cell.getCellFormula();
            }
            cellType = evaluator.evaluateFormulaCellEnum(cell);
        }
        switch (cellType) {
            case NUMERIC:

//                if (DateUtil.isCellDateFormatted(cell, cfEvaluator)) {
                    return getFormattedDateString(cell, cfEvaluator);
//                }
//                return getFormattedNumberString(cell, cfEvaluator);

            case STRING:
                return cell.getRichStringCellValue().getString();

            case BOOLEAN:
                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
            case BLANK:
                return "";
            case ERROR:
                return FormulaError.forInt(cell.getErrorCellValue()).getString();
            default:
                throw new RuntimeException("Unexpected celltype (" + cellType + ")");
        }
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
     * @see java.text.Format#format
     */
    public void setDefaultNumberFormat(Format format) {
        for (Map.Entry<String, Format> entry : formats.entrySet()) {
            if (entry.getValue() == generalNumberFormat) {
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
     * If the Locale has been changed via {@link LocaleUtil#setUserLocale(Locale)} the stored formats need to be
     * refreshed. All formats which aren't originated from DataFormatter itself, i.e. all Formats added via
     * {@link DataFormatter#addFormat(String, Format)} and {@link DataFormatter#setDefaultNumberFormat(Format)}, need to
     * be added again. To notify callers, the returned {@link Observable} should be used. The Object in
     * {@link Observer#update(Observable, Object)} is the new Locale.
     *
     * @return the listener object, where callers can register themselves
     */
    public Observable getLocaleChangedObservable() {
        return localeChangedObservable;
    }

    /**
     * Update formats when locale has been changed
     *
     * @param observable
     *            usually this is our own Observable instance
     * @param localeObj
     *            only reacts on Locale objects
     */
    public void update(Observable observable, Object localeObj) {
        if (!(localeObj instanceof Locale))
            return;
        Locale newLocale = (Locale)localeObj;
        if (!localeIsAdapting || newLocale.equals(locale))
            return;

        locale = newLocale;

        dateSymbols = DateFormatSymbols.getInstance(locale);
        decimalSymbols = DecimalFormatSymbols.getInstance(locale);
        generalNumberFormat = new ExcelGeneralNumberFormat(locale);

        // taken from Date.toString()
        defaultDateformat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", dateSymbols);
        defaultDateformat.setTimeZone(LocaleUtil.getUserTimeZone());

        // init built-in formats

        formats.clear();
        Format zipFormat = ZipPlusFourFormat.instance;
        addFormat("00000\\-0000", zipFormat);
        addFormat("00000-0000", zipFormat);

        Format phoneFormat = PhoneFormat.instance;
        // allow for format string variations
        addFormat("[<=9999999]###\\-####;\\(###\\)\\ ###\\-####", phoneFormat);
        addFormat("[<=9999999]###-####;(###) ###-####", phoneFormat);
        addFormat("###\\-####;\\(###\\)\\ ###\\-####", phoneFormat);
        addFormat("###-####;(###) ###-####", phoneFormat);

        Format ssnFormat = SSNFormat.instance;
        addFormat("000\\-00\\-0000", ssnFormat);
        addFormat("000-00-0000", ssnFormat);
    }

    /**
     * Format class for Excel's SSN format. This class mimics Excel's built-in SSN formatting.
     *
     * @author James May
     */
    @SuppressWarnings("serial")
    private static final class SSNFormat extends Format {
        public static final Format instance = new SSNFormat();
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
        public static final Format instance = new ZipPlusFourFormat();
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
        public static final Format instance = new PhoneFormat();
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

    /**
     * Format class that does nothing and always returns a constant string.
     *
     * This format is used to simulate Excel's handling of a format string of all # when the value is 0. Excel will
     * output "", Java will output "0".
     *
     */
    @SuppressWarnings("serial")
    private static final class ConstantStringFormat extends Format {
        private static final DecimalFormat df = createIntegerOnlyFormat("##########");
        private final String str;

        public ConstantStringFormat(String s) {
            str = s;
        }

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(str);
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return df.parseObject(source, pos);
        }
    }

    /**
     * Workaround until we merge {@link DataFormatter} with {@link CellFormat}. Constant, non-cachable wrapper around a
     * {@link CellFormatResult}
     */
    @SuppressWarnings("serial")
    private final class CellFormatResultWrapper extends Format {
        private final CellFormatResult result;

        private CellFormatResultWrapper(CellFormatResult result) {
            this.result = result;
        }

        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            if (emulateCSV) {
                return toAppendTo.append(result.text);
            } else {
                return toAppendTo.append(result.text.trim());
            }
        }

        public Object parseObject(String source, ParsePosition pos) {
            return null; // Not supported
        }
    }
}
