package com.alibaba.excel.write.context;

import java.io.OutputStream;

import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Table;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author jipengfei
 */
public interface GenerateContext {


    /**
     * 返回当前sheet
     * @return current read sheet
     */
    Sheet getCurrentSheet();

    /**
     *
     * 获取表头样式
     * @return 当前行表头样式
     */
    CellStyle getCurrentHeadCellStyle();

    /**
     * 获取内容样式
     * @return 当前行内容样式
     */
    CellStyle getCurrentContentStyle();


    /**
     * 返回WorkBook
     * @return 返回文件book
     */
    Workbook getWorkbook();

    /**
     * 返回Io流
     * @return 返回out流
     */
    OutputStream getOutputStream();

    /**
     * 构建一个sheet
     * @param sheet
     */
    void buildCurrentSheet(com.alibaba.excel.metadata.Sheet sheet);

    /**
     * 构建一个Table
     * @param table
     */
    void buildTable(Table table);

    /**
     * 返回表头信息
     * @return 返回表头信息
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     *
     * @return 是否需要表头
     */
    boolean needHead();
}


