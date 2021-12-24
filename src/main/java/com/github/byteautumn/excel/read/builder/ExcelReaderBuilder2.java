package com.github.byteautumn.excel.read.builder;

import java.util.List;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.github.byteautumn.excel.ExcelReader2;

/**
 * 只重写需要的几个方法
 *
 * @author byte.autumn
 */
public class ExcelReaderBuilder2 extends ExcelReaderBuilder {
    @Override
    public <T> List<T> doReadAllSync() {
        SyncReadListener syncReadListener = new SyncReadListener();
        registerReadListener(syncReadListener);
        ExcelReader excelReader = build();
        excelReader.readAll();
        excelReader.finish();
        return (List<T>)syncReadListener.getList();
    }

    @Override
    public ExcelReader build() {
        ReadWorkbook readWorkbook = parameter();
        return new ExcelReader2(readWorkbook);
    }
}
