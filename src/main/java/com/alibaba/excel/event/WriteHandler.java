package com.alibaba.excel.event;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author jipengfei
 * @deprecated please use {@link com.alibaba.excel.write.handler.WriteHandler}
 */
@Deprecated
public interface WriteHandler {

    /**
     * Custom operation after creating each sheet
     *
     * @param sheetNo
     * @param sheet
     */
    void sheet(int sheetNo, Sheet sheet);

    /**
     * Custom operation after creating each row
     *
     * @param rowNum
     * @param row
     */
    void row(int rowNum, Row row);

    /**
     * Custom operation after creating each cell
     *
     * @param cellNum
     * @param cell
     */
    void cell(int cellNum, Cell cell);
}
