package com.alibaba.easyexcel.test.core.style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.core.StyleTestUtils;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StyleDataTest {

    private static File file07;
    private static File file03;
    private static File fileVerticalCellStyleStrategy07;
    private static File fileVerticalCellStyleStrategy207;
    private static File fileLoopMergeStrategy;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("style07.xlsx");
        file03 = TestFileUtil.createNewFile("style03.xls");
        fileVerticalCellStyleStrategy07 = TestFileUtil.createNewFile("verticalCellStyle.xlsx");
        fileVerticalCellStyleStrategy207 = TestFileUtil.createNewFile("verticalCellStyle2.xlsx");
        fileLoopMergeStrategy = TestFileUtil.createNewFile("loopMergeStrategy.xlsx");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    @Test
    public void t03AbstractVerticalCellStyleStrategy() {
        AbstractVerticalCellStyleStrategy verticalCellStyleStrategy = new AbstractVerticalCellStyleStrategy() {
            @Override
            protected WriteCellStyle headCellStyle(Head head) {
                WriteCellStyle writeCellStyle = new WriteCellStyle();
                writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                DataFormatData dataFormatData = new DataFormatData();
                dataFormatData.setIndex((short)0);
                writeCellStyle.setDataFormatData(dataFormatData);
                writeCellStyle.setHidden(false);
                writeCellStyle.setLocked(true);
                writeCellStyle.setQuotePrefix(true);
                writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
                writeCellStyle.setWrapped(true);
                writeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                writeCellStyle.setRotation((short)0);
                writeCellStyle.setIndent((short)10);
                writeCellStyle.setBorderLeft(BorderStyle.THIN);
                writeCellStyle.setBorderRight(BorderStyle.THIN);
                writeCellStyle.setBorderTop(BorderStyle.THIN);
                writeCellStyle.setBorderBottom(BorderStyle.THIN);
                writeCellStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
                writeCellStyle.setRightBorderColor(IndexedColors.RED.getIndex());
                writeCellStyle.setTopBorderColor(IndexedColors.RED.getIndex());
                writeCellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
                writeCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
                writeCellStyle.setShrinkToFit(Boolean.TRUE);

                if (head.getColumnIndex() == 0) {
                    writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    WriteFont writeFont = new WriteFont();
                    writeFont.setItalic(true);
                    writeFont.setStrikeout(true);
                    writeFont.setTypeOffset(Font.SS_NONE);
                    writeFont.setUnderline(Font.U_DOUBLE);
                    writeFont.setBold(true);
                    writeFont.setCharset((int)Font.DEFAULT_CHARSET);
                } else {
                    writeCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                }
                return writeCellStyle;
            }

            @Override
            protected WriteCellStyle contentCellStyle(Head head) {
                WriteCellStyle writeCellStyle = new WriteCellStyle();
                writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                if (head.getColumnIndex() == 0) {
                    writeCellStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
                } else {
                    writeCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
                }
                return writeCellStyle;
            }
        };
        EasyExcel.write(fileVerticalCellStyleStrategy07, StyleData.class).registerWriteHandler(
                verticalCellStyleStrategy).sheet()
            .doWrite(data());

    }

    @Test
    public void t04AbstractVerticalCellStyleStrategy02() {
        final StyleProperty styleProperty = StyleProperty.build(StyleData.class.getAnnotation(HeadStyle.class));
        final FontProperty fontProperty = FontProperty.build(StyleData.class.getAnnotation(HeadFontStyle.class));
        AbstractVerticalCellStyleStrategy verticalCellStyleStrategy = new AbstractVerticalCellStyleStrategy() {
            @Override
            protected WriteCellStyle headCellStyle(Head head) {
                WriteCellStyle writeCellStyle = WriteCellStyle.build(styleProperty, fontProperty);

                if (head.getColumnIndex() == 0) {
                    writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    WriteFont writeFont = new WriteFont();
                    writeFont.setItalic(true);
                    writeFont.setStrikeout(true);
                    writeFont.setTypeOffset(Font.SS_NONE);
                    writeFont.setUnderline(Font.U_DOUBLE);
                    writeFont.setBold(true);
                    writeFont.setCharset((int)Font.DEFAULT_CHARSET);
                } else {
                    writeCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                }
                return writeCellStyle;
            }

            @Override
            protected WriteCellStyle contentCellStyle(Head head) {
                WriteCellStyle writeCellStyle = new WriteCellStyle();
                writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                if (head.getColumnIndex() == 0) {
                    writeCellStyle.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
                } else {
                    writeCellStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
                }
                return writeCellStyle;
            }
        };
        EasyExcel.write(fileVerticalCellStyleStrategy207, StyleData.class).registerWriteHandler(
                verticalCellStyleStrategy).sheet()
            .doWrite(data());
    }

    @Test
    public void t05LoopMergeStrategy() {
        EasyExcel.write(fileLoopMergeStrategy, StyleData.class).sheet().registerWriteHandler(
                new LoopMergeStrategy(2, 1))
            .doWrite(data10());
    }

    private void readAndWrite(File file) throws Exception {
        SimpleColumnWidthStyleStrategy simpleColumnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(50);
        SimpleRowHeightStyleStrategy simpleRowHeightStyleStrategy =
            new SimpleRowHeightStyleStrategy((short)40, (short)50);

        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteFont.setColor(IndexedColors.DARK_YELLOW.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.TEAL.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)30);
        contentWriteFont.setColor(IndexedColors.DARK_TEAL.getIndex());
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(2, 2, 0, 1);
        EasyExcel.write(file, StyleData.class).registerWriteHandler(simpleColumnWidthStyleStrategy)
            .registerWriteHandler(simpleRowHeightStyleStrategy).registerWriteHandler(horizontalCellStyleStrategy)
            .registerWriteHandler(onceAbsoluteMergeStrategy).sheet().doWrite(data());
        EasyExcel.read(file, StyleData.class, new StyleDataListener()).sheet().doRead();

        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Assert.assertEquals(50 * 256, sheet.getColumnWidth(0), 0);

        Row row0 = sheet.getRow(0);
        Assert.assertEquals(800, row0.getHeight(), 0);
        Cell cell00 = row0.getCell(0);
        Assert.assertArrayEquals(new byte[] {-1, -1, 0}, StyleTestUtils.getFillForegroundColor(cell00));
        Assert.assertArrayEquals(new byte[] {-128, -128, 0}, StyleTestUtils.getFontColor(cell00, workbook));
        Assert.assertEquals(20, StyleTestUtils.getFontHeightInPoints(cell00, workbook));

        Cell cell01 = row0.getCell(1);
        Assert.assertArrayEquals(new byte[] {-1, -1, 0}, StyleTestUtils.getFillForegroundColor(cell01));
        Assert.assertArrayEquals(new byte[] {-128, -128, 0}, StyleTestUtils.getFontColor(cell01, workbook));
        Assert.assertEquals(20, StyleTestUtils.getFontHeightInPoints(cell01, workbook));

        Row row1 = sheet.getRow(1);
        Assert.assertEquals(1000, row1.getHeight(), 0);
        Cell cell10 = row1.getCell(0);
        Assert.assertArrayEquals(new byte[] {0, -128, -128}, StyleTestUtils.getFillForegroundColor(cell10));
        Assert.assertArrayEquals(new byte[] {0, 51, 102}, StyleTestUtils.getFontColor(cell10, workbook));
        Assert.assertEquals(30, StyleTestUtils.getFontHeightInPoints(cell10, workbook));
        Cell cell11 = row1.getCell(1);
        Assert.assertArrayEquals(new byte[] {0, -128, -128}, StyleTestUtils.getFillForegroundColor(cell11));
        Assert.assertArrayEquals(new byte[] {0, 51, 102}, StyleTestUtils.getFontColor(cell11, workbook));
        Assert.assertEquals(30, StyleTestUtils.getFontHeightInPoints(cell11, workbook));
    }

    private List<StyleData> data() {
        List<StyleData> list = new ArrayList<StyleData>();
        StyleData data = new StyleData();
        data.setString("字符串0");
        data.setString1("字符串01");
        StyleData data1 = new StyleData();
        data1.setString("字符串1");
        data1.setString1("字符串11");
        list.add(data);
        list.add(data1);
        return list;
    }

    private List<StyleData> data10() {
        List<StyleData> list = new ArrayList<StyleData>();
        for (int i = 0; i < 10; i++) {
            StyleData data = new StyleData();
            data.setString("字符串0");
            data.setString1("字符串01");
            list.add(data);
        }
        return list;
    }
}
