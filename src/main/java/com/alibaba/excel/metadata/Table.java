package com.alibaba.excel.metadata;

import java.util.List;

import com.alibaba.excel.write.style.CellStyleStrategy;

/**
 * @author jipengfei
 */
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
    @Deprecated
    private TableStyle tableStyle;

    private CellStyleStrategy cellStyleStrategy;

    public CellStyleStrategy getCellStyleStrategy() {
        return cellStyleStrategy;
    }

    public void setCellStyleStrategy(CellStyleStrategy cellStyleStrategy) {
        this.cellStyleStrategy = cellStyleStrategy;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    public Table(Integer tableNo) {
        this.tableNo = tableNo;
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
