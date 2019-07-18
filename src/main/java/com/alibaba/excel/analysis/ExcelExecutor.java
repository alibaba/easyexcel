package com.alibaba.excel.analysis;

import java.util.List;

import com.alibaba.excel.metadata.Sheet;

/**
 * Excel file Executor
 *
 * @author zhuangjiaju
 */
public interface ExcelExecutor {

    List<Sheet> sheetList();

    void execute();

}
