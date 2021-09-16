package com.alibaba.excel.metadata.format;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.poi.ss.usermodel.DataFormatter;

/**
 * Written with reference to {@link org.apache.poi.ss.usermodel.ExcelGeneralNumberFormat }.
 * <p>
 * Supported Do not use scientific notation.
 *
 * @author JiaJu Zhuang
 **/
public class ExcelGeneralNumberFormat extends Format {

    private static final long serialVersionUID = 1L;

    private static final MathContext TO_10_SF = new MathContext(10, RoundingMode.HALF_UP);

    private final DecimalFormatSymbols decimalSymbols;
    private final DecimalFormat integerFormat;
    private final DecimalFormat decimalFormat;
    private final DecimalFormat scientificFormat;

    public ExcelGeneralNumberFormat(final Locale locale, final boolean useScientificFormat) {
        decimalSymbols = DecimalFormatSymbols.getInstance(locale);
        //  Supported Do not use scientific notation.
        if (useScientificFormat) {
            scientificFormat = new DecimalFormat("0.#####E0", decimalSymbols);
        } else {
            scientificFormat = new DecimalFormat("#", decimalSymbols);
        }
        org.apache.poi.ss.usermodel.DataFormatter.setExcelStyleRoundingMode(scientificFormat);
        integerFormat = new DecimalFormat("#", decimalSymbols);
        org.apache.poi.ss.usermodel.DataFormatter.setExcelStyleRoundingMode(integerFormat);
        decimalFormat = new DecimalFormat("#.##########", decimalSymbols);
        DataFormatter.setExcelStyleRoundingMode(decimalFormat);
    }

    @Override
    public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
        final double value;
        if (number instanceof Number) {
            value = ((Number) number).doubleValue();
            if (Double.isInfinite(value) || Double.isNaN(value)) {
                return integerFormat.format(number, toAppendTo, pos);
            }
        } else {
            // testBug54786 gets here with a date, so retain previous behaviour
            return integerFormat.format(number, toAppendTo, pos);
        }

        final double abs = Math.abs(value);
        if (abs >= 1E11 || (abs <= 1E-10 && abs > 0)) {
            return scientificFormat.format(number, toAppendTo, pos);
        } else if (Math.floor(value) == value || abs >= 1E10) {
            // integer, or integer portion uses all 11 allowed digits
            return integerFormat.format(number, toAppendTo, pos);
        }
        // Non-integers of non-scientific magnitude are formatted as "up to 11
        // numeric characters, with the decimal point counting as a numeric
        // character". We know there is a decimal point, so limit to 10 digits.
        // https://support.microsoft.com/en-us/kb/65903
        final double rounded = new BigDecimal(value).round(TO_10_SF).doubleValue();
        return decimalFormat.format(rounded, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }

}
