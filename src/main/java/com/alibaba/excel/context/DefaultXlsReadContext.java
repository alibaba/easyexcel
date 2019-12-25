package com.alibaba.excel.context;

import com.alibaba.excel.read.metadata.ReadWorkbook;

/**
 *
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public class DefaultXlsReadContext extends AnalysisContextImpl implements XlsReadContext {

    public DefaultXlsReadContext(ReadWorkbook readWorkbook) {
        super(readWorkbook);
    }
}
