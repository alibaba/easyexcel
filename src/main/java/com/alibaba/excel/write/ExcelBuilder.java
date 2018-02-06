package com.alibaba.excel.write;

import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Excel构建器
 *
 * @author jipengfei
 */
public interface ExcelBuilder {

    /**
     * 初始化Excel构造器
     *
     * @param out       文件输出流
     * @param excelType 输出Excel类型，建议使用07版xlsx（性能，内存消耗，cpu使用都远低于03版xls）
     * @param needHead  是否需要将表头写入Excel
     */
    void init(OutputStream out, ExcelTypeEnum excelType, boolean needHead);

    /**
     * 向Excel增加的内容
     *
     * @param data 数据格式
     */
    void addContent(List data);

    /**
     * 向Excel增加的内容
     *
     * @param data       数据格式
     * @param sheetParam 数据写到某个sheet中
     */
    void addContent(List data, Sheet sheetParam);

    /**
     * 向Excel增加的内容
     *
     * @param data       数据格式
     * @param sheetParam 数据写到某个sheet中
     * @param table      写到某个sheet的某个Table
     */
    void addContent(List data, Sheet sheetParam, Table table);

    /**
     * 关闭资源
     */
    void finish();
}
