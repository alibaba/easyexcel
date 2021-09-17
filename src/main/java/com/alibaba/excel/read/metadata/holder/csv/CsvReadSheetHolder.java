package com.alibaba.excel.read.metadata.holder.csv;

import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

import lombok.Data;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
@Data
public class CsvReadSheetHolder extends ReadSheetHolder {

    public CsvReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder);
    }
}
