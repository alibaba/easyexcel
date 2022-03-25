package com.alibaba.excel.read.metadata.holder.csv;

import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvReadSheetHolder extends ReadSheetHolder {
    public void ReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        this.setReadSheet(readSheet);
        this.setParentReadWorkbookHolder(readWorkbookHolder);
        this.setSheetNo(readSheet.getSheetNo());
        this.setSheetName(readSheet.getSheetName());
        this.setCellMap(new LinkedHashMap<>());
        this.setRowIndex(-1);
    }
    public CsvReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        ReadSheetHolder(readSheet, readWorkbookHolder);
    }

}
