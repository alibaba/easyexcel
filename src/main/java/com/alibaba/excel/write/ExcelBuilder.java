package com.alibaba.excel.write;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author jipengfei
 */
public interface ExcelBuilder {


    void init(InputStream templateInputStream, OutputStream out, ExcelTypeEnum excelType, boolean needHead);


    void addContent(List data, int startRow);


    void addContent(List data, Sheet sheetParam);


    void addContent(List data, Sheet sheetParam, Table table);


    void finish();
}
