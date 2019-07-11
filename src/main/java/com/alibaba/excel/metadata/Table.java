package com.alibaba.excel.metadata;

import java.util.List;

import com.alibaba.excel.event.Handler;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * table
 * 
 * @author jipengfei
 */
public class Table {
    /**
     * Starting from 1
     */
    private Integer tableNo;
    /**
     * Writes the header relative to the existing contents of the sheet. Indexes are zero-based.
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
     * Need head
     */
    private Boolean needHead;
    /**
     * Handle some extra logic. So far only for writing purposes {@link WriteHandler}
     *
     */
    private List<Handler> handler;

    public Table(Integer sheetNo) {
        this(sheetNo, 0, null, null, Boolean.TRUE);
    }

    public Table(Integer sheetNo, List<List<String>> head) {
        this(sheetNo, 0, head, null, Boolean.TRUE);
    }

    public Table(Integer sheetNo, Class clazz) {
        this(sheetNo, 0, null, clazz, Boolean.TRUE);
    }

    public Table(Integer tableNo, Integer writeRelativeHeadRowIndex, List<List<String>> head, Class clazz,
        Boolean needHead) {
        if (tableNo == null || tableNo < 1) {
            throw new IllegalArgumentException("SheetNo must greater than 0");
        }
        if (writeRelativeHeadRowIndex == null || writeRelativeHeadRowIndex < 0) {
            throw new IllegalArgumentException("WriteRelativeHeadRowIndex must greater than -1");
        }
        if (head != null && !head.isEmpty() && clazz != null) {
            throw new IllegalArgumentException("Head and clazz fill in no more than one");
        }
        if (needHead == null) {
            throw new IllegalArgumentException("NeedHead can not be null");
        }
        this.tableNo = tableNo;
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
        this.head = head;
        this.clazz = clazz;
        this.needHead = needHead;

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

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }

    public List<Handler> getHandler() {
        return handler;
    }

    public void setHandler(List<Handler> handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNo=" + tableNo + '}';
    }
}
