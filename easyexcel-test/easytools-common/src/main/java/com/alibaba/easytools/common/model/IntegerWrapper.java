package com.alibaba.easytools.common.model;

import java.io.Serializable;

/**
 * 整形的封装类
 *
 * @author 是仪
 */
public class IntegerWrapper extends Number implements Serializable {
    private static final long serialVersionUID = 1L;

    private int value;

    public IntegerWrapper(int initialValue) {
        value = initialValue;
    }

    public IntegerWrapper() {
    }

    public final int get() {
        return value;
    }

    public final void set(int newValue) {
        value = newValue;
    }

    public final int getAndIncrement() {
        return getAndAdd(1);
    }

    public final int getAndDecrement() {
        return getAndAdd(-1);
    }

    public final int getAndAdd(int delta) {
        int oldValue = value;
        value += delta;
        return oldValue;
    }

    public final int incrementAndGet() {
        return addAndGet(1);
    }

    public final int decrementAndGet() {
        return addAndGet(-1);
    }

    public final int addAndGet(int delta) {
        value += delta;
        return value;
    }

    public final void increment() {
        add(1);
    }

    public final void decrement() {
        add(-1);
    }

    public final void add(int delta) {
        value += delta;
    }

    @Override
    public int intValue() {
        return get();
    }

    @Override
    public long longValue() {
        return get();
    }

    @Override
    public float floatValue() {
        return get();
    }

    @Override
    public double doubleValue() {
        return get();
    }
}
