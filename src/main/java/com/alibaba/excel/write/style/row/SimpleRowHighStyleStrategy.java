package com.alibaba.excel.write.style.row;

import org.apache.poi.ss.usermodel.Row;

/**
 * Set the head column high and content column high
 * 
 * @author zhuangjiaju
 */
public class SimpleRowHighStyleStrategy extends AbstractRowHighStyleStrategy {
    private short headColumnHigh;
    private short contentColumnHigh;

    public SimpleRowHighStyleStrategy(short headColumnHigh, short contentColumnHigh) {
        this.headColumnHigh = headColumnHigh;
        this.contentColumnHigh = contentColumnHigh;
    }

    @Override
    protected void setHeadColumnHigh(Row row, int relativeRowIndex) {
        row.setHeight(headColumnHigh);
    }

    @Override
    protected void setContentColumnHigh(Row row, int relativeRowIndex) {
        row.setHeight(contentColumnHigh);
    }

}
