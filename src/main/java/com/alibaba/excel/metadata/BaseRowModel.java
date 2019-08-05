package com.alibaba.excel.metadata;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

import com.alibaba.excel.annotation.ExcelIgnore;

/**
 * Excel基础模型
 *
 * @author jipengfei
 * @deprecated Now you don't need to extend any classes
 */
@Deprecated
public class BaseRowModel {

    /**
     * 每列样式
     */
    @ExcelIgnore
    private Map<Integer, CellStyle> cellStyleMap = new HashMap<Integer, CellStyle>();

    public void addStyle(Integer row, CellStyle cellStyle) {
        cellStyleMap.put(row, cellStyle);
    }

    public CellStyle getStyle(Integer row) {
        return cellStyleMap.get(row);
    }

    public Map<Integer, CellStyle> getCellStyleMap() {
        return cellStyleMap;
    }

    public void setCellStyleMap(Map<Integer, CellStyle> cellStyleMap) {
        this.cellStyleMap = cellStyleMap;
    }
}
