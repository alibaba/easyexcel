package com.alibaba.excel.metadata;

import java.util.List;

/**
 * @author jipengfei
 * @deprecated please use {@link com.alibaba.excel.write.metadata.WriteTable}
 */
@Deprecated
public class Table {
    /**
     */
    private Class<? extends BaseRowModel> clazz;

    /**
     */
    private List<List<String>> head;

    /**
     */
    private int tableNo;

    /**
     */
    private TableStyle tableStyle;

    public Table(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    public Class<? extends BaseRowModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends BaseRowModel> clazz) {
        this.clazz = clazz;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }
}
