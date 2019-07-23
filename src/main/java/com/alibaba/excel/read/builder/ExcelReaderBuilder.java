package com.alibaba.excel.read.builder;

import com.alibaba.excel.write.metadata.Workbook;

/**
 * Build ExcelWriter
 * 
 * @author zhuangjiaju
 */
public class ExcelReaderBuilder {
    /**
     * Workbook
     */
    private Workbook workbook;

    public ExcelReaderBuilder() {
        this.workbook = new Workbook();
    }


}
