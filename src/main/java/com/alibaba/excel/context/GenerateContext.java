package com.alibaba.excel.context;

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
     * @return current analysis sheet
     */
    Sheet getCurrentSheet();

    /**
     *
     * 获取表头样式
     * @return
     */
    CellStyle getCurrentHeadCellStyle();

    /**
     * 获取内容样式
     * @return
     */
    CellStyle getCurrentContentStyle();


    /**
     * 返回WorkBook
     * @return
     */
    Workbook getWorkbook();

    /**
     * 返回Io流
     * @return
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
     * @return
     */
    ExcelHeadProperty getExcelHeadProperty();

    /**
     *
     * @return
     */
    boolean needHead();
}


