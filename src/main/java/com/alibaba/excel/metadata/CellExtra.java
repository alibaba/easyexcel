package com.alibaba.excel.metadata;

/**
 * Cell extra information.
 *
 * @author Jiaju Zhuang
 */
public class CellExtra extends AbstractCell {
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
