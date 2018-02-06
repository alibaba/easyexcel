package com.alibaba.excel.metadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 */
public class ExcelColumnProperty implements Comparable<ExcelColumnProperty> {

    /**
     * 列对应的Class field字段
     */
    private Field field;

    /**
     * 列顺序 默认很大
     */
    private int index = 99999;

    /**
     * 该列对应的表头
     */
    private List<String> head = new ArrayList<String>();

    /**
     * 日期类型format 如："yyyy-MM-dd HH:mm:ss"
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