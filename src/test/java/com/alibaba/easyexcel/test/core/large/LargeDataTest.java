package com.alibaba.easyexcel.test.core.large;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.CostUtil;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.handler.impl.FillStyleCellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LargeDataTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeDataTest.class);
    private static File fileFill07;
    private static File template07;
    private static File fileCsv;
    private static File fileWrite07;
    private static File fileWritePoi07;

    private int i = 0;

    @BeforeClass
    public static void init() {
        fileFill07 = TestFileUtil.createNewFile("largefill07.xlsx");
        fileWrite07 = TestFileUtil.createNewFile("large" + File.separator + "fileWrite07.xlsx");
        fileWritePoi07 = TestFileUtil.createNewFile("large" + File.separator + "fileWritePoi07.xlsx");
        template07 = TestFileUtil.readFile("large" + File.separator + "fill.xlsx");
        fileCsv = TestFileUtil.createNewFile("largefileCsv.csv");
    }

    @Test
    public void t01Read() {
        long start = System.currentTimeMillis();
        EasyExcel.read(TestFileUtil.getPath() + "large" + File.separator + "large07.xlsx", LargeData.class,
            new LargeDataListener()).headRowNumber(2).sheet().doRead();
        LOGGER.info("Large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void t02Fill() {
        ExcelWriter excelWriter = EasyExcel.write(fileFill07).withTemplate(template07).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.fill(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();
    }

    @Test
    public void t03ReadAndWriteCsv() {
        // write
        long start = System.currentTimeMillis();
        ExcelWriter excelWriter = EasyExcel.write(fileCsv).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} write success.", j);
        }
        excelWriter.finish();
        LOGGER.info("CSV large data total time spent:{}", System.currentTimeMillis() - start);

        //  read
        start = System.currentTimeMillis();
        EasyExcel.read(fileCsv, LargeData.class, new LargeDataListener()).sheet().doRead();
        LOGGER.info("CSV large data total time spent:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void t04Write666() throws Exception {
        long start = System.currentTimeMillis();
        ExcelWriter excelWriter = EasyExcel.write(fileWrite07, LargeData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 2; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} write success.", j);
        }
        excelWriter.finish();
        long cost = System.currentTimeMillis() - start;
        LOGGER.info("write cost:{}", cost);

        start = System.currentTimeMillis();
        excelWriter = EasyExcel.write(fileWrite07, LargeData.class).build();
        writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} write success.", j);
        }
        excelWriter.finish();
        cost = System.currentTimeMillis() - start;
        LOGGER.info("write cost:{}", cost);
        start = System.currentTimeMillis();
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileWritePoi07)) {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("sheet1");
            for (int i = 0; i < 100 * 5000; i++) {
                SXSSFRow row = sheet.createRow(i);
                for (int j = 0; j < 25; j++) {
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue("str-" + j + "-" + i);
                }
                if (i % 5000 == 0) {
                    LOGGER.info("{} write success.", i);
                }
            }
            workbook.write(fileOutputStream);
            workbook.dispose();
            workbook.close();
        }
        long costPoi = System.currentTimeMillis() - start;
        LOGGER.info("poi write cost:{}", System.currentTimeMillis() - start);
        LOGGER.info("{} vs {}", cost, costPoi);

        Assert.assertTrue(costPoi * 3 > cost);
    }

    @Test
    public void t04Write44() throws Exception {
        //read();
        long start = System.currentTimeMillis();

        for (int j = 0; j < 20; j++) {
            List<LargeData> data = data();
            LOGGER.info("" + data.size());
        }

        LOGGER.info("poi写入消费:{}", System.currentTimeMillis() - start);

    }

    @Test
    public void t04Write445() throws Exception {
        //read();
        long start = System.currentTimeMillis();

        for (int j = 0; j < 20; j++) {
            List<LargeData> data = data2();
            LOGGER.info("" + data.size());
        }

        LOGGER.info("poi写入消费:{}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        LargeData largeData = new LargeData();
        for (int j = 0; j < 20 * 5000; j++) {
            largeData.setStr1("1");
            largeData.setStr2("2");
            largeData.setStr3("3");
        }

        LOGGER.info("poi写入消费:{}", System.currentTimeMillis() - start);

    }

    @Test
    public void t04Writev3() throws Exception {
        //read();
        read();

    }

    @Test
    public void t04Write() throws Exception {
        WriteHandler cellhanderl1 = new Cellhanderl();
        WriteHandler cellhanderl2 = new Cellhanderl();
        WriteHandler cellhanderl3 = new Cellhanderl();
        WriteHandler cellhanderl4 = new Cellhanderl();
        List<WriteHandler> list = new ArrayList<>();
        list.add(cellhanderl1);
        list.add(cellhanderl2);
        list.add(cellhanderl3);

        //list.add(cellhanderl4);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 6250025; i++) {
            CellWriteHandlerContext content=new CellWriteHandlerContext();
            content.setColumnIndex(99);
            for (int j = 0; j < list.size(); j++) {
                WriteHandler writeHandler = list.get(0);
                ((Cellhanderl)writeHandler).beforeCellCreate(content);
                ((Cellhanderl)writeHandler).afterCellCreate(content);
                ((Cellhanderl)writeHandler).afterCellDataConverted(content);
                ((Cellhanderl)writeHandler).afterCellDispose(content);
            }
        }
        LOGGER.info("第一次:{}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < 6250025; i++) {
            CellWriteHandlerContext content=new CellWriteHandlerContext();
            content.setColumnIndex(99);
            for (WriteHandler writeHandler : list) {

                ((Cellhanderl)writeHandler).beforeCellCreate(content);
                ((Cellhanderl)writeHandler).afterCellCreate(content);
                ((Cellhanderl)writeHandler).afterCellDataConverted(content);
                ((Cellhanderl)writeHandler).afterCellDispose(content);
            }
        }
        LOGGER.info("第二次:{}", System.currentTimeMillis() - start);
        //
        //start = System.currentTimeMillis();
        //for (int i = 0; i < 6250025; i++) {
        //    cellhanderl1.beforeCellCreate(null);
        //    cellhanderl1.afterCellCreate(null);
        //    cellhanderl1.afterCellDataConverted(null);
        //    cellhanderl1.afterCellDispose(null);
        //    cellhanderl2.beforeCellCreate(null);
        //    cellhanderl2.afterCellCreate(null);
        //    cellhanderl2.afterCellDataConverted(null);
        //    cellhanderl2.afterCellDispose(null);
        //    cellhanderl3.beforeCellCreate(null);
        //    cellhanderl3.afterCellCreate(null);
        //    cellhanderl3.afterCellDataConverted(null);
        //    cellhanderl3.afterCellDispose(null);
        //
        //}
        //LOGGER.info("第三次:{}", System.currentTimeMillis() - start);
        //
        //start = System.currentTimeMillis();
        //for (int i = 0; i < 25000075; i++) {
        //    Iterator<Cellhanderl> iterator = list.iterator();
        //    while (iterator.hasNext()) {
        //        Cellhanderl writeHandler = iterator.next();
        //        writeHandler.beforeCellCreate(null);
        //        writeHandler.afterCellCreate(null);
        //        writeHandler.afterCellDataConverted(null);
        //        writeHandler.afterCellDispose(null);
        //    }
        //}
        //
        //LOGGER.info("第四次:{}", System.currentTimeMillis() - start);
        //
        //start = System.currentTimeMillis();
        //for (int i = 0; i < 25000075; i++) {
        //    list.stream().forEach(writeHandler -> {
        //        writeHandler.beforeCellCreate(null);
        //        writeHandler.afterCellCreate(null);
        //        writeHandler.afterCellDataConverted(null);
        //        writeHandler.afterCellDispose(null);
        //    });
        //}
        //
        //LOGGER.info("第五次:{}", System.currentTimeMillis() - start);
    }

    private void v2(Cellhanderl cellhanderl1) {
        cellhanderl1.beforeCellCreate(null);
        cellhanderl1.afterCellCreate(null);
        cellhanderl1.afterCellDataConverted(null);
        cellhanderl1.afterCellDispose(null);
    }

    @Test
    public void t04Writev32() throws Exception {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/zhuangjiaju/test");
        //read();
        Thread.sleep(10*1000);
        read();

    }


    @Test
    public void t04Writev33() throws Exception {
        //read();
        //Thread.sleep(10*1000);

        read();

    }


    private void read() throws Exception {
        long start = System.currentTimeMillis();
        //try (FileOutputStream fileOutputStream = new FileOutputStream(fileWritePoi07)) {
        //    SXSSFWorkbook workbook = new SXSSFWorkbook();
        //    SXSSFSheet sheet = workbook.createSheet("sheet1");
        //    for (int i = 0; i < 20 * 5000; i++) {
        //        SXSSFRow row = sheet.createRow(i);
        //        for (int j = 0; j < 25; j++) {
        //            SXSSFCell cell = row.createCell(j);
        //            cell.setCellValue("str-" + j + "-" + i);
        //        }
        //        if (i % 5000 == 0) {
        //            //LOGGER.info("{} write success.", i);
        //        }
        //    }
        //    workbook.write(fileOutputStream);
        //    workbook.dispose();
        //    workbook.close();
        //}
        long costPoi = System.currentTimeMillis() - start;
        LOGGER.info("poi写入消费:{}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        ExcelWriter excelWriter = EasyExcel.write(fileWrite07, LargeData.class)
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            //.registerWriteHandler(new Cellhanderl())
            .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 50; j++) {
            long s1 = System.currentTimeMillis();
            excelWriter.write(data(), writeSheet);
            LOGGER.info("平均.{}", System.currentTimeMillis() - s1);
        }
        excelWriter.finish();
        long cost = System.currentTimeMillis() - start;
        LOGGER.info("easyxcel写入:{}", cost);
        //LOGGER.info("easyxcel写入:{}", FillStyleCellWriteHandler.count);
        LOGGER.info("easyxcel写入:{}", Cellhanderl.cout);
        LOGGER.info("easyxcel写入:{}", CostUtil.count2);
        LOGGER.info("easyxcel写入:{}", CostUtil.count);
        LOGGER.info("easyxcel写入:{}", FillStyleCellWriteHandler.count);
        LOGGER.info("easyxcel写入:{}", WriteWorkbookHolder.count);

    }

    private List<LargeData> data() {
        List<LargeData> list = new ArrayList<>();
        int size = i + 5000;
        for (; i < size; i++) {
            LargeData largeData = new LargeData();
            list.add(largeData);
            largeData.setStr1("str1-" + i);
            largeData.setStr2("str2-" + i);
            largeData.setStr3("str3-" + i);
            largeData.setStr4("str4-" + i);
            largeData.setStr5("str5-" + i);
            largeData.setStr6("str6-" + i);
            largeData.setStr7("str7-" + i);
            largeData.setStr8("str8-" + i);
            largeData.setStr9("str9-" + i);
            largeData.setStr10("str10-" + i);
            largeData.setStr11("str11-" + i);
            largeData.setStr12("str12-" + i);
            largeData.setStr13("str13-" + i);
            largeData.setStr14("str14-" + i);
            largeData.setStr15("str15-" + i);
            largeData.setStr16("str16-" + i);
            largeData.setStr17("str17-" + i);
            largeData.setStr18("str18-" + i);
            largeData.setStr19("str19-" + i);
            largeData.setStr20("str20-" + i);
            largeData.setStr21("str21-" + i);
            largeData.setStr22("str22-" + i);
            largeData.setStr23("str23-" + i);
            largeData.setStr24("str24-" + i);
            largeData.setStr25("str25-" + i);
        }
        return list;
    }

    private List<LargeData> data2() {
        List<LargeData> list = new ArrayList<>();
        int size = i + 5000;
        for (; i < size; i++) {
            LargeData largeData = new LargeData();
            list.add(largeData);
            largeData.setStr1("1");
            largeData.setStr2("2");
            largeData.setStr3("3");
        }
        return list;
    }
}
