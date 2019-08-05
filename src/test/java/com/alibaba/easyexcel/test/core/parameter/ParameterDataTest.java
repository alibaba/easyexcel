package com.alibaba.easyexcel.test.core.parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.cache.MapCache;
import com.alibaba.excel.converters.string.StringStringConverter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;

/**
 *
 * @author zhuangjiaju
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParameterDataTest {

    private static File file;

    @BeforeClass
    public static void init() {
        file = TestFileUtil.createNewFile("parameter07.xlsx");
    }

    @Test
    public void T01ReadAndWrite() throws Exception {
        readAndWrite1();
        readAndWrite2();
        readAndWrite3();
        readAndWrite4();
        readAndWrite5();
        readAndWrite6();
        readAndWrite7();
    }

    private void readAndWrite1() {
        EasyExcelFactory.write(file.getPath()).head(ParameterData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file.getPath()).head(ParameterData.class)
            .registerReadListener(new ParameterDataListener()).sheet().doRead().finish();
    }

    private void readAndWrite2() {
        EasyExcelFactory.write(file.getPath(), ParameterData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file.getPath(), ParameterData.class, new ParameterDataListener()).sheet().doRead()
            .finish();
    }

    private void readAndWrite3() throws Exception {
        EasyExcelFactory.write(new FileOutputStream(file)).head(ParameterData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file.getPath()).head(ParameterData.class)
            .registerReadListener(new ParameterDataListener()).sheet().doRead().finish();
    }

    private void readAndWrite4() throws Exception {
        EasyExcelFactory.write(new FileOutputStream(file), ParameterData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file.getPath(), new ParameterDataListener()).head(ParameterData.class).sheet().doRead()
            .finish();
    }

    private void readAndWrite5() throws Exception {
        ExcelWriter excelWriter = EasyExcelFactory.write(new FileOutputStream(file)).head(ParameterData.class)
            .relativeHeadRowIndex(0).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet(0).relativeHeadRowIndex(0).needHead(Boolean.FALSE).build();
        WriteTable writeTable = EasyExcelFactory.writerTable(0).relativeHeadRowIndex(0).needHead(Boolean.TRUE).build();
        excelWriter.write(data(), writeSheet, writeTable);
        excelWriter.finish();

        ExcelReader excelReader =
            EasyExcelFactory.read(file.getPath(), new ParameterDataListener()).head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE).autoCloseStream(Boolean.TRUE).readCache(new MapCache()).build();
        ReadSheet readSheet = EasyExcelFactory.readSheet().head(ParameterData.class).use1904windowing(Boolean.FALSE)
            .headRowNumber(1).sheetNo(0).sheetName("0").build();
        excelReader.read(readSheet);
        excelReader.finish();

        excelReader = EasyExcelFactory.read(file.getPath(), new ParameterDataListener()).head(ParameterData.class)
            .mandatoryUseInputStream(Boolean.FALSE).autoCloseStream(Boolean.TRUE).readCache(new MapCache()).build();
        excelReader.read();
        excelReader.finish();
    }

    private void readAndWrite6() throws Exception {
        ExcelWriter excelWriter = EasyExcelFactory.write(new FileOutputStream(file)).head(ParameterData.class)
            .relativeHeadRowIndex(0).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet(0).relativeHeadRowIndex(0).needHead(Boolean.FALSE).build();
        WriteTable writeTable = EasyExcelFactory.writerTable(0).registerConverter(new StringStringConverter())
            .relativeHeadRowIndex(0).needHead(Boolean.TRUE).build();
        excelWriter.write(data(), writeSheet, writeTable);
        excelWriter.finish();

        ExcelReader excelReader =
            EasyExcelFactory.read(file.getPath(), new ParameterDataListener()).head(ParameterData.class)
                .mandatoryUseInputStream(Boolean.FALSE).autoCloseStream(Boolean.TRUE).readCache(new MapCache()).build();
        ReadSheet readSheet = EasyExcelFactory.readSheet("0").head(ParameterData.class).use1904windowing(Boolean.FALSE)
            .headRowNumber(1).sheetNo(0).build();
        excelReader.read(readSheet);
        excelReader.finish();

        excelReader = EasyExcelFactory.read(file.getPath(), new ParameterDataListener()).head(ParameterData.class)
            .mandatoryUseInputStream(Boolean.FALSE).autoCloseStream(Boolean.TRUE).readCache(new MapCache()).build();
        excelReader.read();
        excelReader.finish();
    }

    private void readAndWrite7() throws Exception {
        EasyExcelFactory.write(file, ParameterData.class).registerConverter(new StringStringConverter()).sheet()
            .registerConverter(new StringStringConverter()).needHead(Boolean.FALSE).table(0).needHead(Boolean.TRUE)
            .doWrite(data()).finish();
        EasyExcelFactory.read(file.getPath()).head(ParameterData.class)
            .registerReadListener(new ParameterDataListener()).sheet().registerConverter(new StringStringConverter())
            .doRead().finish();
    }

    private List<ParameterData> data() {
        List<ParameterData> list = new ArrayList<ParameterData>();
        for (int i = 0; i < 10; i++) {
            ParameterData simpleData = new ParameterData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }
}
