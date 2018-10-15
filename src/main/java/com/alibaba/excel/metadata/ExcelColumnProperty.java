package com.alibaba.excel.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 */
public class ExcelColumnProperty implements Comparable<ExcelColumnProperty> {

    /**
     */
    private Field field;

    /**
     */
    private int index = 99999;

    /**
     */
    private List<String> head = new ArrayList<String>();

    /**
     */
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getHead() {
        return head;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

    public int compareTo(ExcelColumnProperty o) {
        int x = this.index;
        int y = o.getIndex();
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}