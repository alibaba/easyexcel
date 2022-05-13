package com.alibaba.excel.context.csv;

import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.csv.CsvReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public class DefaultCsvReadContext extends AnalysisContextImpl implements CsvReadContext {

    public DefaultCsvReadContext(ReadWorkbook readWorkbook, ExcelTypeEnum actualExcelType) {
        super(readWorkbook, actualExcelType);
    }

    @Override
    public CsvReadWorkbookHolder csvReadWorkbookHolder() {
        return (CsvReadWorkbookHolder)readWorkbookHolder();
    }

    @Override
    public CsvReadSheetHolder csvReadSheetHolder() {
        return (CsvReadSheetHolder)readSheetHolder();
    }
}
