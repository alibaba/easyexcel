package com.alibaba.excel.write.metadata;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

/**
 * Write sheet
 *
 * @author jipengfei
 */
public class WriteSheet extends WriteBasicParameter {
    /**
     * Starting from 0
     */
    private Integer sheetNo;
    /**
     * sheet name
     */
    private String sheetName;
    /**
     * column with
     *
     * @deprecated please use {@link SimpleColumnWidthStyleStrategy}
     */
    @Deprecated
    private Map<Integer, Integer> columnWidthMap = new HashMap<Integer, Integer>();
    /**
     *
     * @deprecated please use{@link HorizontalCellStyleStrategy}
     */
    @Deprecated
    private TableStyle tableStyle;

    public Integer getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Map<Integer, Integer> getColumnWidthMap() {
        return columnWidthMap;
    }

    public void setColumnWidthMap(Map<Integer, Integer> columnWidthMap) {
        this.columnWidthMap = columnWidthMap;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    @Override
    public String toString() {
        return "WriteSheet{" + "sheetNo=" + sheetNo + ", sheetName='" + sheetName + '\'' + "} " + super.toString();
    }
}
