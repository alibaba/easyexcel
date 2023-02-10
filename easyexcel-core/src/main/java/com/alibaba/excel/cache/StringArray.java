package com.alibaba.excel.cache;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Specially used to store a string array
 *
 * The reason for not using  {@link ArrayList} is: when the `elementData` field of `ArrayList` is serialized, `ehcache`
 * There will be a warning. In fact, there is no problem with the `elementData` warning, but there will be a
 * `warn` prompt, so I wrote a warning to prevent the `warn` prompt.
 *
 * @author Jiaju Zhuang
 */
public class StringArray implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * element Data
     */
    private String[] elementData;

    /**
     * The size of the StringArray (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    public StringArray(int capacity) {
        this.elementData = new String[capacity];
    }

    public void add(String e) {
        elementData[size++] = e;
    }

    public String get(int index) {
        return elementData[index];
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

}
