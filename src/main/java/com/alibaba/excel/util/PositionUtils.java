package com.alibaba.excel.util;

/**
 * @author jipengfei
 */
public class PositionUtils {

    public static int getRow(String currentCellIndex) {
        int row = 0;
        if (currentCellIndex != null) {
            String rowStr = currentCellIndex.replaceAll("[A-Z]", "").replaceAll("[a-z]", "");
            row = Integer.parseInt(rowStr)-1;
        }
        return row;
    }

    public static int getCol(String currentCellIndex) {
        int col = 0;
        if (currentCellIndex != null) {

            char[] currentIndex = currentCellIndex.replaceAll("[0-9]", "").toCharArray();
            for (int i = 0; i < currentIndex.length; i++) {
                col += (currentIndex[i] - '@') * Math.pow(26, (currentIndex.length - i - 1));
            }
        }
        return col-1;
    }
}
