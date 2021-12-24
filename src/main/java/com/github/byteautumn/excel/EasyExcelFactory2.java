package com.github.byteautumn.excel;

import java.io.File;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.github.byteautumn.excel.read.builder.ExcelReaderBuilder2;

/**
 * 重写 Easy Excel Factory 入口，因为需要重新写 Read 方法
 * <p>
 * 其实继承不继承都没关系，static 的也继承不下来
 *
 * @author byte.autumn
 */
public class EasyExcelFactory2 extends EasyExcelFactory {

    /**
     * Build excel the read
     *
     * @param pathName
     *            File path to read.
     * @param head
     *            Annotate the class for configuration information.
     * @param readListener
     *            Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read2(String pathName, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder2();
        excelReaderBuilder.file(pathName);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

}
