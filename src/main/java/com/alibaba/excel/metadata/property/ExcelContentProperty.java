package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;

import com.alibaba.excel.converters.Converter;
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
    /**
     * Custom defined converters
     */
    private Converter converter;
    private DateTimeFormatProperty dateTimeFormatProperty;
    private NumberFormatProperty numberFormatProperty;

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

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
