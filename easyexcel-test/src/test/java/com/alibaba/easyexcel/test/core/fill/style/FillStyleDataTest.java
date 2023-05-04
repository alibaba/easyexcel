package com.alibaba.easyexcel.test.core.fill.style;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class FillStyleDataTest {

    private static File fileStyle07;
    private static File fileStyle03;
    private static File fileStyleHandler07;
    private static File fileStyleHandler03;
    private static File fileStyleTemplate07;
    private static File fileStyleTemplate03;

    @BeforeAll
    public static void init() {
        fileStyle07 = TestFileUtil.createNewFile("fileStyle07.xlsx");
        fileStyle03 = TestFileUtil.createNewFile("fileStyle03.xls");
        fileStyleHandler07 = TestFileUtil.createNewFile("fileStyleHandler07.xlsx");
        fileStyleHandler03 = TestFileUtil.createNewFile("fileStyleHandler03.xls");
        fileStyleTemplate07 = TestFileUtil.readFile("fill" + File.separator + "style.xlsx");
        fileStyleTemplate03 = TestFileUtil.readFile("fill" + File.separator + "style.xls");
    }

    @Test
    public void t01Fill07() throws Exception {
        fill(fileStyle07, fileStyleTemplate07);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileStyle07));
        XSSFSheet sheet = workbook.getSheetAt(0);
        t01Fill07check(sheet.getRow(1));
        t01Fill07check(sheet.getRow(2));
    }

    private void t01Fill07check(XSSFRow row) {
        XSSFCell cell0 = row.getCell(0);
        Assertions.assertEquals("张三", cell0.getStringCellValue());
        Assertions.assertEquals(49, cell0.getCellStyle().getDataFormat());
        Assertions.assertEquals("FF00B050", cell0.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF7030A0", cell0.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell0.getCellStyle().getFont().getBold());

        XSSFCell cell1 = row.getCell(1);
        Assertions.assertEquals(5.2, cell1.getNumericCellValue(), 1);
        Assertions.assertEquals(0, cell1.getCellStyle().getDataFormat());
        Assertions.assertEquals("FF92D050", cell1.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF4BACC6", cell1.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertFalse(cell1.getCellStyle().getFont().getBold());

        XSSFCell cell2 = row.getCell(2);
        Assertions.assertEquals("2020-01-01 01:01:01",
            DateUtils.format(cell2.getDateCellValue(), "yyyy-MM-dd HH:mm:ss"));
        Assertions.assertEquals("yyyy-MM-dd HH:mm:ss", cell2.getCellStyle().getDataFormatString());
        Assertions.assertEquals("FFFFC000", cell2.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FFC0504D", cell2.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell2.getCellStyle().getFont().getBold());

        XSSFCell cell3 = row.getCell(3);
        Assertions.assertEquals("张三今年5.2岁了", cell3.getStringCellValue());
        Assertions.assertEquals(0, cell3.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF0000", cell3.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FFEEECE1", cell3.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell3.getCellStyle().getFont().getBold());

        XSSFCell cell4 = row.getCell(4);
        Assertions.assertEquals("{.name}忽略，张三", cell4.getStringCellValue());
        Assertions.assertEquals(0, cell4.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFC00000", cell4.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF000000", cell4.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertFalse(cell4.getCellStyle().getFont().getBold());

        XSSFCell cell5 = row.getCell(5);
        Assertions.assertEquals("空", cell5.getStringCellValue());
        Assertions.assertEquals(0, cell5.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFF79646", cell5.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF8064A2", cell5.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertFalse(cell5.getCellStyle().getFont().getBold());
    }

    @Test
    public void t02Fill03() throws Exception {
        fill(fileStyle03, fileStyleTemplate03);
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileStyle03));
        HSSFSheet sheet = workbook.getSheetAt(0);
        t02Fill03check(workbook, sheet.getRow(1));
        t02Fill03check(workbook, sheet.getRow(2));
    }

    private void t02Fill03check(HSSFWorkbook workbook, HSSFRow row) {
        HSSFCell cell0 = row.getCell(0);
        Assertions.assertEquals("张三", cell0.getStringCellValue());
        Assertions.assertEquals(49, cell0.getCellStyle().getDataFormat());
        Assertions.assertEquals("0:8080:0", cell0.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("8080:0:8080", cell0.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell0.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell1 = row.getCell(1);
        Assertions.assertEquals(5.2, cell1.getNumericCellValue(), 1);
        Assertions.assertEquals(0, cell1.getCellStyle().getDataFormat());
        Assertions.assertEquals("9999:CCCC:0", cell1.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("0:8080:8080", cell1.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertFalse(cell1.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell2 = row.getCell(2);
        Assertions.assertEquals("2020-01-01 01:01:01",
            DateUtils.format(cell2.getDateCellValue(), "yyyy-MM-dd HH:mm:ss"));
        Assertions.assertEquals("yyyy-MM-dd HH:mm:ss", cell2.getCellStyle().getDataFormatString());
        Assertions.assertEquals("FFFF:CCCC:0", cell2.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("8080:0:0", cell2.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell2.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell3 = row.getCell(3);
        Assertions.assertEquals("张三今年5.2岁了", cell3.getStringCellValue());
        Assertions.assertEquals(0, cell3.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF:0:0", cell3.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("FFFF:FFFF:9999", cell3.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell3.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell4 = row.getCell(4);
        Assertions.assertEquals("{.name}忽略，张三", cell4.getStringCellValue());
        Assertions.assertEquals(0, cell4.getCellStyle().getDataFormat());
        Assertions.assertEquals("9999:3333:0", cell4.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("3333:3333:3333", cell4.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertFalse(cell4.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell5 = row.getCell(5);
        Assertions.assertEquals("空", cell5.getStringCellValue());
        Assertions.assertEquals(0, cell5.getCellStyle().getDataFormat());
        Assertions.assertEquals("9999:3333:0", cell5.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("CCCC:9999:FFFF", cell5.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertFalse(cell5.getCellStyle().getFont(workbook).getBold());
    }

    private void fill(File file, File template) throws Exception {
        EasyExcel.write(file, FillStyleData.class).withTemplate(template).sheet().doFill(data());
    }

    @Test
    public void t11FillStyleHandler07() throws Exception {
        fillStyleHandler(fileStyleHandler07, fileStyleTemplate07);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileStyleHandler07));
        XSSFSheet sheet = workbook.getSheetAt(0);
        t11FillStyleHandler07check(sheet.getRow(1));
        t11FillStyleHandler07check(sheet.getRow(2));
    }

    private void t11FillStyleHandler07check(XSSFRow row) {
        XSSFCell cell0 = row.getCell(0);
        Assertions.assertEquals("张三", cell0.getStringCellValue());
        Assertions.assertEquals(49, cell0.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFFFF00", cell0.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF808000", cell0.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell0.getCellStyle().getFont().getBold());

        XSSFCell cell1 = row.getCell(1);
        Assertions.assertEquals(5.2, cell1.getNumericCellValue(), 1);
        Assertions.assertEquals(0, cell1.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF0000", cell1.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF800000", cell1.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell1.getCellStyle().getFont().getBold());

        XSSFCell cell2 = row.getCell(2);
        Assertions.assertEquals("2020-01-01 01:01:01",
            DateUtils.format(cell2.getDateCellValue(), "yyyy-MM-dd HH:mm:ss"));
        Assertions.assertEquals("yyyy-MM-dd HH:mm:ss", cell2.getCellStyle().getDataFormatString());
        Assertions.assertEquals("FF008000", cell2.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF003300", cell2.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell2.getCellStyle().getFont().getBold());

        XSSFCell cell3 = row.getCell(3);
        Assertions.assertEquals("张三今年5.2岁了", cell3.getStringCellValue());
        Assertions.assertEquals(0, cell3.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF0000", cell3.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FFEEECE1", cell3.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertTrue(cell3.getCellStyle().getFont().getBold());

        XSSFCell cell4 = row.getCell(4);
        Assertions.assertEquals("{.name}忽略，张三", cell4.getStringCellValue());
        Assertions.assertEquals(0, cell4.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFC00000", cell4.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF000000", cell4.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertFalse(cell4.getCellStyle().getFont().getBold());

        XSSFCell cell5 = row.getCell(5);
        Assertions.assertEquals("空", cell5.getStringCellValue());
        Assertions.assertEquals(0, cell5.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFF79646", cell5.getCellStyle().getFillForegroundColorColor().getARGBHex());
        Assertions.assertEquals("FF8064A2", cell5.getCellStyle().getFont().getXSSFColor().getARGBHex());
        Assertions.assertFalse(cell5.getCellStyle().getFont().getBold());
    }

    @Test
    public void t12FillStyleHandler03() throws Exception {
        fillStyleHandler(fileStyleHandler03, fileStyleTemplate03);
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileStyleHandler03));
        HSSFSheet sheet = workbook.getSheetAt(0);
        t12FillStyleHandler03check(workbook, sheet.getRow(1));
        t12FillStyleHandler03check(workbook, sheet.getRow(2));
    }

    private void t12FillStyleHandler03check(HSSFWorkbook workbook, HSSFRow row) {
        HSSFCell cell0 = row.getCell(0);
        Assertions.assertEquals("张三", cell0.getStringCellValue());
        Assertions.assertEquals(49, cell0.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF:FFFF:0", cell0.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("8080:8080:0", cell0.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell0.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell1 = row.getCell(1);
        Assertions.assertEquals(5.2, cell1.getNumericCellValue(), 1);
        Assertions.assertEquals(0, cell1.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF:0:0", cell1.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("8080:0:0", cell1.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell1.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell2 = row.getCell(2);
        Assertions.assertEquals("2020-01-01 01:01:01",
            DateUtils.format(cell2.getDateCellValue(), "yyyy-MM-dd HH:mm:ss"));
        Assertions.assertEquals("yyyy-MM-dd HH:mm:ss", cell2.getCellStyle().getDataFormatString());
        Assertions.assertEquals("0:8080:0", cell2.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("0:3333:0", cell2.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell2.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell3 = row.getCell(3);
        Assertions.assertEquals("张三今年5.2岁了", cell3.getStringCellValue());
        Assertions.assertEquals(0, cell3.getCellStyle().getDataFormat());
        Assertions.assertEquals("FFFF:0:0", cell3.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("FFFF:FFFF:9999", cell3.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertTrue(cell3.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell4 = row.getCell(4);
        Assertions.assertEquals("{.name}忽略，张三", cell4.getStringCellValue());
        Assertions.assertEquals(0, cell4.getCellStyle().getDataFormat());
        Assertions.assertEquals("9999:3333:0", cell4.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("3333:3333:3333", cell4.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertFalse(cell4.getCellStyle().getFont(workbook).getBold());

        HSSFCell cell5 = row.getCell(5);
        Assertions.assertEquals("空", cell5.getStringCellValue());
        Assertions.assertEquals(0, cell5.getCellStyle().getDataFormat());
        Assertions.assertEquals("9999:3333:0", cell5.getCellStyle().getFillForegroundColorColor().getHexString());
        Assertions.assertEquals("CCCC:9999:FFFF", cell5.getCellStyle().getFont(workbook).getHSSFColor(workbook)
            .getHexString());
        Assertions.assertFalse(cell5.getCellStyle().getFont(workbook).getBold());
    }

    private void fillStyleHandler(File file, File template) throws Exception {
        EasyExcel.write(file, FillStyleData.class).withTemplate(template).sheet()
            .registerWriteHandler(new AbstractVerticalCellStyleStrategy() {

                @Override
                protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
                    WriteCellStyle writeCellStyle = new WriteCellStyle();
                    WriteFont writeFont = new WriteFont();
                    writeCellStyle.setWriteFont(writeFont);
                    writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                    writeFont.setBold(true);
                    if (context.getColumnIndex() == 0) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                        writeFont.setColor(IndexedColors.DARK_YELLOW.getIndex());
                    }
                    if (context.getColumnIndex() == 1) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        writeFont.setColor(IndexedColors.DARK_RED.getIndex());
                    }
                    if (context.getColumnIndex() == 2) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        writeFont.setColor(IndexedColors.DARK_GREEN.getIndex());
                    }
                    if (context.getColumnIndex() == 3) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                        writeFont.setColor(IndexedColors.DARK_BLUE.getIndex());
                    }
                    if (context.getColumnIndex() == 4) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                        writeFont.setColor(IndexedColors.DARK_YELLOW.getIndex());
                    }
                    if (context.getColumnIndex() == 5) {
                        writeCellStyle.setFillForegroundColor(IndexedColors.TEAL.getIndex());
                        writeFont.setColor(IndexedColors.DARK_TEAL.getIndex());
                    }
                    return writeCellStyle;
                }

                @Override
                protected WriteCellStyle headCellStyle(Head head) {
                    return null;
                }

            })
            .doFill(data());
    }

    private List<FillStyleData> data() throws Exception {
        List<FillStyleData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FillStyleData fillData = new FillStyleData();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
            fillData.setDate(DateUtils.parseDate("2020-01-01 01:01:01"));
            if (i == 5) {
                fillData.setName(null);
            }
        }
        return list;
    }

}
