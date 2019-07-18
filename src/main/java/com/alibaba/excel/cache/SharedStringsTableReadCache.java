package com.alibaba.excel.cache;

import org.apache.poi.xssf.model.SharedStringsTable;

/**
 * Default cache
 * 
 * @author zhuangjiaju
 */
public class SharedStringsTableReadCache implements ReadCache {
    private SharedStringsTable sharedStringsTable;

    public SharedStringsTableReadCache(SharedStringsTable sharedStringsTable) {
        this.sharedStringsTable = sharedStringsTable;
    }

    @Override
    public void put(Integer key, String value) {
        throw new UnsupportedOperationException("Can not put value");
    }

    @Override
    public String get(Integer key) {
        return sharedStringsTable.getItemAt(key).toString();
    }

    @Override
    public void finish() {

    }
}
