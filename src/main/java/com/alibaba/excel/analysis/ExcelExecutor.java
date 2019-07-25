package com.alibaba.excel.analysis;

import java.util.List;

import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * Excel file Executor
 *
 * @author zhuangjiaju
 */
public interface ExcelExecutor {

    List<ReadSheet> sheetList();

    void execute();

}
