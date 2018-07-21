package com.alibaba.excel;

import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;

/**
 * 生成excel，thread unsafe
 *
 * @author jipengfei
 */
public class ExcelWriter {

    private ExcelBuilder excelBuilder;

    /**
     * 生成EXCEL
     *
     * @param outputStream 文件输出流
     * @param typeEnum     输出文件类型03或07，强烈建议使用07版（可以输出超大excel而不内存溢出）
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        this(outputStream, typeEnum, true);
    }

    /**
     * 生成EXCEL
     *
     * @param outputStream 文件输出流
     * @param typeEnum     输出文件类型03或07，强烈建议使用07版（可以输出超大excel而不内存溢出）
     * @param needHead 是否需要表头
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        excelBuilder = new ExcelBuilderImpl();
        excelBuilder.init(outputStream, typeEnum, needHead);
    }

    /**
     * 生成多sheet,每个sheet一张表
     *
     * @param data  一行数据是一个BaseRowModel子类的模型
     * @param sheet data写入某个sheet
     * @return this（当前引用）
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * 生成多sheet,每个sheet一张表
     *
     * @param data  List代表一行数据
     * @param sheet data写入某个sheet
     * @return this（当前引用）
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * 可生成多sheet,每个sheet多张表
     *
     * @param data  type 一个java模型一行数据
     * @param sheet data写入某个sheet
     * @param table data写入某个table
     * @return this（当前引用）
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * 可生成多sheet,每个sheet多张表
     *
     * @param data  List 代表一行数据
     * @param sheet data写入某个sheet
     * @param table data写入某个table
     * @return this（当前引用）
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    public void finish() {
        excelBuilder.finish();
    }
}
