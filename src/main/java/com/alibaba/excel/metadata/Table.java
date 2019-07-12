package com.alibaba.excel.metadata;

import com.alibaba.excel.write.style.RowCellStyleStrategy;

/**
 * table
 * 
 * @author jipengfei
 */
public class Table extends BasicParameter {
    /**
     * Starting from 0
     */
    private Integer tableNo;
    /**
     *
     * @deprecated please use{@link RowCellStyleStrategy}
     */
    @Deprecated
    private TableStyle tableStyle;

    public Table() {
        super();
    }

    public Table(Integer tableNo) {
        super();
        this.tableNo = tableNo;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
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
