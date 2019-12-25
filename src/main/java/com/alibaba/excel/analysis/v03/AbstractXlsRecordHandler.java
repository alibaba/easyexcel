package com.alibaba.excel.analysis.v03;

import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.metadata.CellData;

/**
 *
 * @author Dan Zheng
 */
public abstract class AbstractXlsRecordHandler implements XlsRecordHandler {
    protected int row = -1;
    protected int column = -1;
    protected CellData cellData;
    protected XlsReadContext analysisContext;

    public AbstractXlsRecordHandler(XlsReadContext analysisContext) {
        this.analysisContext = analysisContext;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public CellData getCellData() {
        return cellData;
    }

    @Override
    public int compareTo(XlsRecordHandler o) {
        return this.getOrder() - o.getOrder();
    }
}
