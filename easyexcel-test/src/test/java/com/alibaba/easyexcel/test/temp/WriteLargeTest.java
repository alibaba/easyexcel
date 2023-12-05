package com.alibaba.easyexcel.test.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.core.large.LargeData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/

@Slf4j
public class WriteLargeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteLargeTest.class);

    @Test
    public void test() throws Exception {
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = TestFileUtil.getPath() + "large" + System.currentTimeMillis() + ".xlsx";
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriter excelWriter = EasyExcel.write(fileName, LargeData.class).registerWriteHandler(
            horizontalCellStyleStrategy).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();

    }

    @Test
    public void read() throws Exception {
        log.info("start");
        String fileName = "/Users/zhuangjiaju/Downloads/1e9e0578a9634abbbbd9b67f338f142a.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(fileName, new PageReadListener<List<Map<String, String>>>(dataList -> {
            log.info("SIZEL：{}", dataList.size());
        })).sheet().doRead();

        log.info("test");

    }

    @Test
    public void read2() throws Exception {
        // 使用输入的文件创建一个新的文件输入流
        //FileInputStream fin = new FileInputStream("/Users/zhuangjiaju/Downloads/1e9e0578a9634abbbbd9b67f338f142a
        // .xls");
        // 创建一个新的org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(
            new File("/Users/zhuangjiaju/Downloads/1e9e0578a9634abbbbd9b67f338f142a.xls"));
        // 在InputStream中获取Workbook流
        InputStream din = poifs.createDocumentInputStream("Workbook");
        // 构造出HSSFRequest对象
        HSSFRequest req = new HSSFRequest();
        // 注册全部的监听器
        req.addListenerForAllRecords(new EventExample());
        // 创建事件工厂
        HSSFEventFactory factory = new HSSFEventFactory();
        // 根据文档输入流处理我们监听的事件
        factory.processEvents(req, din);
        // 关闭文件输入流
        //fin.close();
        // 关闭文档输入流
        din.close();
        System.out.println("读取结束");
    }

    @Test
    public void read3() throws Exception {
        HSSFWorkbook hwb = new HSSFWorkbook(
            new FileInputStream("/Users/zhuangjiaju/Downloads/1e9e0578a9634abbbbd9b67f338f142a.xls"));
        HSSFSheet sheet = hwb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if(row!=null){
                log.info("r:{}",row.getRowNum());

            }
        }

        log.info("end");
    }

    public static class EventExample implements HSSFListener {
        private SSTRecord sstrec;

        /**
         * 此方法监听传入记录并根据需要处理它们
         *
         * @param record读取时找到的记录
         */
        public void processRecord(Record record) {
            switch (record.getSid()) {
                //BOFRecord可以表示工作表或工作簿的开头
                case BOFRecord.sid:
                    BOFRecord bof = (BOFRecord)record;
                    if (bof.getType() == bof.TYPE_WORKBOOK) {
                        System.out.println("监听到工作表");
                    } else if (bof.getType() == bof.TYPE_WORKSHEET) {
                        System.out.println("监听到工作簿");
                    }
                    break;
                case BoundSheetRecord.sid:
                    BoundSheetRecord bsr = (BoundSheetRecord)record;
                    System.out.println("工作簿名称: " + bsr.getSheetname());
                    break;
            }
        }
    }

    @Test
    public void test2() throws Exception {
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = TestFileUtil.getPath() + "large" + System.currentTimeMillis() + ".xlsx";

        ExcelWriter excelWriter = EasyExcel.write(fileName, LargeData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();

    }

    private List<List<String>> data() {
        List<List<String>> list = new ArrayList<>();

        for (int j = 0; j < 10000; j++) {
            List<String> oneRow = new ArrayList<>();
            for (int i = 0; i < 150; i++) {
                oneRow.add("这是测试字段" + i);
            }
            list.add(oneRow);
        }

        return list;
    }
}
