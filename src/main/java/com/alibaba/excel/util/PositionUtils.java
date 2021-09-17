package com.alibaba.excel.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.util.CellReference;

/**
 * @author jipengfei
 */
public class PositionUtils {

    private static final Pattern CELL_REF_PATTERN = Pattern.compile("(\\$?[A-Z]+)?" + "(\\$?[0-9]+)?",
        Pattern.CASE_INSENSITIVE);
    private static final char SHEET_NAME_DELIMITER = '!';

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

    public static int getCol(String currentCellIndex, Integer before) {
        if (currentCellIndex != null) {
            int plingPos = currentCellIndex.lastIndexOf(SHEET_NAME_DELIMITER);
            String cell = currentCellIndex.substring(plingPos + 1).toUpperCase(Locale.ROOT);
            Matcher matcher = CELL_REF_PATTERN.matcher(cell);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid CellReference: " + currentCellIndex);
            }
            String col = matcher.group(1);

            if (col.length() > 0 && col.charAt(0) == '$') {
                col = col.substring(1);
            }
            if (col.length() == 0) {
                return -1;
            } else {
                return CellReference.convertColStringToIndex(col);
            }
        } else {
            if (before == null) {
                before = -1;
            }
            return before + 1;
        }
    }

}
