package com.alibaba.easyexcel.test.temp.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
@Slf4j
public class CsvReadTest {

    @Test
    public void write() throws Exception {
        Appendable out = new PrintWriter(TestFileUtil.createNewFile("csvWrite.csv"));
        CSVPrinter printer = CSVFormat.DEFAULT.withHeader("userId", "userName")
            .print(out);
        for (int i = 0; i < 10; i++) {
            printer.printRecord("userId" + i, "userName" + i);
        }
        printer.flush();
        printer.close();
    }

    @Test
    public void csvWrite() throws Exception {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".csv";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, CsvData.class).sheet().doWrite(data());
    }

    @Test
    public void writev2() throws Exception {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".csv";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, CsvData.class).sheet().doWrite(data());
    }

    @Test
    public void writeFile() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/zhuangjiaju/test/test1.csv"));
        FileMagic fileMagic = FileMagic.valueOf(fileInputStream);
        log.info("{}", fileMagic);
    }

    private List<CsvData> data() {
        List<CsvData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CsvData data = new CsvData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setIgnore("忽略" + i);
            list.add(data);
        }
        return list;
    }

    @Test
    public void read() {
        //
        //Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        //for (CSVRecord record : records) {
        //    String lastName = record.get("id");
        //    String firstName = record.get("name");
        //    System.out.println(lastName);
        //    System.out.println(firstName);
        //}

    }
}
