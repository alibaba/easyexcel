package com.alibaba.easyexcel.test.temp;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.PositionUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Slf4j
public class Lock2Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Lock2Test.class);

    @Test
    public void test() throws Exception {
        // File file = TestFileUtil.readUserHomeFile("test/test4.xlsx");
        //        File file = TestFileUtil.readUserHomeFile("test/test6.xls");
        File file = new File("/Users/zhuangjiaju/IdeaProjects/easyexcel/src/test/resources/converter/converter07.xlsx");

        List<Object> list = EasyExcel.read(
                "/Users/zhuangjiaju/Downloads/证券投资基金估值表_外贸信托-稳盈淳享37号集合资金信托计划_2024-07-23(1).xls")
            //.useDefaultListener(false)
            .sheet(0)
            .headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (Object data : list) {
            LOGGER.info("返回数据：{}", CollectionUtils.size(data));
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test33() throws Exception {
        File file = TestFileUtil.readUserHomeFile("test/test6.xlsx");

        EasyExcel.read(file, LockData.class, new LockDataListener()).sheet(0).headRowNumber(0)
            .doRead();

    }

    @Test
    public void write() throws Exception {
        String fileName = TestFileUtil.getPath() + "styleWrite" + System.currentTimeMillis() + ".xlsx";
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

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
            .doWrite(data());
    }

    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + System.currentTimeMillis() + ".xlsx";
        System.out.println(fileName);
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("表头");

        list.add(head0);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        List<Object> data = new ArrayList<Object>();
        data.add("字符串");
        data.add(new Date());
        data.add(0.56);
        list.add(data);
        return list;
    }

    @Test
    public void testc() throws Exception {
        LOGGER.info("reslut:{}", JSON.toJSONString(new CellReference("B3")));
    }

    @Test
    public void simpleRead() {
        // 写法1：
        String fileName = "D:\\test\\珠海 (1).xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, LockData.class, new LockDataListener()).useDefaultListener(false).sheet().doRead();
    }

    @Test
    public void test2() throws Exception {
        File file = new File("D:\\test\\converter03.xls");

        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
        LOGGER.info("文件状态：{}", file.exists());
        file.delete();
        Thread.sleep(500 * 1000);
    }

    @Test
    public void test335() throws Exception {

        LOGGER.info("reslut:{}", PositionUtils.getCol("A10", null));
        LOGGER.info("reslut:{}", PositionUtils.getRow("A10"));
        LOGGER.info("reslut:{}", PositionUtils.getCol("AB10", null));
        LOGGER.info("reslut:{}", PositionUtils.getRow("AB10"));

        //LOGGER.info("reslut:{}", PositionUtils2.getCol("A10",null));
        //LOGGER.info("reslut:{}", PositionUtils2.getRow("A10"));
        //LOGGER.info("reslut:{}", PositionUtils2.getCol("AB10",null));
        //LOGGER.info("reslut:{}", PositionUtils2.getRow("AB10"));
    }

    @Test
    public void numberforamt() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44727.99998842592), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));
        //
        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44728.99998842592), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));
        //
        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44729.99998836806), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));
        //
        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44727.99998842592).setScale(10, RoundingMode
        //    .HALF_UP), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));
        //
        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44728.99998842592).setScale(10, RoundingMode
        //    .HALF_UP), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));

        //44729.9999883681
        //44729.999988368058
        //LOGGER.info("date:{}",
        //    NumberDataFormatterUtils.format(BigDecimal.valueOf(44729.999988368058).setScale(10, RoundingMode
        //    .HALF_UP), (short)200, "yyyy-MM-dd HH:mm:ss",
        //        null,
        //        null, null));
        //LOGGER.info("date:{}",BigDecimal.valueOf(44729.999988368058).setScale(10, RoundingMode.HALF_UP).doubleValue
        // ());

        // 2022/6/17 23:59:59
        // 期望 44729.99998842592
        //LOGGER.info("data:{}", DateUtil.getJavaDate(44729.9999883681, true));
        LOGGER.info("data4:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999988368058)
            .setScale(4, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data5:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999988368058)
            .setScale(5, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data6:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999988368058)
            .setScale(6, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data7:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999988368058)
            .setScale(7, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data8:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999988368058)
            .setScale(8, RoundingMode.HALF_UP).doubleValue(), false));

        LOGGER.info("data:{}", format.format(DateUtil.getJavaDate(44729.999988368058, false)));
        LOGGER.info("data:{}", format.format(DateUtil.getJavaDate(44729.9999883681, false)));

        LOGGER.info("data:{}", DateUtil.getJavaDate(Double.parseDouble("44729.999988368058"), false));
        LOGGER.info("data:{}", DateUtil.getJavaDate(Double.parseDouble("44729.9999883681"), false));

        // 44729.999976851854
        // 44729.999988368058
        LOGGER.info("data:{}", DateUtil.getExcelDate(format.parse("2022-06-17 23:59:58")));
        // 44729.99998842592
        LOGGER.info("data:{}", DateUtil.getExcelDate(format.parse("2022-06-17 23:59:59")));

        LOGGER.info("data:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999976851854)
            .setScale(10, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.99998842592)
            .setScale(10, RoundingMode.HALF_UP).doubleValue(), false));

        LOGGER.info("data:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.999976851854)
            .setScale(5, RoundingMode.HALF_UP).doubleValue(), false));
        LOGGER.info("data:{}", DateUtil.getJavaDate(BigDecimal.valueOf(44729.99998842592)
            .setScale(5, RoundingMode.HALF_UP).doubleValue(), false));
    }

    @Test
    public void testDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("TT:{}", format.format(new Date(100L)));
        log.info("TT:{}", new Date().getTime());
    }

    @Test
    public void testDateAll() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        long dateTime = 0L;
        while (true) {
            Date date = new Date(dateTime);
            double excelDate = DateUtil.getExcelDate(date);

            Assertions.assertEquals("测试基本转换错误" + dateTime, format.format(date),
                format.format(DateUtil.getJavaDate(excelDate, false)));
            Assertions.assertEquals("测试精度5转换错误" + dateTime, format.format(date),
                format.format(DateUtil.getJavaDate(BigDecimal.valueOf(excelDate)
                    .setScale(10, RoundingMode.HALF_UP).doubleValue(), false)));
            LOGGER.info("date:{}", format2.format(DateUtil.getJavaDate(BigDecimal.valueOf(excelDate)
                .setScale(10, RoundingMode.HALF_UP).doubleValue())));
            dateTime += 1000L;
            // 30天输出
            if (dateTime % (24 * 60 * 60 * 1000) == 0) {
                log.info("{}成功", format.format(date));
            }
            if (dateTime > 1673957544750L) {
                log.info("结束啦");
                break;
            }
        }
        log.info("结束啦");

    }

    @Test
    public void numberforamt3() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<Map<Integer, ReadCellData>> list = EasyExcel.read("/Users/zhuangjiaju/Downloads/date3.xlsx")
            .useDefaultListener(false)
            .sheet(0)
            .headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (Map<Integer, ReadCellData> readCellDataMap : list) {
            ReadCellData data = readCellDataMap.get(0);
            LOGGER.info("data:{}", format.format(
                DateUtil.getJavaDate(data.getNumberValue().setScale(10, RoundingMode.HALF_UP).doubleValue(), false)));

        }
        //
        //LOGGER.info("data:{}", format.format(DateUtil.getJavaDate(44727.999988425923, false)));
        //LOGGER.info("data:{}", format.format(DateUtil.getJavaDate(44729.999988368058, false)));

    }

    @Test
    public void numberforamt4() throws Exception {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
            .sheet("模板")
            .doWrite(() -> {
                // 分页查询数据
                return data2();
            });

    }

    @Test
    public void numberforamt77() throws Exception {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData3.class)
            .sheet("模板")
            .doWrite(() -> {
                List<DemoData3> list = new ArrayList<>();
                DemoData3 demoData3 = new DemoData3();
                demoData3.setLocalDateTime(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 400000000));
                list.add(demoData3);
                demoData3 = new DemoData3();
                demoData3.setLocalDateTime(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 499000000));
                list.add(demoData3);
                demoData3 = new DemoData3();
                demoData3.setLocalDateTime(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 500000000));
                list.add(demoData3);
                demoData3 = new DemoData3();
                demoData3.setLocalDateTime(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 501000000));
                list.add(demoData3);
                demoData3 = new DemoData3();
                demoData3.setLocalDateTime(LocalDateTime.of(2023, 1, 1, 0, 0, 0, 995000000));
                list.add(demoData3);
                return list;
            });

    }

    @Test
    public void numberforamt99() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0, 995000000);
        log.info("date:{}", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

    }

    @Test
    public void numberforamt5() throws Exception {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
            .sheet("模板")
            .doWrite(() -> {
                // 分页查询数据
                return data3();
            });

    }

    @Test
    public void numberforamt6() throws Exception {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        BigDecimal bigDecimal = new BigDecimal(3101011021236149800L);
        log.info("b:{}", bigDecimal);
        log.info("b:{}", bigDecimal.setScale(-4, RoundingMode.HALF_UP));
        log.info("b:{}", decimalFormat.format(bigDecimal.setScale(-4, RoundingMode.HALF_UP)));

    }

    @Test
    public void numberforamt7() throws Exception {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        BigDecimal bigDecimal = new BigDecimal(3.1010110212361498E+18).round(new MathContext(15, RoundingMode.HALF_UP));
        //bigDecimal.

        // bigDecimal
        log.info("b:{}", bigDecimal);
        log.info("b:{}", bigDecimal.setScale(-4, RoundingMode.HALF_UP));
        log.info("b:{}", decimalFormat.format(bigDecimal.setScale(-4, RoundingMode.HALF_UP)));
        log.info("b:{}", decimalFormat.format(bigDecimal));

    }

    private List<DemoData2> data3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<DemoData2> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData2 data = new DemoData2();
            data.setString("字符串" + i);
            data.setDoubleData(0.56);
            data.setBigDecimal(BigDecimal.valueOf(3101011021236149800L));
            list.add(data);
        }
        return list;
    }

    private List<DemoData> data() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            try {
                data.setDate(format.parse("2032-01-18 09:00:01.995"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    private List<DemoData> data2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            try {
                data.setDate(format.parse("2032-01-18 09:00:00."));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
