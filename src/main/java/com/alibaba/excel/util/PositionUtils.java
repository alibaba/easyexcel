package com.alibaba.excel.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jipengfei
 */
public class PositionUtils {

    private static final Pattern CELL_REF_PATTERN = Pattern.compile("(\\$?[A-Z]+)?" + "(\\$?[0-9]+)?",
        Pattern.CASE_INSENSITIVE);
    private static final char SHEET_NAME_DELIMITER = '!';
    private static final char REDUNDANT_CHARACTERS = '$';

    private PositionUtils() {}

    public static int getRowByRowTagt(String rowTagt) {
        int row = 0;
        if (rowTagt != null) {
            row = Integer.parseInt(rowTagt) - 1;
        }
        return row;
    }

    public static int getRowByRowTagt(String rowTagt, Integer before) {
        int row;
        if (rowTagt != null) {
            row = Integer.parseInt(rowTagt) - 1;
            return row;
        } else {
            if (before == null) {
                before = -1;
            }
            return before + 1;
        }

    }

    public static int getRow(String currentCellIndex) {
        if (currentCellIndex != null) {
            int plingPos = currentCellIndex.lastIndexOf(SHEET_NAME_DELIMITER);
            String cell = currentCellIndex.substring(plingPos + 1).toUpperCase(Locale.ROOT);
            Matcher matcher = CELL_REF_PATTERN.matcher(cell);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid CellReference: " + currentCellIndex);
            }
            String row = matcher.group(2);
            return Integer.parseInt(row) - 1;
        }
        return -1;
    }

    private static final char ABSOLUTE_REFERENCE_MARKER = '$';
    private static final int COL_RADIX = 'Z' - 'A' + 1;


    public static int getCol(String currentCellIndex, Integer before) {
        final int length = currentCellIndex.length();

        int offset = currentCellIndex.charAt(0) == ABSOLUTE_REFERENCE_MARKER ? 1 : 0;
        int col = 0;
        for (; offset < length; offset++) {
            final char c = currentCellIndex.charAt(offset);
            if (c == ABSOLUTE_REFERENCE_MARKER) {
                offset++;
                break; //next there must be digits
            }
            if (isAsciiDigit(c)) {
                break;
            }
            col = col * COL_RADIX + toUpperCase(c) - (int) 'A' + 1;
        }
        return col - 1;

        //if (currentCellIndex != null) {
        //    int plingPos = currentCellIndex.lastIndexOf(SHEET_NAME_DELIMITER);
        //    String cell = currentCellIndex.substring(plingPos + 1).toUpperCase(Locale.ROOT);
        //    Matcher matcher = CELL_REF_PATTERN.matcher(cell);
        //    if (!matcher.matches()) {
        //        throw new IllegalArgumentException("Invalid CellReference: " + currentCellIndex);
        //    }
        //    String col = matcher.group(1);
        //
        //    if (col.length() > 0 && col.charAt(0) == REDUNDANT_CHARACTERS) {
        //        col = col.substring(1);
        //    }
        //    if (col.length() == 0) {
        //        return -1;
        //    } else {
        //        return CellReference.convertColStringToIndex(col);
        //    }
        //} else {
        //    if (before == null) {
        //        before = -1;
        //    }
        //    return before + 1;
        //}
    }

    private static final boolean isAsciiDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private final static char toUpperCase(char c) {
        if (isAsciiUpperCase(c)) {
            return c;
        }
        if (isAsciiLowerCase(c)) {
            return (char) (c + ('A' - 'a'));
        }
        throw new IllegalArgumentException("Unexpected char: " + c);
    }

    private static final boolean isAsciiLowerCase(char c) {
        return 'a' <= c && c <= 'z';
    }

    private static final boolean isAsciiUpperCase(char c) {
        return 'A' <= c && c <= 'Z';
    }

}
