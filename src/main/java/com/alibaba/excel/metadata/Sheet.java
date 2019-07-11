package com.alibaba.excel.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

/**
 * sheet
 * 
 * @author jipengfei
 */
public class Sheet {
    /**
     * Starting from 0
     */
    private Integer sheetNo;
    /**
     * sheet name
     */
    private String sheetName;
    /**
     * Count the number of added heads when read sheet.
     *
     * <li>0 - This Sheet has no head ,since the first row are the data
     * <li>1 - This Sheet has one row head , this is the default
     * <li>2 - This Sheet has two row head ,since the third row is the data
     */
    private Integer readHeadRowNumber;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;
    /**
     * You can only choose one of the {@link Sheet#head} and {@link Sheet#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link Sheet#head} and {@link Sheet#clazz}
     */
    private Class clazz;
    /**
     * Custom type conversions override the default
     */
    private Map<Class, Converter> customConverterMap;
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Custom type handler override the default
     */
    private List<WriteHandler> customWriteHandlerList;

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

    public Sheet() {}

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     * 
     * @param sheetNo
     * @param readHeadRowNumber
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo, int readHeadRowNumber) {
        this.sheetNo = sheetNo - 1;
        this.readHeadRowNumber = readHeadRowNumber;
    }

    /**
     * It was 'sheetNo' starting from 1 and now it is starting from 0
     *
     * @param sheetNo
     * @deprecated please use {@link ExcelWriterSheetBuilder#build()}
     */
    @Deprecated
    public Sheet(int sheetNo) {
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
        this.sheetNo = sheetNo - 1;
        this.readHeadRowNumber = readHeadRowNumber;
        this.clazz = clazz;
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
        this.sheetNo = sheetNo - 1;
        this.clazz = clazz;
        this.readHeadRowNumber = readHeadRowNumber;
        this.sheetName = sheetName;
        this.head = head;
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

    public Integer getReadHeadRowNumber() {
        return readHeadRowNumber;
    }

    public void setReadHeadRowNumber(Integer readHeadRowNumber) {
        this.readHeadRowNumber = readHeadRowNumber;
    }

    public Integer getWriteRelativeHeadRowIndex() {
        return writeRelativeHeadRowIndex;
    }

    public void setWriteRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Map<Class, Converter> getCustomConverterMap() {
        return customConverterMap;
    }

    public void setCustomConverterMap(Map<Class, Converter> customConverterMap) {
        this.customConverterMap = customConverterMap;
    }

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }

    public List<WriteHandler> getCustomWriteHandlerList() {
        return customWriteHandlerList;
    }

    public void setCustomWriteHandlerList(List<WriteHandler> customWriteHandlerList) {
        this.customWriteHandlerList = customWriteHandlerList;
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
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
    }

    /**
     * 
     * @param readHeadRowNumber
     * @deprecated please use {@link Sheet#setReadHeadRowNumber(Integer)} )}
     */
    @Deprecated
    public void setHeadLineMun(int readHeadRowNumber) {
        this.readHeadRowNumber = readHeadRowNumber;
    }

    @Override
    public String toString() {
        return "Sheet{" + "sheetNo=" + sheetNo + ", sheetName='" + sheetName + '\'' + '}';
    }
}
