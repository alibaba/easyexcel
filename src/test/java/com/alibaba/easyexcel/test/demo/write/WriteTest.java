package com.alibaba.easyexcel.test.demo.write;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

/**
 * 写的常见写法
 *
 * @author Jiaju Zhuang
 */
@Ignore
public class WriteTest {
    /**
     * 最简单的写
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcelFactory.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        // 写法2
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").build();
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    /**
     * 指定写入的列
     * <li>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <li>2. 使用{@link ExcelProperty}注解指定写入的列
     * <li>3. 直接写即可
     */
    @Test
    public void indexWrite() {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }

    /**
     * 复杂头写入
     * <li>1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <li>2. 使用{@link ExcelProperty}注解指定复杂的头
     * <li>3. 直接写即可
     */
    @Test
    public void complexHeadWrite() {
        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 重复多次写入
     * <li>1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <li>2. 使用{@link ExcelProperty}注解指定复杂的头
     * <li>3. 直接调用二次写入即可
     */
    @Test
    public void repeatedWrite() {
        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").build();
        // 第一次写入会创建头
        excelWriter.write(data(), writeSheet);
        // 第二次写入会在上一次写入的最后一行后面写入
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    /**
     * 日期、数字或者自定义格式转换
     * <li>1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <li>2. 使用{@link ExcelProperty}配合使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <li>3. 直接写即可
     */
    @Test
    public void converterWrite() {
        String fileName = TestFileUtil.getPath() + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, ConverterData.class).sheet("模板").doWrite(data());
    }

    /**
     * 根据模板写入
     * <li>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <li>2. 使用{@link ExcelProperty}注解指定写入的列
     * <li>3. 使用withTemplate 读取模板
     * <li>4. 直接写即可
     */
    @Test
    public void templateWrite() {
        String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "templateWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).withTemplate(templateFileName).sheet().doWrite(data());
    }

    /**
     * 列宽、行高
     * <li>1. 创建excel对应的实体对象 参照{@link WidthAndHeightData}
     * <li>2. 使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight}指定宽度或高度
     * <li>3. 直接写即可
     */
    @Test
    public void widthAndHeightWrite() {
        String fileName = TestFileUtil.getPath() + "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(data());
    }

    /**
     * 自定义样式
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 创建一个style策略 并注册
     * <li>3. 直接写即可
     */
    @Test
    public void styleWrite() {
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

        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
            .doWrite(data());
    }

    /**
     * 合并单元格
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 创建一个merge策略 并注册
     * <li>3. 直接写即可
     */
    @Test
    public void mergeWrite() {
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcelFactory.write(fileName, DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("模板")
            .doWrite(data());
    }

    /**
     * 使用table去写入
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 然后写入table即可
     */
    @Test
    public void tableWrite() {
        String fileName = TestFileUtil.getPath() + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去读
        ExcelWriter excelWriter = EasyExcelFactory.write(fileName, DemoData.class).build();
        // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
        WriteSheet writeSheet = EasyExcelFactory.writerSheet("模板").needHead(Boolean.FALSE).build();
        // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
        WriteTable writeTable0 = EasyExcelFactory.writerTable(0).needHead(Boolean.TRUE).build();
        WriteTable writeTable1 = EasyExcelFactory.writerTable(1).needHead(Boolean.TRUE).build();
        // 第一次写入会创建头
        excelWriter.write(data(), writeSheet, writeTable0);
        // 第二次写如也会创建头，然后在第一次的后面写入数据
        excelWriter.write(data(), writeSheet, writeTable1);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
