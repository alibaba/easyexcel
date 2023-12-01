package com.alibaba.easyexcel.test.demo.write;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.easyexcel.test.core.head.ComplexHeadData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.CommentData;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.HyperlinkData.HyperlinkType;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.ImageData.ImageType;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.junit.jupiter.api.Test;

/**
 * 写的常见写法
 *
 * @author Jiaju Zhuang
 */

public class WriteTest {

    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入

        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".csv";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
            .excelType(ExcelTypeEnum.CSV)
            .sheet("模板")
            .doWrite(() -> {
                // 分页查询数据
                return data();
            });

        // 写法2
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());

        // 写法3
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(), writeSheet);
        }
    }

    /**
     * 根据参数只导出指定列
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 根据自己或者排除自己需要的列
     * <p>
     * 3. 直接写即可
     *
     * @since 2.1.1
     */
    @Test
    public void excludeOrIncludeWrite() {
        String fileName = TestFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里需要注意 在使用ExcelProperty注解的使用，如果想不空列则需要加入order字段，而不是index,order会忽略空列，然后继续往后，而index，不会忽略空列，在第几列就是第几列。

        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFieldNames = new HashSet<>();
        excludeColumnFieldNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).excludeColumnFieldNames(excludeColumnFieldNames).sheet("模板")
            .doWrite(data());

        fileName = TestFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        // 根据用户传入字段 假设我们只要导出 date
        Set<String> includeColumnFieldNames = new HashSet<>();
        includeColumnFieldNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).includeColumnFieldNames(includeColumnFieldNames).sheet("模板")
            .doWrite(data());
    }

    /**
     * 指定写入的列
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定写入的列
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void indexWrite() {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }

    /**
     * 复杂头写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void complexHeadWrite() {
        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 重复多次写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3. 直接调用二次写入即可
     */
    @Test
    public void repeatedWrite() {
        // 方法1: 如果写到同一个sheet
        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        }

        // 方法2: 如果写到不同的sheet 同一个对象
        fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        }

        // 方法3 如果写到不同的sheet 不同的对象
        fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class
                // 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoData.class).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        }

    }

    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>
     * 2. 使用{@link ExcelProperty}配合使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void converterWrite() {
        String fileName = TestFileUtil.getPath() + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).sheet("模板").doWrite(data());
    }

    /**
     * 图片导出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ImageDemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void imageWrite() throws Exception {
        String fileName = TestFileUtil.getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";

        // 这里注意下 所有的图片都会放到内存 暂时没有很好的解法，大量图片的情况下建议 2选1:
        // 1. 将图片上传到oss 或者其他存储网站: https://www.aliyun.com/product/oss ，然后直接放链接
        // 2. 使用: https://github.com/coobird/thumbnailator 或者其他工具压缩图片

        String imagePath = TestFileUtil.getPath() + "converter" + File.separator + "img.jpg";
        try (InputStream inputStream = FileUtils.openInputStream(new File(imagePath))) {
            List<ImageDemoData> list = ListUtils.newArrayList();
            ImageDemoData imageDemoData = new ImageDemoData();
            list.add(imageDemoData);
            // 放入五种类型的图片 实际使用只要选一种即可
            imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageDemoData.setFile(new File(imagePath));
            imageDemoData.setString(imagePath);
            imageDemoData.setInputStream(inputStream);
            imageDemoData.setUrl(new URL(
                "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));

            // 这里演示
            // 需要额外放入文字
            // 而且需要放入2个图片
            // 第一个图片靠左
            // 第二个靠右 而且要额外的占用他后面的单元格
            WriteCellData<Void> writeCellData = new WriteCellData<>();
            imageDemoData.setWriteCellDataFile(writeCellData);
            // 这里可以设置为 EMPTY 则代表不需要其他数据了
            writeCellData.setType(CellDataTypeEnum.STRING);
            writeCellData.setStringValue("额外的放一些文字");

            // 可以放入多个图片
            List<ImageData> imageDataList = new ArrayList<>();
            ImageData imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            // 放入2进制图片
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            // 图片类型
            imageData.setImageType(ImageType.PICTURE_TYPE_PNG);
            // 上 右 下 左 需要留空
            // 这个类似于 css 的 margin
            // 这里实测 不能设置太大 超过单元格原始大小后 打开会提示修复。暂时未找到很好的解法。
            imageData.setTop(5);
            imageData.setRight(40);
            imageData.setBottom(5);
            imageData.setLeft(5);

            // 放入第二个图片
            imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setImageType(ImageType.PICTURE_TYPE_PNG);
            imageData.setTop(5);
            imageData.setRight(5);
            imageData.setBottom(5);
            imageData.setLeft(50);
            // 设置图片的位置 假设 现在目标 是 覆盖 当前单元格 和当前单元格右边的单元格
            // 起点相对于当前单元格为0 当然可以不写
            imageData.setRelativeFirstRowIndex(0);
            imageData.setRelativeFirstColumnIndex(0);
            imageData.setRelativeLastRowIndex(0);
            // 前面3个可以不写  下面这个需要写 也就是 结尾 需要相对当前单元格 往右移动一格
            // 也就是说 这个图片会覆盖当前单元格和 后面的那一格
            imageData.setRelativeLastColumnIndex(1);

            // 写入数据
            EasyExcel.write(fileName, ImageDemoData.class).sheet().doWrite(list);
        }
    }

    /**
     * 超链接、备注、公式、指定单个单元格的样式、单个单元格多种样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link WriteCellDemoData}
     * <p>
     * 2. 直接写即可
     *
     * @since 3.0.0-beta1
     */
    @Test
    public void writeCellDataWrite() {
        String fileName = TestFileUtil.getPath() + "writeCellDataWrite" + System.currentTimeMillis() + ".xlsx";
        WriteCellDemoData writeCellDemoData = new WriteCellDemoData();

        // 设置超链接
        WriteCellData<String> hyperlink = new WriteCellData<>("官方网站");
        writeCellDemoData.setHyperlink(hyperlink);
        HyperlinkData hyperlinkData = new HyperlinkData();
        hyperlink.setHyperlinkData(hyperlinkData);
        hyperlinkData.setAddress("https://github.com/alibaba/easyexcel");
        hyperlinkData.setHyperlinkType(HyperlinkType.URL);

        // 设置备注
        WriteCellData<String> comment = new WriteCellData<>("备注的单元格信息");
        writeCellDemoData.setCommentData(comment);
        CommentData commentData = new CommentData();
        comment.setCommentData(commentData);
        commentData.setAuthor("Jiaju Zhuang");
        commentData.setRichTextStringData(new RichTextStringData("这是一个备注"));
        // 备注的默认大小是按照单元格的大小 这里想调整到4个单元格那么大 所以向后 向下 各额外占用了一个单元格
        commentData.setRelativeLastColumnIndex(1);
        commentData.setRelativeLastRowIndex(1);

        // 设置公式
        WriteCellData<String> formula = new WriteCellData<>();
        writeCellDemoData.setFormulaData(formula);
        FormulaData formulaData = new FormulaData();
        formula.setFormulaData(formulaData);
        // 将 123456789 中的第一个数字替换成 2
        // 这里只是例子 如果真的涉及到公式 能内存算好尽量内存算好 公式能不用尽量不用
        formulaData.setFormulaValue("REPLACE(123456789,1,1,2)");

        // 设置单个单元格的样式 当然样式 很多的话 也可以用注解等方式。
        WriteCellData<String> writeCellStyle = new WriteCellData<>("单元格样式");
        writeCellStyle.setType(CellDataTypeEnum.STRING);
        writeCellDemoData.setWriteCellStyle(writeCellStyle);
        WriteCellStyle writeCellStyleData = new WriteCellStyle();
        writeCellStyle.setWriteCellStyle(writeCellStyleData);
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.
        writeCellStyleData.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        writeCellStyleData.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        // 设置单个单元格多种样式
        // 这里需要设置 inMomery=true 不然会导致无法展示单个单元格多种样式，所以慎用
        WriteCellData<String> richTest = new WriteCellData<>();
        richTest.setType(CellDataTypeEnum.RICH_TEXT_STRING);
        writeCellDemoData.setRichText(richTest);
        RichTextStringData richTextStringData = new RichTextStringData();
        richTest.setRichTextStringDataValue(richTextStringData);
        richTextStringData.setTextString("红色绿色默认");
        // 前2个字红色
        WriteFont writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.RED.getIndex());
        richTextStringData.applyFont(0, 2, writeFont);
        // 接下来2个字绿色
        writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.GREEN.getIndex());
        richTextStringData.applyFont(2, 4, writeFont);

        List<WriteCellDemoData> data = new ArrayList<>();
        data.add(writeCellDemoData);
        EasyExcel.write(fileName, WriteCellDemoData.class).inMemory(true).sheet("模板").doWrite(data);
    }

    /**
     * 根据模板写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>
     * 2. 使用{@link ExcelProperty}注解指定写入的列
     * <p>
     * 3. 使用withTemplate 写取模板
     * <p>
     * 4. 直接写即可
     */
    @Test
    public void templateWrite() {
        String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "templateWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 这里要注意 withTemplate 的模板文件会全量存储在内存里面，所以尽量不要用于追加文件，如果文件模板文件过大会OOM
        // 如果要再文件中追加（无法在一个线程里面处理，可以在一个线程的建议参照多次写入的demo） 建议临时存储到数据库 或者 磁盘缓存(ehcache) 然后再一次性写入
        EasyExcel.write(fileName, DemoData.class).withTemplate(templateFileName).sheet().doWrite(data());
    }

    /**
     * 列宽、行高
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link WidthAndHeightData }
     * <p>
     * 2. 使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight}指定宽度或高度
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void widthAndHeightWrite() {
        String fileName = TestFileUtil.getPath() + "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(data());
    }

    /**
     * 注解形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoStyleData}
     * <p>
     * 3. 直接写即可
     *
     * @since 2.2.0-beta1
     */
    @Test
    public void annotationStyleWrite() {
        String fileName = TestFileUtil.getPath() + "annotationStyleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoStyleData.class).sheet("模板").doWrite(data());
    }

    /**
     * 拦截器形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 创建一个style策略 并注册
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void handlerStyleWrite() {
        // 方法1 使用已有的策略 推荐
        // HorizontalCellStyleStrategy 每一行的样式都一样 或者隔行一样
        // AbstractVerticalCellStyleStrategy 每一列的样式都一样 需要自己回调每一页
        String fileName = TestFileUtil.getPath() + "handlerStyleWrite" + System.currentTimeMillis() + ".xlsx";
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
        EasyExcel.write(fileName, DemoData.class)
            .registerWriteHandler(horizontalCellStyleStrategy)
            .sheet("模板")
            .doWrite(data());

        // 方法2: 使用easyexcel的方式完全自己写 不太推荐 尽量使用已有策略
        // @since 3.0.0-beta2
        fileName = TestFileUtil.getPath() + "handlerStyleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, DemoData.class)
            .registerWriteHandler(new CellWriteHandler() {
                @Override
                public void afterCellDispose(CellWriteHandlerContext context) {
                    // 当前事件会在 数据设置到poi的cell里面才会回调
                    // 判断不是头的情况 如果是fill 的情况 这里会==null 所以用not true
                    if (BooleanUtils.isNotTrue(context.getHead())) {
                        // 第一个单元格
                        // 只要不是头 一定会有数据 当然fill的情况 可能要context.getCellDataList() ,这个需要看模板，因为一个单元格会有多个 WriteCellData
                        WriteCellData<?> cellData = context.getFirstCellData();
                        // 这里需要去cellData 获取样式
                        // 很重要的一个原因是 WriteCellStyle 和 dataFormatData绑定的 简单的说 比如你加了 DateTimeFormat
                        // ，已经将writeCellStyle里面的dataFormatData 改了 如果你自己new了一个WriteCellStyle，可能注解的样式就失效了
                        // 然后 getOrCreateStyle 用于返回一个样式，如果为空，则创建一个后返回
                        WriteCellStyle writeCellStyle = cellData.getOrCreateStyle();
                        writeCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                        writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

                        // 这样样式就设置好了 后面有个FillStyleCellWriteHandler 默认会将 WriteCellStyle 设置到 cell里面去 所以可以不用管了
                    }
                }
            }).sheet("模板")
            .doWrite(data());

        // 方法3: 使用poi的样式完全自己写 不推荐
        // @since 3.0.0-beta2
        // 坑1：style里面有dataformat 用来格式化数据的 所以自己设置可能导致格式化注解不生效
        // 坑2：不要一直去创建style 记得缓存起来 最多创建6W个就挂了
        fileName = TestFileUtil.getPath() + "handlerStyleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, DemoData.class)
            .registerWriteHandler(new CellWriteHandler() {
                @Override
                public void afterCellDispose(CellWriteHandlerContext context) {
                    // 当前事件会在 数据设置到poi的cell里面才会回调
                    // 判断不是头的情况 如果是fill 的情况 这里会==null 所以用not true
                    if (BooleanUtils.isNotTrue(context.getHead())) {
                        Cell cell = context.getCell();
                        // 拿到poi的workbook
                        Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
                        // 这里千万记住 想办法能复用的地方把他缓存起来 一个表格最多创建6W个样式
                        // 不同单元格尽量传同一个 cellStyle
                        CellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell.setCellStyle(cellStyle);

                        // 由于这里没有指定dataformat 最后展示的数据 格式可能会不太正确

                        // 这里要把 WriteCellData的样式清空， 不然后面还有一个拦截器 FillStyleCellWriteHandler 默认会将 WriteCellStyle 设置到
                        // cell里面去 会导致自己设置的不一样
                        context.getFirstCellData().setWriteCellStyle(null);
                    }
                }
            }).sheet("模板")
            .doWrite(data());
    }

    /**
     * 合并单元格
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData} {@link DemoMergeData}
     * <p>
     * 2. 创建一个merge策略 并注册
     * <p>
     * 3. 直接写即可
     *
     * @since 2.2.0-beta1
     */
    @Test
    public void mergeWrite() {
        // 方法1 注解
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 在DemoStyleData里面加上ContentLoopMerge注解
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoMergeData.class).sheet("模板").doWrite(data());

        // 方法2 自定义合并单元格策略
        fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("模板").doWrite(data());
    }

    /**
     * 使用table去写入
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 然后写入table即可
     */
    @Test
    public void tableWrite() {
        String fileName = TestFileUtil.getPath() + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 方法1 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").needHead(Boolean.FALSE).build();
            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
            WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
            // 第一次写入会创建头
            excelWriter.write(data(), writeSheet, writeTable0);
            // 第二次写如也会创建头，然后在第一次的后面写入数据
            excelWriter.write(data(), writeSheet, writeTable1);
        }
    }

    /**
     * 动态头，实时生成头写入
     * <p>
     * 思路是这样子的，先创建List<String>头格式的sheet仅仅写入头,然后通过table 不写入头的方式 去写入数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 然后写入table即可
     */
    @Test
    public void dynamicHeadWrite() {
        String fileName = TestFileUtil.getPath() + "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName)
            // 这里放入动态头
            .head(head()).sheet("模板")
            // 当然这里数据也可以用 List<List<String>> 去传入
            .doWrite(data());
    }

    /**
     * 自动列宽(不太精确)
     * <p>
     * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。 当然也可以自己参照 {@link LongestMatchColumnWidthStyleStrategy}
     * 重新实现.
     * <p>
     * poi 自带{@link SXSSFSheet#autoSizeColumn(int)} 对中文支持也不太好。目前没找到很好的算法。 有的话可以推荐下。
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link LongestMatchColumnWidthData}
     * <p>
     * 2. 注册策略{@link LongestMatchColumnWidthStyleStrategy}
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void longestMatchColumnWidthWrite() {
        String fileName =
            TestFileUtil.getPath() + "longestMatchColumnWidthWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, LongestMatchColumnWidthData.class)
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("模板").doWrite(dataLong());
    }

    /**
     * 下拉，超链接等自定义拦截器（上面几点都不符合但是要对单元格进行操作的参照这个）
     * <p>
     * demo这里实现2点。1. 对第一行第一列的头超链接到:https://github.com/alibaba/easyexcel 2. 对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 注册拦截器 {@link CustomCellWriteHandler} {@link CustomSheetWriteHandler}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void customHandlerWrite() {
        String fileName = TestFileUtil.getPath() + "customHandlerWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(new CustomSheetWriteHandler())
            .registerWriteHandler(new CustomCellWriteHandler()).sheet("模板").doWrite(data());
    }

    /**
     * 插入批注
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 注册拦截器 {@link CommentWriteHandler}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void commentWrite() {
        String fileName = TestFileUtil.getPath() + "commentWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 这里要注意inMemory 要设置为true，才能支持批注。目前没有好的办法解决 不在内存处理批注。这个需要自己选择。
        EasyExcel.write(fileName, DemoData.class).inMemory(Boolean.TRUE).registerWriteHandler(new CommentWriteHandler())
            .sheet("模板").doWrite(data());
    }

    /**
     * 可变标题处理(包括标题国际化等)
     * <p>
     * 简单的说用List<List<String>>的标题 但是还支持注解
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void variableTitleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "variableTitleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).head(variableTitleHead()).sheet("模板").doWrite(data());
    }

    /**
     * 不创建对象的写
     */
    @Test
    public void noModelWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "noModelWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }

    private List<LongestMatchColumnWidthData> dataLong() {
        List<LongestMatchColumnWidthData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            LongestMatchColumnWidthData data = new LongestMatchColumnWidthData();
            data.setString("测试很长的字符串测试很长的字符串测试很长的字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(1000000000000.0);
            list.add(data);
        }
        return list;
    }

    private List<List<String>> variableTitleHead() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("string" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("number" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("date" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Object> data = ListUtils.newArrayList();
            data.add("字符串" + i);
            data.add(0.56);
            data.add(new Date());
            list.add(data);
        }
        return list;
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();

            data.setString("Answer the question based on the given passage. Only give me the answer and do not output any other words. The following are some examples.\\n\\nPassage:\\nFolies Bergère\\nThe Folies Bergère is a cabaret music hall, located in Paris, France.\\n\\nEstablished in 1869, the house was at the height of its fame and popularity from the 1890s' Belle Époque through the 1920s' Années folles. The institution is still in business, and is always a strong symbol of French and Parisian life.\\n\\nHistory\\n\\nLocated at 32 rue Richer in the 9th Arrondissement, the Folies Bergère was built as an opera house by the architect Plumeret. The closest métro stations are Cadet and Grands Boulevards.\\n\\nIt opened on 2 May 1869 as the Folies Trévise, with light entertainment including operettas, opéra comique (comic opera), popular songs, and gymnastics. It became the Folies Bergère on 13 September 1872, named after a nearby street, the rue Bergère (\\\"bergère\\\" means \\\"shepherdess\\\").   The American impresario Florenz Ziegfeld, Jr., named his light-hearted, extravagant Broadway revues the Ziegfeld Follies (1907-1931), after the Parisian venue.\\n\\nIn 1882, Édouard Manet painted his well-known painting A Bar at the Folies-Bergère which depicts a bar-girl, one of the demimondaines, standing before a mirror.\\n\\nIn 1886, Édouard Marchand conceived a new genre of entertainment for the Folies Bergère: the music-hall revue. Women would be the heart of Marchand's concept for the Folies. In the early 1890s, the American dancer Loie Fuller starred at the Folies Bergère. In 1902, illness forced Marchand to leave after 16 years. \\n\\nIn 1918, Paul Derval (1880–1966) made his mark on the revue. His revues were to feature extravagant costumes, sets and effects, and his \\\"small nude women\\\". Derval's small nude women would become the hallmark of the Folies. During his 48 years at the Folies, he launched the careers of many French stars including  Maurice Chevalier, Mistinguett, Josephine Baker, Fernandel and many others. In 1926, Josephine Baker, an African-American expatriate singer, dancer, and entertainer, caused a sensation at the Folies Bergère in a new revue, La Folie du Jour, in which she danced a number Fatou wearing a costume consisting of a skirt made of a string of artificial bananas and little else. Her erotic dancing and near nude performances were renowned. The Folies Bergère catered to popular taste. Shows featured elaborate costumes; the women's were frequently revealing, practically leaving them naked, and shows often contained a good deal of nudity. Shows also played up the \\\"exoticness\\\" of persons and objects from other cultures, obliging the Parisian fascination with the négritude of the 1920s.\\n\\nIn 1936, Derval brought Josephine Baker from New York to lead the revue En Super Folies. Michel Gyarmathy, a young Hungarian arrived from Balassagyarmat, his hometown, designed the poster for En Super Folies, a show starring Josephine Baker in 1936. This began a long love story between Michel Gyarmathy, Paris, the Folies Bergère and the public of the whole world which lasted 56 years. The funeral of Paul Derval was held on 20 May 1966. He was 86 and had reigned supreme over the most celebrated music hall in the world. His wife Antonia, supported by Michel Gyarmathy, succeeded him. In August 1974, the Folies Antonia Derval passed on the direction of the business to Hélène Martini, the empress of the night (25 years earlier she had been a showgirl in the revues). This new mistress of the house reverted to the original concept to maintain the continued existence of the last music hall which remained faithful to the tradition.\\n\\nSince 2006, the Folies Bergère has presented some musical productions with Stage Entertainment like Cabaret (2006–2008) or Zorro the Musical (2009–2010).\\n\\nPerformers \\n\\nFilmography \\n\\n* 1935: Folies Bergère de Paris directed by Roy Del Ruth, with Maurice Chevalier, Merle Oberon, and Ann Sothern\\n* 1935: Folies Bergère de Paris directed by Marcel Achard with Maurice Chevalier, Natalie Paley, Fernand Ledoux. A French-language version of the 1935 Hollywood film.\\n* 1956: Folies-Bergère directed by Henri Decoin with Eddie Constantine, Zizi Jeanmaire, Yves Robert, Pierre Mondy\\n* 1956: Énigme aux Folies Bergère directed by Jean Mitry with Dora Doll, Claude Godard\\n* 1991: La Totale! directed by Claude Zidi with Thierry Lhermitte\\n\\nSimilar venues\\n\\nThe Folies Bergère inspired the Ziegfeld Follies in the United States and other similar shows, including a longstanding revue at the Tropicana Resort & Casino in Las Vegas and the Teatro Follies in Mexico. In the 1930s and '40s the impresario Clifford C. Fischer staged several Folies Bergere productions in the United States. These included the Folies Bergère of 1939 at the Broadway Theater in New York  and the Folies Bergère of 1944 at the Winterland Ballroom   in San Francisco.\\n\\nThe Las Vegas Folies Bergere, which opened in 1959, closed at the end of March 2009 after nearly 50 years in operation.    Another recent example is Faceboyz Folliez, a monthly burlesque and variety show at the Bowery Poetry Club in New York City.\\nQuestion:\\nIn French what is a bergere, as in Les Folies Bergere?\\nAnswer:\\nShepherd\\nPassage:\\nAmerican Words/ British Words - 5 Minute English\\nAmerican Words/ British Words\\nESL Programs\\nAmerican Words/ British Words\\nAs you know, even though North America and Great Britain share the same      language, some words are completely different.  Here are some of the most      common differences.\\nAmerican Word British Word Flashlight Torch Gas Petrol Soccer Football Cookie Biscuit Diaper\\n(on a baby) Nappy\\n(of food) Elevator Lift Truck Lorry Hood\\n(of a car) Bonnet\\n(of a car) Eraser Rubber\\nCheck Your Understanding\\nCan you fill in the blanks without          looking back?\\n1.  In North America, a person drives a truck down the road.  In          Great Britain, a person drives a\\n.\\n2.  In Great Britain, the front of a car is called a bonnet.           In North America, the front of a car is called a\\n.\\n3.  In North America, the back of the car has a trunk. In          Great Britain, the back of the car has a\\n.\\n4.  In Great Britain, people put petrol in their cars to make          them go. In North America, people put\\nin          their cars.\\n5.  In North America, babies wear diapers before they learn to          use the toilet. In Great Britain, babies wear\\n.\\n6.  In Great Britain, food can be bought in tins. In North          America, food is bought in\\n.\\nQuestion:\\nWhat is the American word for the bonnet of a car?\\nAnswer:\\nAlmutium\\nPassage:\\nCleeve Hill\\nCleeve Hill (also known as Cleeve Cloud) is the highest point both of the Cotswolds hill range and in the county of Gloucestershire, at 1083 ft. It commands a clear view to the west, over Cheltenham and the racecourse, over the River Severn and into Wales; and to the north over Winchcombe.  It is a conspicuous outcrop on the edge of the limestone escarpment, (sometimes called the \\\"Cotswold Edge\\\"). It is crossed by the Cotswold Way footpath.\\n\\nWith the hill's south slopes draining to the River Coln, Cleeve Hill is the highest point in the drainage basin of the River Thames.\\n\\nSummit and views\\n\\nThe summit, at 1083 ft, is a nondescript point marked by a trig point on the relatively flat common south of the Hill. Because of this, it does not offer particularly wide-ranging views. To the North by north-west, another summit at 1033 ft high boasts a toposcope and a trig-point, offering far wider views. On an exceptionally clear day (for example a sunny day following a day of rain in spring or early summer), the view extends an impressive 90 mi to Winsford Hill on Exmoor, Somerset.\\n\\nTaking the road up from Aggs Hill you can expect to see three tall radio masts situated 430 yards from the highest point (1,083 ft) above sea level.\\n\\nHill fort and rock faces\\n\\nClose to the summit is the Neolithic long barrow, Belas Knap. On its western scarp is an Iron Age hill fort.\\n\\nThe Hill bears one of the few rock faces in the area, Castle Rock, which is sound enough for rock-climbing. The routes are short, difficult for their grade and highly polished.\\n\\nGolf course and Cleeve Common\\n\\nThe Hill has been home to a golf course since 1891.   The course was the location of the 2010 Wells Cup that took place in June 2010.\\n\\nCleeve Common, which is sited on Cleeve Hill, is a designated Site of Special Scientific Interest by Natural England  and is recorded in the List of Sites of Special Scientific Interest in Gloucestershire.\\n\\nRoutes to the highest point \\n\\nFrom the south, a road for car access ends very close to the summit trig point, which is a short walk from the parking area. This can be reached from Cheltenham via Ham Hill and Aggs Hill, or from the village of Whittington, Gloucestershire. \\n\\nTo the north and to reach the more favourable view point, a minor road leads off the B4632 to the golf course where there is free parking in a disused quarry. From this point, the viewpoint is roughly a half-mile ascent on foot. \\n\\nThere are numerous other ways to reach the hill, and there is a well-maintained network of paths and tracks crossing it in many directions.  Ordnance Survey maps show all routes, paths and rights of way as well as the best viewpoints.\\n\\nCleeve Mount \\n\\nCleeve Mount is the highest residential house in Gloucestershire, and is situated very close to the summit. \\n\\nAfter extensive renovation in 2002 by Mr and Mrs Cooper, a local successful business couple, the estate now benefits from panoramic views of Cheltenham, Bishops Cleeve and Tewkesbury.\\n\\nThe property has a large Stable Block (With capacity for 8 Horses), a small cottage (Referred to as the \\\"Bothy\\\", originally built for the stable groom to stay in) and a large area of woodland below the main house.\\nQuestion:\\nCleeve Hill, at 330m, is the highest point in which range of hills in south-western and west central England ?\\nAnswer:\\nCotteswold Hills\\nPassage:\\nGordie Howe hat trick\\nIn ice hockey, a Gordie Howe hat trick is a variation on the hat-trick, wherein a player scores a goal, records an assist, and gets in a fight all in one game. It is named after Gordie Howe, well known for his skill at both scoring and fighting.\\n\\nThe first known Gordie Howe hat trick was achieved by Hall of Famer Harry Cameron of the Toronto St. Pats on December 22, 1920. \\n\\nThe namesake of the achievement, Gordie Howe, achieved a Gordie Howe hat trick only twice in his NHL career:\\n*Howe got his first Gordie Howe hat trick on October 11, 1953 when he fought the Toronto Maple Leafs' Fernie Flaman, assisted on Red Kelly's goal, and scored his own.\\n*Howe's second happened on March 21, 1954, once again versus the Maple Leafs. Howe scored the opening goal, assisted on two Ted Lindsay goals, and fought Ted \\\"Teeder\\\" Kennedy. \\n\\nA double Gordie Howe hat trick, involving two players who fought each other, has occurred on two occasions:\\n* On March 9, 2010, Fedor Tyutin (who recorded 1G, 2A) fought Ryan Getzlaf (who recorded 1G, 1A)\\n* On January 10, 2012, Adam Henrique (who recorded 1G, 1A) fought Jarome Iginla (who recorded 1G, 2A)\\n\\nA double Gordie Howe hat trick achieved by two players on the same team occurred on April 5, 2012, in a game between the Los Angeles Kings and San Jose Sharks.  Joe Thornton and Ryane Clowe of the Sharks each recorded 1G, 1A and one fight. \\n\\nA triple Gordie Howe hat trick occurred on November 14, 1992, in a game between Buffalo Sabres and New York Islanders. Tom Fitzgerald recorded 1G 1A,  Wayne Presley recorded 1G 2A,  Benoit Hogue recorded 2G 1A  and all three players recorded fights.\\n\\nOn April 9, 1981, during a play-off game between the Minnesota North Stars and the Boston Bruins, a triple-double Gordie Howe Hat Trick occurred.  Bryan Maxwell and Bobby Smith of the Minnesota North Stars and Brad Park of the Boston Bruins all got Gordie Howe Hat Tricks.  Smith and Park fought each other.      Park also scored 1G and 3A.  The North Stars won the game nine to six.\\n\\nAn unusual Gordie Howe hat trick that included a player's first NHL goal occurred on November 19, 2014, when Steve Pinizzotto was called up by the Edmonton Oilers and he made his 2014-15 season debut against his previous team, the Vancouver Canucks. Coach's Corner Don Cherry and Ron McLean broadcast the replay of Pinizzotto's Gordie Howe Hat Trick along with multiple broadcasters.    \\n\\nLeaders\\n\\nAccording to the Society for International Hockey Research, the all-time leader in regular season Gordie Howe hat tricks is Brendan Shanahan (who would later become the NHL's chief disciplinarian) with 17.  In second is Rick Tocchet with 15, followed by Brian Sutter with 12.  The active leader is Jarome Iginla of the Colorado Avalanche with 9. \\n\\nIf one were to include regular season and playoffs, Tocchet has the most Gordie Howe hat tricks as listed below with actual game box scores.\\n\\nBoth regular season and playoff GHHTs included in totals.\\nQuestion:\\nA ‘Gordie Howe Hat Trick’ is when a player scores a goal, notches an assist and gets into a fight all in the same game while playing what?\\nAnswer:\\nHockey (ice)\\nPassage:\\nEmanuel Leutze\\nEmanuel Gottlieb Leutze (May 24, 1816 - July 18, 1868) was a German American history painter best known for his painting Washington Crossing the Delaware. He is associated with the Düsseldorf school of painting.\\n\\nBiography\\n\\nPhiladelphia\\n\\nLeutze was born in Schwäbisch Gmünd, Württemberg, Germany, and was brought to the United States as a child.  His parents settled first in Fredericksburg, Virginia, and then at Philadelphia.  His early education was good, though not especially in the direction of art. The first development of his artistic talent occurred while he was attending the sickbed of his father, when he attempted drawing to occupy the long hours of waiting.  His father died in 1831.  At 14, he was painting portraits for $5 apiece.  Through such work, he supported himself after the death of his father.  In 1834, he received his first instruction in art in classes of John Rubens Smith,  a portrait painter in Philadelphia.  He soon became skilled, and promoted a plan for publishing, in Washington, portraits of eminent American statesmen; however, he met with but slight encouragement.\\n\\nEurope\\n\\nIn 1840, one of his paintings attracted attention and procured him several orders, which enabled him to go to the Kunstakademie Düsseldorf, where he studied with Lessing.  In 1842 he went to Munich, studying the works of Cornelius and Kaulbach, and, while there, finished his Columbus before the Queen. The following year he visited Venice and Rome, making studies from Titian and Michelangelo. His first work, Columbus before the Council of Salamanca was purchased by the Düsseldorf Art Union. A companion picture, Columbus in Chains, procured him the gold medal of the Brussels Art Exhibition, and was subsequently purchased by the Art Union in New York; it was the basis of the 1893 $2 Columbian stamp. In 1845, after a tour in Italy, he returned to Düsseldorf, marrying Juliane Lottner and making his home there for 14 years.\\n\\nDuring his years in Düsseldorf, he was a resource for visiting Americans:  he found them places to live and work, provided introductions, and emotional and even financial support. For many years, he was the president of the Düsseldorf Artists' Association; in 1848, he was an early promoter of the “Malkasten” art association; and in 1857, he led the call for a gathering of artists which led to the founding of the Allgemeine deutsche Kunstgenossenschaft.\\n\\nA strong supporter of Europe's Revolutions of 1848, Leutze decided to paint an image that would encourage Europe's liberal reformers with the example of the American Revolution. Using American tourists and art students as models and assistants, Leutze finished Washington Crossing the Delaware in 1850.  It is owned by the Metropolitan Museum of Art in New York.  In 1854, Leutze finished his depiction of the Battle of Monmouth, \\\"Washington rallying the troops at Monmouth,\\\" commissioned by an important Leutze patron, banker David Leavitt of New York City and Great Barrington, Massachusetts. \\n\\nNew York City and Washington, D.C.\\n\\nIn 1859, Leutze returned to the United States and opened a studio in New York City. He divided his time between New York City and Washington, D.C.  In 1859, he painted a portrait of Chief Justice Roger Brooke Taney which hangs in the Harvard Law School.  In a 1992 opinion, Justice Antonin Scalia described the portrait of Taney, made two years after Taney's infamous decision in Dred Scott v. Sandford, as showing Taney \\\"in black, sitting in a shadowed red armchair, left hand resting upon a pad of paper in his lap, right hand hanging limply, almost lifelessly, beside the inner arm of the chair. He sits facing the viewer and staring straight out. There seems to be on his face, and in his deep-set eyes, an expression of profound sadness and disillusionment.\\\"\\n\\nLeutze also executed other portraits, including one of fellow painter William Morris Hunt. That portrait was owned by Hunt's brother Leavitt Hunt, a New York attorney and sometime Vermont resident, and was shown at an exhibition devoted to William Morris Hunt's work at the Museum of Fine Arts, Boston in 1878. \\n\\nIn 1860 Leutze was commissioned by the U.S. Congress to decorate a stairway in the Capitol Building in Washington, DC, for which he painted a large composition, Westward the Course of Empire Takes Its Way, which is also commonly known as Westward Ho!.\\n\\nLate in life, he became a member of the National Academy of Design. He was also a member of the Union League Club of New York, which has a number of his paintings.  He died in Washington, D.C., in his 52nd year, of heatstroke. He was interred at Glenwood Cemetery.  At the time of his death, a painting, The Emancipation of the Slaves, was in preparation.\\n\\nLeutze's portraits are known less for their artistic quality than for their patriotic emotionalism.  Washington Crossing the Delaware firmly ranks among the American national iconography, and is thus often caricatured.\\n\\nGallery of works\\n\\nFile:Emanuel Leutze (American, 1816-1868). Columbus before the Queen, 1843.jpg|Columbus before the Queen (1843)\\nFile:Emanuel Leutze - Westward the Course of Empire Takes Its Way - Capitol.jpg|Westward the Course of Empire Takes Its Way (1860)\\nFile:Mrs. Schuyler Burning Her Wheat Fields on the Approach of the British.jpg|Mrs. Schuyler Burning Her Wheat Fields on the Approach of the British\\nImage:BattleofMonmouth.jpg|Washington Rallying the Troops at Monmouth\\nFile:Alaska purchase.jpg|William H. Seward and Eduard de Stoeckl Negotiating the Alaska Purchase \\nFile:Leutze, Emanuel — Storming of the Teocalli by Cortez and His Troops — 1848.jpg|Storming of the Teocalli by Cortez and his troops (1848)\\nFile:Worthington Whittredge in His Tenth Street Studio.jpeg|Worthington Whittredge in His Tenth Street Studio (1865)\\nFile:Emanuel Leutze William Morris Hunt.jpeg|Portrait of William Morris Hunt (ca. 1845)\\nFile:General Ambrose Burnside at Antietam by Leutze.jpg|General Ambrose Burnside at Antietam (1863)\\nQuestion:\\nEmmanuel Leutze's most famous painting is of George Washington crossing which river, an event that took place in 1776?\\nAnswer:\\nReligion in Delaware\\nPassage:\\nJohn Wakeham\\nJohn Wakeham, Baron Wakeham, PC, DL (born 22 June 1932) is a British businessman and Conservative Party politician, and the current Chancellor of Brunel University. \\n\\nHe was a director of Enron from 1994  until its bankruptcy in 2001. \\n\\nLife and career\\n\\nWakeham was educated at two independent schools in Surrey: Aldro School in Shackleford, and Charterhouse School near Godalming, and later attended Christ Church, Oxford. He became a successful accountant and later a businessman. He stood unsuccessfully in Coventry East in 1966  and in Putney in 1970 before his election to the House of Commons at the February 1974 general election as the Member of Parliament (MP) for Maldon in Essex. He became a minister after Margaret Thatcher's victory in 1979.\\n\\nHis first wife, Roberta, was killed in the Brighton hotel bombing in October 1984 and he was trapped in rubble for seven hours, suffering serious crush injuries to his legs. The couple had two children. Wakeham married his secretary, Alison Ward MBE in 1985  and they have a son of their own. Before being Wakeham's secretary, Ward had been Margaret Thatcher's secretary.\\n\\nDuring the late 1980s he served as Leader of the House of Commons, in which capacity he was responsible for the televising of Parliament, and as Energy Secretary (1989–92), where he drew up plans for the privatisation of electricity supply. Following a recommendation by John Major, he was created a life peer on 24 April 1992 taking the title Baron Wakeham, of Maldon in the County of Essex,  serving as the Leader of the House of Lords until 1994. \\n\\nHe became chairman of the Press Complaints Commission in 1995, retiring in 2001. In 1997 he was appointed a Deputy Lieutenant of Hampshire. Tony Blair appointed him in 1999 to head a Royal Commission on reform of the House of Lords — the resulting Wakeham Report suggested a mainly-appointed Lords be maintained, with a small elected component.\\n\\nArms\\nQuestion:\\nLord Wakeham resigned from which public body in January 2003?\\nAnswer:\\nPress Complaints Council\\nPassage:\\nRonald Wayne\\nRonald Gerald Wayne (born May 17, 1934) is a retired American electronics industry worker. He co-founded Apple Computer (now Apple Inc.) with Steve Wozniak and Steve Jobs, providing administrative oversight for the new venture. He soon, however, sold his share of the new company for $800 US dollars, and later accepted $1,500 to forfeit any claims against Apple (in total, ).\\n\\nEarly life\\n\\nWayne was born in Cleveland, Ohio, United States on May 17, 1934.  He trained as a technical draftsman at the School of Industrial Arts in New York. In 1956 he moved to California.  Wayne's first business venture was a company selling slot machines. The company failed, with Wayne reflecting in 2014 that, \\\"I discovered very quickly that I had no business being in business. I was far better working in engineering.\\\"\\n\\nCareer\\n\\nApple\\n\\nWayne worked with Steve Jobs at Atari before he, Jobs, and Wozniak founded Apple Computer on April 1, 1976. Serving as the venture's \\\"adult supervision\\\", Wayne drew the first Apple logo, wrote the three men's original partnership agreement,  and wrote the Apple I manual.  \\n\\nWayne received a 10% stake in Apple but relinquished his equity for US$800 less than two weeks later, on April 12, 1976. Legally, all members of a partnership are personally responsible for any debts incurred by any partner; unlike Jobs and Wozniak, then 21 and 25, Wayne had personal assets that potential creditors could seize.  The failure of a slot machine company that he had started five years earlier also contributed to his decision to exit the partnership.\\n\\nLater that year, venture capitalist Arthur Rock and Mike Markkula helped develop a business plan and converted the partnership to a corporation. A year after leaving Apple, Wayne received $1,500 for his agreement to forfeit any claims against the new company. In its first year of operations (1976), Apple's sales reached US$174,000. In 1977 sales rose to US$2.7 million, in 1978 to US$7.8 million, and in 1980 to US$117 million. By 1982 Apple had a billion dollars in annual sales. In February 2015, Apple's value exceeded $700 billion, making it the most valuable U.S. company by far. Had Wayne kept his 10% stock until then, it would have been worth billions. \\n\\nWayne has stated that he does not regret selling his share of the company as he made the \\\"best decision with the information available to me at the time\\\".  Wayne also stated that he felt the Apple enterprise \\\"would be successful, but at the same time there would be significant bumps along the way and I couldn't risk it. I had already had a rather unfortunate business experience before. I was getting too old and those two were whirlwinds. It was like having a tiger by the tail and I couldn't keep up with these guys.\\\"\\n\\nPost-Apple\\n\\nAfter leaving Apple, Wayne resisted Jobs' attempts to get him to return, remaining at Atari until 1978, when he joined Lawrence Livermore National Laboratory and later an electronics company in Salinas, California.\\n\\nWayne retired to a Pahrump, Nevada mobile home park  selling stamps and rare coins in Pahrump. Wayne never owned an Apple product  until 2011, when he was given an iPad 2 by Aral Balkan at the Update Conference in Brighton, England.\\n\\nWayne also ran a stamp shop in Milpitas, California for a short time in the late 1970s, Wayne's Philatelics. After a number of break-ins, he moved his stamp operations to Nevada. The logo for the business was a wood-cut style design, with a man sitting under an apple tree, with the \\\"Wayne's Philatelics\\\" name written in a flowing ribbon curved around the tree. This was the original logo he designed for Apple Computer.\\n\\nIn the early 1990s Wayne sold the original Apple company agreement, signed in 1976 by Jobs, Wozniak and himself, for $500. In 2011 the contract was sold at auction for $1.6 million. Wayne has stated that it is the one thing he regrets about his involvement with Apple.  \\n\\nHe holds a dozen patents.\\n\\nAuthor\\n\\nWayne published a memoir titled, Adventures of an Apple Founder, in July 2011. Plans for initial exclusivity on the Apple iBookstore did not pan out.\\n\\nWayne has also written a socio-economic treatise titled Insolence of Office, released on October 1, 2011 which he describes as:\\n\\nDocumentaries\\n\\nWayne appeared in the documentary Welcome to Macintosh where he describes some of his experiences with Steve Jobs, Steve Wozniak, and Apple Computer.\\n\\nPersonal life\\n\\nWayne came out as gay to Jobs shortly after February 1974, while both men were employees at Atari. Jobs later recalled, \\\"It was my first encounter with someone who I knew was gay.\\\" Wayne recalled in 2011 that, \\\"Nobody at Atari knew, and I could count on my toes and fingers the number of people I told in my whole life. But I guess it just felt right to tell him, that he would understand, and it didn't have any effect on our relationship.\\\"\\nQuestion:\\nApril 1, 1976 saw Ronald Wayne, Steve Wozniak, and whom, start a company to sell a computer mother board (including CPU, RAM, and video chip) for a mere $666.66?\\nAnswer:\\nJobs, Steven Paul\\nPassage:\\nThe World of Donald McGill - joylandbooks.com\\nThe World of Donald McGill\\nThe World of Donald McGill\\nby Elfreda Buckland\\nPublication Date: 1984 (1990 reprint)\\nPublisher: Blandford\\nA respectable Victorian gentleman,               Donald McGill, spent virtually the whole of his life creating               colour-washed drawings which were reproduced as postcards and sold               in millions from 1904 until the mid-1960s. McGill was, and               remains, 'the king of the saucy postcard', still collected and               appreciated today for his artistic skill, power of social               observation and rumbustious humour.\\nThe World of Donald McGill tells               the story of McGill's life and takes a light-hearted look at the               development of his work in its social context. Over 200 postcards               are shown in full colour, including early and less well-known               ones.\\nCondition: Fine hardback in fine protected      dustwrapper.\\nQuestion:\\nWho was the respectable Victorian gentleman who became known as the 'King of Saucy Postcards'?\\nAnswer:\\nDonald McGill\\nPassage:\\nScottish Grand National\\n|}\\n\\nThe Scottish Grand National is a Grade 3 National Hunt steeplechase in Great Britain which is open to horses aged five years or older. It is run at Ayr, Scotland, over a distance of approximately 4 miles and 110 yards (6,538 metres) and 27 fences. It is a handicap race, and takes place each year in April.\\nIt is Scotland's equivalent of the Grand National, and is held during Ayr's two-day Scottish Grand National Festival meeting.\\n\\nHistory\\n\\nThe race, then known as the \\\"West of Scotland Grand National\\\", was first run at a course near Houston, Renfrewshire in 1858.  It consisted of 32 jumps, mainly stone walls.\\n\\nIn 1867, after objections by the leader of the Free Kirk in Houston, the race moved to Bogside Racecourse, near Irvine. The inaugural winner at Bogside, The Elk, was owned by the Duke of Hamilton. During the early part of its history the race's distance was about three miles. It was later extended to 3⅞ miles, and became known by its present title in 1880, when it was won by Peacock.\\n\\nBogside Racecourse closed in 1965, and the Scottish Grand National was transferred to Ayr the following year. At this point the race was increased to its present length. Several winners of the Scottish Grand National have also won its English counterpart at Aintree. The first to complete the double was Music Hall, the winner of the 1922 Grand National. The feat has been achieved more recently by Little Polveir and Earth Summit, but the only horse to win both races in the same year was Red Rum in 1974.\\n\\nPrize money\\n\\nThe winning horse in 1867 won £100, increasing to £440 by 1906, £1030 in 1950, £5,436 in 1963 and  £119,595 in 2015.\\n\\nTelevision coverage\\n\\nThe first television coverage of the Scottish National was in 1953 on the BBC.  It was also shown the following year, but then wasn't screened again until 1969 on ITV and has been shown live ever since.  Coverage moved to Channel 4 in 1986.\\n\\nRecords\\n\\nMost successful horse (3 wins):\\n* Couvrefeu II – 1911, 1912, 1913\\n* Southern Hero – 1934, 1936, 1939\\n* Queen's Taste – 1953, 1954, 1956\\n\\nLeading jockey\\n*All-time (4 wins)\\n**Charlie Cunningham - Bellman (1881), Wild Meadow (1885), Orcadian (1887), Deloraine (1889)\\n*At Ayr (3 wins)\\n** Mark Dwyer – Androma (1984, 1985), Moorcroft Boy (1996)\\n\\nLeading trainer\\n*All-time (5 wins)\\n**Neville Crump – Wot No Sun (1949), Merryman II (1959), Arcturus (1968), Salkeld (1980), Canton (1983)\\n**Ken Oliver – Pappageno's Cottage (1963), The Spaniard (1970), Young Ash Leaf (1971), Fighting Fit (1979), Cockle Strand (1982)\\n*At Ayr (4 wins)\\n**Ken Oliver – The Spaniard (1970), Young Ash Leaf (1971), Fighting Fit (1979), Cockle Strand (1982)\\n\\nWinners at Ayr\\n\\n* Weights given in stones and pounds; Amateur jockeys indicated by \\\"Mr\\\".\\n\\nWinners at Bogside\\n\\nEarlier Winners\\n\\n* 1867 – The Elk\\n* 1868 – Greenland\\n* 1869 – Huntsman\\n* 1870 – Snowstorm\\n* 1871 – Keystone\\n* 1872 – Cinna\\n* 1873 – Hybla\\n* 1874 – Ouragon II\\n* 1875 – Solicitor\\n* 1876 – Earl Marshal\\n* 1877 – Solicitor\\n* 1878 – no race\\n* 1879 – Militant\\n* 1880 – Peacock\\n* 1881 – Bellman\\n* 1882 – Gunboat\\n* 1883 – Kerclaw\\n* 1884 – The Peer\\n* 1885 – Wild Meadow\\n* 1886 – Crossbow\\n* 1887 – Orcadian\\n* 1888 – Ireland\\n* 1889 – Deloraine\\n* 1890 – no race\\n* 1891 – see note below *\\n* 1892 – Lizzie\\n* 1893 – Lady Ellen II\\n* 1894 – Leybourne\\n* 1895 – Nepcote\\n* 1896 – Cadlaw Cairn\\n* 1897 – Modest Friar\\n* 1898 – Trade Mark\\n* 1899 – Tyrolean\\n* 1900 – Dorothy Vane\\n* 1901 – Big Busbie\\n* 1902 – Canter Home\\n* 1903 – Chit Chat\\n* 1904 – Innismacsaint\\n* 1905 – Theodocian\\n* 1906 – Creolin\\n* 1907 – Barney III\\n* 1908 – Atrato\\n* 1909 – Mount Prospect's Fortune\\n* 1910 – The Duffrey\\n* 1911 – Couvrefeu II\\n* 1912 – Couvrefeu II\\n* 1913 – Couvrefeu II\\n* 1914 – Scrabee\\n* 1915 – Templedowney\\n* 1916 – no race\\n* 1917 – no race\\n* 1918 – no race\\n* 1919 – The Turk\\n* 1920 – Music Hall\\n* 1921 – no race\\n* 1922 – Sergeant Murphy\\n* 1923 – Harrismith\\n* 1924 – Royal Chancellor\\n* 1925 – Gerald L.\\n* 1926 – Estuna\\n* 1927 – Estuna\\n* 1928 – Ardeen\\n* 1929 – Donzelon\\n* 1930 – Drintyre\\n* 1931 – Annandale\\n* 1932 – Clydesdale\\n* 1933 – Libourg\\n* 1934 – Southern Hero\\n* 1935 – Kellsboro' Jack\\n* 1936 – Southern Hero\\n* 1937 – Right'un\\n* 1938 – Young Mischief\\n* 1939 – Southern Hero\\n* 1940–46 – no race\\n\\n* There were only two runners in 1891 – neither could clear the second fence and there was no winner.\\nQuestion:\\nAt which racecourse is the Scottish Grand National run each April?\\nAnswer:\\nAYR\\nPassage:\\nEngland will wear all-white kit at the World Cup after ...\\nEngland will wear all-white kit at the World Cup after caving in to new FIFA demands - Mirror Online\\nSport\\nEngland will wear all-white kit at the World Cup after caving in to new FIFA demands\\nFIFA want sides to wear singled-coloured kits in order to improve the quality of high-def television pictures from Brazil\\n Share\\nAll-white on the night: England went one-colour in Euro 2012 (Photo: Getty)\\n Share\\nGet football updates directly to your inbox\\n+ Subscribe\\nThank you for subscribing!\\nCould not subscribe, try again laterInvalid Email\\nEngland will ditch their traditional kit for an all-white World Cup strip after bowing to demands from FIFA .\\nThe Zurich bureaucrats have urged nations to adopt predominantly single-coloured kits to improve the quality of HD pictures from Brazil. And it means Roy Hodgson’s men will run out in Manaus, Sao Paulo and Belo Horizonte in their World Cup group clashes wearing a kit that some old-school fans will not appreciate.           \\nWhile the hosts are understood to be ignoring FIFA’s request and sticking with their canary yellow shirts and blue shorts, England are following other major countries and falling into line.\\nGermany last month revealed their all-white design, ditching their traditional black shorts.\\nSpain will be all-red, Portugal all Port-red and Italy all blue, although France are sticking with white shorts under their blue shirts.\\nAnd the FA and strip manufacturers Nike have agreed a new all-white outfit which will be unveiled before the Wembley farewell friendly against Peru in May.\\nQuestion:\\nWhat colour shirts did the winning team wear 1966 World cup?\\nAnswer:\\nRed (colour)\\nPassage:\\nBibliophilia\\nBibliophilia or bibliophilism is the love of books, and a bibliophile is an individual who loves books. \\n\\nProfile\\n\\nThe classic bibliophile is one who loves to read, admire and collect books, often amassing a large and specialized collection. Bibliophiles do not necessarily want to possess the books that they love; an alternative would be unusual bindings, autographed copies, etc.\\n\\nUsage of the term\\n\\nBibliophilia is not to be confused with bibliomania, an obsessive–compulsive disorder involving the collecting of books to the extent that interpersonal relations or health may be negatively affected, and in which the mere fact that a physical object is a book is sufficient for it to be collected or beloved. Some use the term \\\"bibliomania\\\" interchangeably with \\\"bibliophily\\\", and in fact, the Library of Congress does not use the term \\\"bibliophily,\\\" but rather refers to its readers as either book collectors or bibliomaniacs. \\nThe New York Public Library follows the same practice. \\n\\nHistory\\n\\nAccording to Arthur H. Minters, the \\\"private collecting of books was a fashion indulged in by many Romans, including Cicero and Atticus\\\".  The term bibliophile entered the English language in 1824.  A bibliophile is to be distinguished from the much older notion of a bookman (which dates back to 1583), who is one who loves books, and especially reading; more generally, a bookman is one who participates in writing, publishing, or selling books. \\n\\nLord Spencer and the Marquess of Blandford were noted bibliophiles. \\\"The Roxburghe sale quickly became a foundational myth for the burgeoning secondhand book trade, and remains so to this day\\\"; this sale is memorable due to the competition between \\\"Lord Spencer and the marquis of Blandford [which] drove [the price of a probable first edition of Boccaccio's Decameron] up to the astonishing and unprecedented sum of £2,260\\\".  J. P. Morgan was also a noted bibliophile. In 1884, he paid $24,750 for a 1459 edition of the Mainz Psalter.\\nQuestion:\\nA bibliophile is a lover of what?\\nAnswer:\\nBooke\\nPassage:\\nGoneril\\nGoneril is a character in Shakespeare's tragic play King Lear (1605). She is the eldest of King Lear's three daughters. Along with her sister Regan, Goneril is considered a villain,  obsessed with power and overthrowing her elderly father as ruler of the kingdom of Britain. Her aggressiveness is a rare trait for a female character in Elizabethan literature.\\n\\nShakespeare based the character on Gonorilla, a personage described by Geoffrey of Monmouth in his pseudohistorical chronicle Historia regum Britanniae (\\\"History of the Kings of Britain\\\", ) as the eldest of the British king Lear's three daughters, alongside Regan and Cordeilla (the source for Cordelia), and the mother of Marganus.\\n\\nAnalysis\\n\\nThe earliest example of her deceitful tendencies occurs in the first act. Without a male heir, Lear is prepared to divide his kingdom among his three daughters, as long as they express their true love to him. Knowing her response will get her closer to the throne, Goneril professes, \\\"Sir, I love you more than words can wield the matter\\\" (1.1. 53).  She has no reservations about lying to her father.\\n\\nShe finally begins to show her true colours when Lear asks to stay with her and her husband. She tells him to send away his knights and servants because they are too loud and too numerous. Livid that he is being disrespected, Lear curses her and leaves.\\n\\nGoneril, the wife of the Duke of Albany (an archaic name for Scotland), has an intimate relationship with Edmund, one that may have been played up in the earlier editions of King Lear.  She writes a note encouraging Edmund to kill her husband and marry her, but it is discovered. In the final act, Goneril discovers that Regan has a sexual desire for Edmund as well and poisons her sister’s drink. However, once Edmund is mortally wounded, Goneril goes offstage and kills herself.\\n\\nPerformance history\\n\\nOnscreen\\n\\n*Kate Fleetwood. \\\"King Lear\\\" (2014) National Theatre Live broadcast. Dir. Sam Mendes\\n*Frances Barber. King Lear (2009) PBS Dir. Sir Trevor Nunn and Chris Hunt\\n*Caroline Lennon. King Lear (1999) Dir. Brian Blessed & Tony Rotherham\\n*Barbara Flynn. Performance King Lear (1998) Dir. Richard Eyre\\n*Dorothy Tutin. King Lear (1983) (TV) Dir. Keith Elliott\\n*Gillian Barge. King Lear (1982) (TV) Dir. Jonathan Miller\\n*Beth Harris. King Lear (1976) (TV) Dir. Tony Davenall\\n*Rosalind Cash. King Lear (1974) (TV) Dir. Edwin Sherin\\n*Irene Worth. King Lear (1971 UK Film) Dir. Peter Brook\\n*Elza Radzina. Korol Lir (1971 USSR Film) Dir. Grigori Kozintsev & Iosif Shapiro\\n*Beatrice Straight. King Lear (1953) (TV) Dir. Andrew McCullough\\nQuestion:\\nRegan and Goneril are two of King Lear's daughters, who is the third?\\nAnswer:\\nCordelia\\nPassage:\\nBankable Productions\\nBankable Productions (previously known as \\\"Ty Ty Productions\\\") is an independent film and television production company founded by former model Tyra Banks who also serves as CEO of the company. According to Bankable Productions, the company strives to entertain broad audiences that span all ages from children to adults. \\n\\nProjects\\n\\nTelevision\\n\\n* America's Next Top Model (UPN & The CW) (2003–2012; production rights transferred to the separate \\\"The Tyra Banks Company\\\")\\n* The Tyra Banks Show (syndication & The CW) (2005–2010; co-production with Telepictures)\\n* Stylista (The CW) (2008: co-production with Warner Horizon Television)\\n* True Beauty (ABC) (2009–2010; co-production with Warner Horizon Television and Katalyst Media)\\n* FABLife (syndication, 2015–2016; co-production with Disney-ABC Domestic Television)\\n\\nFilm\\n\\n* The Clique (2008)\\n\\nWeb productions\\n\\n* Fa-Fa-Fa Fashion (2011)\\n\\nDeal with Warner Bros.\\n\\nIn October 2007 Tyra Banks signed an Exclusive Multiyear Development and Production Deal between Bankable Productions and Warner Bros. Entertainment. Under terms of the multiyear pact, Bankable Productions will create and produce original primetime television series programming via the Studio's Warner Bros. Television (WBTV) and Warner Horizon Television (WHTV) production units, as well as original movies for Warner Premiere, the Studio's direct-to-consumer production arm.\\nQuestion:\\n‘Bankable Productions’, an independent film and television production company, was founded by which former model?\\nAnswer:\\nTyra banks\\nPassage:\\nBartizan\\nA bartizan, also called a guerite or echauguette, is an overhanging, wall-mounted turret projecting from the walls of late medieval and early-modern fortifications from the early 14th century up to the 16th century.  Most frequently found at corners, they protected a warder and enabled him to see his surroundings. Bartizans generally are furnished with oillets or arrow slits.  The turret was usually supported by stepped masonry corbels and could be round or square.\\n\\nBartizans were incorporated into many notable examples of Scots Baronial Style architecture in  Scotland. In the architecture of Aberdeen, the new Town House, built in 1868–74, incorporates bartizans in the West Tower.\\n\\nGallery \\n\\nRound Bartizan, Fortaleza de Santiago, Sesimbra, Portugal.JPG|Guarita at Fortaleza de Santiago, Sesimbra Municipality, Portugal.\\nSudika Isla watchtower.jpg|Gardjola at the Spur, Senglea, Malta.\\nCanuelo-2.jpg|Garita at El Cañuelo in the Bay of San Juan, Puerto Rico.\\nBartizan.jpg|South-East Bartizan on Greenknowe Tower, Scottish Borders (and another one in the background)\\nBartizan (PSF).jpg|Line drawing of a bartizan\\nTown House, top of West Tower, Aberdeen, Peddie and Kinnear, 1868-74, photo Jane Cartney 2010RESIZED200.jpg|Bartizans on the West Tower of the new Town House in Aberdeen, Scotland, 1868–74.\\nGarita at Castillo de San Cristobal-Detail.jpg|Garita at Castillo de San Cristobal in San Juan, Puerto Rico.\\nJerusalemBevingrad.jpg|A bartizan-style British concrete position  built at the north-western corner of Sergei courtyard, Jerusalem. This is probably the sole existing testimony of the British \\\"Bevingrad\\\" constructed in 1946.\\n\\nDevil's Sentry Box or the \\\"Garita del Diablo\\\", en el Morro y el Atlantico.tiff|Devil's Sentry Box, or the \\\"Garita del Diablo\\\", Castle of San Felipe del Morro, in San Juan, Puerto Rico.\\nQuestion:\\nBartizan, stylobate, breastsummer are terms found in what discipline?\\nAnswer:\\nArchitecturally\\nPassage:\\nAndy Roberts (cricketer)\\nSir Anderson Montgomery Everton \\\"Andy\\\" Roberts, KCN (born 29 January 1951) is a former Antiguan and West Indian cricketer. He was a fast bowler, twice taking seven wickets in an innings of a Test match. In England, he played first class cricket for Hampshire County Cricket Club and Leicestershire County Cricket Club.\\n\\nAndy Roberts formed part of the \\\"quartet\\\" of West Indian fast bowlers from the mid-Seventies to the early Eighties (the others being Michael Holding, Joel Garner and Colin Croft) that had such a devastating effect on opposition batsmen at both Test and One Day International level. He was also part of the West Indies team that won the first two Prudential World Cups in England in 1975 and 1979.\\n\\nBy his own reckoning, the best spell Roberts ever delivered was during the Headingley Test of the West Indies' 1976 tour of England: \\\"I only got three wickets, but in my mind there was a decision given against me. It was a leg-before decision against Peter Willey, where he played right back onto his stumps to a fuller delivery. I would've bowled England out that afternoon if the umpire had given me the decision.\\\"  Despite an excellent record in Tests his international career was relatively short and ended in 1983. Imran Khan (former captain Pakistan national cricket team) once described a ball bowled to him by Andy Roberts as the fastest and most terrifying he had ever faced.\\n\\nOne of his trademarks was the use of two different bouncers. One was delivered at a slower pace and was often dealt with quite easily by the batsman. However, this was a ploy by Roberts to lull the batsman into a false sense of security. Roberts would then deliver the second bouncer, pitching in a similar spot to the first, but delivered at far greater pace. The batsman would attempt to play this delivery in the same fashion as the first slower bouncer only to be surprised by the extra pace and bounce of the ball. Many batsmen were dismissed, and many more struck painful blows, by Roberts using this ploy.\\n\\nAndy Roberts' contribution to West Indies cricket has continued since his retirement as a player. As an administrator overseeing the preparation of pitches, he helped prepare the pitches in Antigua on which Brian Lara twice broke the world record for highest Test scores.\\n\\nRoberts was the first Antiguan to play Test cricket for the West Indies, thus leading the way for many of his famous countrymen including Viv Richards, Richie Richardson and Curtly Ambrose. In October 2005, Roberts was inducted into the United States Cricket Hall of Fame, becoming the second Antiguan to be recognised.\\n\\nRoberts worked with Bangladesh's fast bowlers in 2001 and again in 2005,  and also helped coach India's seam bowling all-rounder Irfan Pathan in 2006.  Roberts joined the West Indies Cricket Board selection panel in July 2006.  In 2008 Roberts was one of 12 former West Indies cricketers who made up the 'Stanford Legends' who promoted the Stanford 20/20. \\n\\nRoberts was appointed a Knight Commander of the Order of the Nation (KCN) by the Antiguan Barbudan government on 28 February 2014. \\n\\nNotes\\nQuestion:\\nWhich politician (as at 2011) has the second-highest average for a No6 cricket test batsman (over 61 runs) and in a competition bowled faster than Dennis Lillee and Andy Roberts?\\nAnswer:\\nImran Conne\\n\\n\\nPassage:\\nMuhammad Ali refuses to fight in Vietnam war: From the ...\\nMuhammad Ali refuses to fight in Vietnam war: From the archive, 27 April 1967 | From the Guardian | The Guardian\\nShare on Messenger\\nClose\\nBoxing authorities in America today stripped Muhammad Ali (Cassius Clay) of his world heavyweight title and suspended his boxing licence after he had refused to be inducted into the United States Army.\\nClay had stood in line with 11 other men being called up in a room in the old Post Office building in Houston, Texas, and heard his Black Muslim name called by the officer administering the oath. Clay did not move. Another officer walked up to him and said: \\\"Mr Ali, will you accompany me, please ?\\\" Clay did not speak, but followed him out of the room to be given a warning of the consequences of his refusal.\\nHe was taken back into the room and given a second chance to take the oath, but he again refused. He then signed a statement to that effect.\\nSoon after he left the centre, to be mobbed by well-wishers, the New York Boxing Commission, the World Boxing Association, and the Texas Boxing Commission withdrew their recognition of him as champion.\\nReading on mobile? Click here to watch video\\nAt the same time a spokesman for the Justice Department said it would decide whether to ask a federal grand jury for an indictment. If an indictment were returned, Clay would have to go for trial. He could face a long prison sentence.\\nClay issued a statement saying: \\\"It is in the light of my consciousness as a Muslim minister and my own personal convictions that I take my stand in rejecting the call to be inducted. I do so with the full realisation of its implications. I have searched my conscience.\\n\\\"I had the world heavyweight title not because it was given to me, not because of my race or religion, but because I won it in the ring. Those who want to take it and start a series of auction-type bouts not only do me a disservice, but actually disgrace themselves... Sports fans and fair-minded people throughout America would never accept such a title-holder.\\\"\\nThe New York Boxing Commission, which suspended his licence, said his refusal to enter the service was detrimental to the best interests of boxing.\\n[Muhammad Ali was sentenced to five years in prison and a $10,000 fine, though he remained out on bail while he appealed. He was stripped of his passport and his heavyweight title and banned from fighting in the US. Ali returned to boxing in 1970 and his conviction was reversed in 1971]\\nQuestion:\\nWhat boxer was stripped of his heavyweight boxing titles when he refused his US army induction in April, 1967?\\nAnswer:\\n" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

}
