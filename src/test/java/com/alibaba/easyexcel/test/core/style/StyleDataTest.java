package com.alibaba.easyexcel.test.core.style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StyleDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("style07.xlsx");
        file03 = TestFileUtil.createNewFile("style03.xls");
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
    public void t03AbstractVerticalCellStyleStrategy() {
        AbstractVerticalCellStyleStrategy verticalCellStyleStrategy = new AbstractVerticalCellStyleStrategy() {
            @Override
            protected WriteCellStyle headCellStyle(Head head) {
                WriteCellStyle writeCellStyle = new WriteCellStyle();
                writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                writeCellStyle.setDataFormat((short)0);
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
        EasyExcel.write(file07, StyleData.class).registerWriteHandler(verticalCellStyleStrategy).sheet()
            .doWrite(data());
    }

    @Test
    public void t04LoopMergeStrategy() {
        EasyExcel.write(file07, StyleData.class).sheet().registerWriteHandler(new LoopMergeStrategy(2, 1))
            .doWrite(data10());
    }

    private void readAndWrite(File file) {
        SimpleColumnWidthStyleStrategy simpleColumnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(50);
        SimpleRowHeightStyleStrategy simpleRowHeightStyleStrategy =
            new SimpleRowHeightStyleStrategy((short)40, (short)50);

        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(2, 2, 0, 1);
        EasyExcel.write(file, StyleData.class).registerWriteHandler(simpleColumnWidthStyleStrategy)
            .registerWriteHandler(simpleRowHeightStyleStrategy).registerWriteHandler(horizontalCellStyleStrategy)
            .registerWriteHandler(onceAbsoluteMergeStrategy).sheet().doWrite(data());
        EasyExcel.read(file, StyleData.class, new StyleDataListener()).sheet().doRead();
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
