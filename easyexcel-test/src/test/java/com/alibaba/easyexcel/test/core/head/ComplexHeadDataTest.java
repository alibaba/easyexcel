package com.alibaba.easyexcel.test.core.head;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ComplexHeadDataTest {

    private static File file07;
    private static File file03;
    private static File fileCsv;
    private static File file07AutomaticMergeHead;
    private static File file03AutomaticMergeHead;
    private static File fileCsvAutomaticMergeHead;

    @BeforeAll
    public static void init() {
        file07 = TestFileUtil.createNewFile("complexHead07.xlsx");
        file03 = TestFileUtil.createNewFile("complexHead03.xls");
        fileCsv = TestFileUtil.createNewFile("complexHeadCsv.csv");
        file07AutomaticMergeHead = TestFileUtil.createNewFile("complexHeadAutomaticMergeHead07.xlsx");
        file03AutomaticMergeHead = TestFileUtil.createNewFile("complexHeadAutomaticMergeHead03.xls");
        fileCsvAutomaticMergeHead = TestFileUtil.createNewFile("complexHeadAutomaticMergeHeadCsv.csv");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    @Test
    public void t03ReadAndWriteCsv() {
        readAndWrite(fileCsv);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, ComplexHeadData.class).sheet().doWrite(data());
        EasyExcel.read(file, ComplexHeadData.class, new ComplexDataListener())
            .xlsxSAXParserFactoryName("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl").sheet().doRead();
    }

    @Test
    public void t11ReadAndWriteAutomaticMergeHead07() {
        readAndWriteAutomaticMergeHead(file07AutomaticMergeHead);
    }

    @Test
    public void t12ReadAndWriteAutomaticMergeHead03() {
        readAndWriteAutomaticMergeHead(file03AutomaticMergeHead);
    }

    @Test
    public void t13ReadAndWriteAutomaticMergeHeadCsv() {
        readAndWriteAutomaticMergeHead(fileCsvAutomaticMergeHead);
    }

    private void readAndWriteAutomaticMergeHead(File file) {
        EasyExcel.write(file, ComplexHeadData.class).automaticMergeHead(Boolean.FALSE).sheet().doWrite(data());
        EasyExcel.read(file, ComplexHeadData.class, new ComplexDataListener()).sheet().doRead();
    }

    private List<ComplexHeadData> data() {
        List<ComplexHeadData> list = new ArrayList<ComplexHeadData>();
        ComplexHeadData data = new ComplexHeadData();
        data.setString0("字符串0");
        data.setString1("字符串1");
        data.setString2("字符串2");
        data.setString3("字符串3");
        data.setString4("字符串4");
        list.add(data);
        return list;
    }
}
