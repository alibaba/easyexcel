package com.alibaba.excel.metadata;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * table
 * 
 * @author jipengfei
 */
public class Table {
    /**
     * Starting from 0
     */
    private Integer tableNo;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;
    /**
     * You can only choose one of the {@link Table#head} and {@link Table#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link Table#head} and {@link Table#clazz}
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
     *
     * @deprecated please use{@link RowCellStyleStrategy}
     */
    @Deprecated
    private TableStyle tableStyle;

    public Table(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
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

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNo=" + tableNo + '}';
    }
}
