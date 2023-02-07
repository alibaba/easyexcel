package com.alibaba.excel.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class CellUtils {

    private CellUtils() {
        throw new UnsupportedOperationException("utils class can't invoke construction");
    }

    /**
     * get cell value;
     *
     * @return maybe null when cell style is not in
     **/
    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            throw new NullPointerException("cell must be not null");
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }


}
