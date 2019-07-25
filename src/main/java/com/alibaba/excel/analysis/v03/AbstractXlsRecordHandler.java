package com.alibaba.excel.analysis.v03;

/**
 *
 * @author Dan Zheng
 */
public abstract class AbstractXlsRecordHandler implements XlsRecordHandler {
    protected int row = -1;
    protected int column = -1;
    protected String value = null;

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(XlsRecordHandler o) {
        return this.getOrder() - o.getOrder();
    }
}
