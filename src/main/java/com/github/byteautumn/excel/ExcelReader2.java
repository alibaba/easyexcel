package com.github.byteautumn.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.github.byteautumn.excel.analysis.ExcelAnalyserImpl2;
import com.github.byteautumn.excel.analysis.ExcelAnalyserImpl3;

/**
 * 重写需要的方法
 *
 * @author byte.autumn
 */
public class ExcelReader2 extends ExcelReader {

    private ExcelAnalyser excelAnalyser;

    public ExcelReader2(ReadWorkbook readWorkbook) {
        super(readWorkbook);
        // 只能通过反射来指定对应的对象了
        //super.excelAnalyser =
        excelAnalyser = new ExcelAnalyserImpl3(readWorkbook);

    }

    @Override
    public void readAll() {
        System.out.println("子类");
        //super.readAll();
        excelAnalyser.analysis(null, Boolean.TRUE);
    }
}
