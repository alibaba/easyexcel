package com.alibaba.excel.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

/**
 * sheet
 * 
 * @author jipengfei
 */
public class Sheet extends BasicParameter {
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
     * @deprecated please use{@link RowCellStyleStrategy}
     */
    @Deprecated
    private TableStyle tableStyle;

    public Sheet() {
        super();
    }

    /**
     * Create sheet
     * 
     * @param sheetNo
     * @param sheetName
     */
    public Sheet(Integer sheetNo, String sheetName) {
        super();
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     * 
     * @param sheetNo
     * @param readHeadRowNumber
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo, int readHeadRowNumber) {
        super();
        this.sheetNo = sheetNo - 1;
        setReadHeadRowNumber(readHeadRowNumber);
    }

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     *
     * @param sheetNo
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo) {
        super();
        this.sheetNo = sheetNo - 1;
    }

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     * 
     * @param sheetNo
     * @param readHeadRowNumber
     * @param clazz
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo, int readHeadRowNumber, Class clazz) {
        super();
        this.sheetNo = sheetNo - 1;
        setReadHeadRowNumber(readHeadRowNumber);
        setClazz(clazz);
    }

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     *
     * @param sheetNo
     * @param readHeadRowNumber
     * @param clazz
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo, int readHeadRowNumber, Class clazz, String sheetName, List<List<String>> head) {
        super();
        this.sheetNo = sheetNo - 1;
        this.sheetName = sheetName;

        setReadHeadRowNumber(readHeadRowNumber);
        setClazz(clazz);
        setHead(head);
    }

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

    /**
     * 
     * @param writeRelativeHeadRowIndex
     * @deprecated please use {@link Sheet#setWriteRelativeHeadRowIndex(Integer)}
     */
    @Deprecated
    public void setStartRow(int writeRelativeHeadRowIndex) {
        setWriteRelativeHeadRowIndex(writeRelativeHeadRowIndex);
    }

    /**
     * 
     * @param readHeadRowNumber
     * @deprecated please use {@link Sheet#setReadHeadRowNumber(Integer)} )}
     */
    @Deprecated
    public void setHeadLineMun(int readHeadRowNumber) {
        setReadHeadRowNumber(readHeadRowNumber);
    }

    @Override
    public String toString() {
        return "Sheet{" + "sheetNo=" + sheetNo + ", sheetName='" + sheetName + '\'' + '}';
    }
}
