package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;

import com.alibaba.excel.metadata.Head;

/**
 * @author jipengfei
 */
public class ExcelContentProperty {
    /**
     * Java filed
     */
    private Field field;
    /**
     * Excel head
     */
    private Head head;
    private CellStyleProperty cellStyleProperty;
    private DateTimeFormatProperty dateTimeFormatProperty;
    private NumberFormatProperty numberFormatProperty;
    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * @return
     */
    private Boolean use1904windowing;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public CellStyleProperty getCellStyleProperty() {
        return cellStyleProperty;
    }

    public void setCellStyleProperty(CellStyleProperty cellStyleProperty) {
        this.cellStyleProperty = cellStyleProperty;
    }

    public DateTimeFormatProperty getDateTimeFormatProperty() {
        return dateTimeFormatProperty;
    }

    public void setDateTimeFormatProperty(DateTimeFormatProperty dateTimeFormatProperty) {
        this.dateTimeFormatProperty = dateTimeFormatProperty;
    }

    public NumberFormatProperty getNumberFormatProperty() {
        return numberFormatProperty;
    }

    public void setNumberFormatProperty(NumberFormatProperty numberFormatProperty) {
        this.numberFormatProperty = numberFormatProperty;
    }

    public Boolean getUse1904windowing() {
        return use1904windowing;
    }

    public void setUse1904windowing(Boolean use1904windowing) {
        this.use1904windowing = use1904windowing;
    }
}
