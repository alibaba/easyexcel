package com.alibaba.easyexcel.test.temp.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.util.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/

public class PoiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiTest.class);

    @Test
    public void lastRowNum() throws IOException {
        String file = "/Users/zhuangjiaju/test/test3.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("dd{}", row.getCell(0).getColumnIndex());
        Date date = row.getCell(1).getDateCellValue();

    }

    @Test
    public void lastRowNumXSSF() throws IOException {

        String file = "/Users/zhuangjiaju/test/test3 copy 10.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(1);
        LOGGER.info("dd{}", row.getCell(0).getRow().getRowNum());
        LOGGER.info("dd{}", xssfSheet.getLastRowNum());

        XSSFCellStyle cellStyle = row.getCell(0).getCellStyle();
        LOGGER.info("size1:{}", cellStyle.getFontIndexAsInt());

        XSSFCellStyle cellStyle1 = xssfWorkbook.createCellStyle();
        LOGGER.info("size2:{}", cellStyle1.getFontIndexAsInt());

        cellStyle1.cloneStyleFrom(cellStyle);
        LOGGER.info("size3:{}", cellStyle1.getFontIndexAsInt());

        LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getIndex());
        LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getIndexed());
        XSSFColor myColor = new XSSFColor(cellStyle1.getFont().getXSSFColor().getRGB(), null);
        LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getRGB());
        LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getARGBHex());

        LOGGER.info("bbb:{}", cellStyle1.getFont().getBold());
        LOGGER.info("bbb:{}", cellStyle1.getFont().getFontName());

        XSSFFont xssfFont = xssfWorkbook.createFont();

        xssfFont.setColor(myColor);

        xssfFont.setFontHeightInPoints((short)50);
        xssfFont.setBold(Boolean.TRUE);
        cellStyle1.setFont(xssfFont);
        cellStyle1.setFillForegroundColor(IndexedColors.PINK.getIndex());

        LOGGER.info("aaa:{}", cellStyle1.getFont().getColor());

        row.getCell(1).setCellStyle(cellStyle1);
        row.getCell(1).setCellValue(3334l);

        XSSFCellStyle cellStyle2 = xssfWorkbook.createCellStyle();
        cellStyle2.cloneStyleFrom(cellStyle);
        cellStyle2.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //cellStyle2.setFont(cellStyle1.getFont());
        row.getCell(2).setCellStyle(cellStyle2);
        row.getCell(2).setCellValue(3334l);
        //LOGGER.info("date1:{}",  row.getCell(0).getStringCellValue());
        //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).getIndex());
        //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isRGB());
        //LOGGER.info("date4:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isIndexed());
        //LOGGER.info("date3:{}", cellStyle.getFont().getXSSFColor().getRGB());
        //LOGGER.info("date4:{}", cellStyle.getFont().getCTFont().getColorArray(0).getRgb());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        xssfWorkbook.close();
    }

    @Test
    public void lastRowNumXSSFv22() throws IOException {

        String file = "/Users/zhuangjiaju/test/test3 copy 2.xls";
        HSSFWorkbook xssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        HSSFRow row = xssfSheet.getRow(1);
        LOGGER.info("dd{}", row.getCell(0).getRow().getRowNum());
        LOGGER.info("dd{}", xssfSheet.getLastRowNum());

        HSSFCellStyle cellStyle = row.getCell(0).getCellStyle();
        LOGGER.info("单元格1的字体:{}", cellStyle.getFontIndexAsInt());

        HSSFCellStyle cellStyle1 = xssfWorkbook.createCellStyle();
        LOGGER.info("size2:{}", cellStyle1.getFontIndexAsInt());

        cellStyle1.cloneStyleFrom(cellStyle);
        LOGGER.info("单元格2的字体:{}", cellStyle1.getFontIndexAsInt());

        LOGGER.info("bbb:{}", cellStyle1.getFont(xssfWorkbook).getColor());

        HSSFFont xssfFont = xssfWorkbook.createFont();

        xssfFont.setColor(cellStyle1.getFont(xssfWorkbook).getColor());
        xssfFont.setFontHeightInPoints((short)50);
        xssfFont.setBold(Boolean.TRUE);
        cellStyle1.setFont(xssfFont);
        cellStyle1.setFillForegroundColor(IndexedColors.PINK.getIndex());

        LOGGER.info("aaa:{}", cellStyle1.getFont(xssfWorkbook).getColor());

        row.getCell(1).setCellStyle(cellStyle1);
        row.getCell(1).setCellValue(3334l);

        HSSFCellStyle cellStyle2 = xssfWorkbook.createCellStyle();
        cellStyle2.cloneStyleFrom(cellStyle);
        cellStyle2.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        //cellStyle2.setFont(cellStyle1.getFont());
        row.getCell(2).setCellStyle(cellStyle2);
        row.getCell(2).setCellValue(3334l);
        //LOGGER.info("date1:{}",  row.getCell(0).getStringCellValue());
        //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).getIndex());
        //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isRGB());
        //LOGGER.info("date4:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isIndexed());
        //LOGGER.info("date3:{}", cellStyle.getFont().getXSSFColor().getRGB());
        //LOGGER.info("date4:{}", cellStyle.getFont().getCTFont().getColorArray(0).getRgb());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        xssfWorkbook.close();
    }

    @Test
    public void lastRowNum233() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        Workbook xx = new XSSFWorkbook(file);
        System.out.println(new File(file).exists());

        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook();
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);

        Cell cell = xssfSheet.getRow(0).createCell(9);
        cell.setCellValue("testssdf是士大夫否t");

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        xssfWorkbook.write(fileout);
        xssfWorkbook.close();
    }

    @Test
    public void lastRowNum255() throws IOException, InvalidFormatException {
        String file = "D:\\test\\complex.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook);
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
        xssfSheet.shiftRows(1, 4, 10, true, true);

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        sxssfWorkbook.write(fileout);
        sxssfWorkbook.dispose();
        sxssfWorkbook.close();

        xssfWorkbook.close();
    }

    @Test
    public void cp() throws IOException, InvalidFormatException {
        String file = "d://test/tt.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }

    @Test
    public void lastRowNum233443() throws IOException, InvalidFormatException {
        String file = "d://test/em0.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        System.out.println(xssfSheet.getLastRowNum());
        System.out.println(xssfSheet.getRow(0));

    }

    @Test
    public void lastRowNum2333() throws IOException, InvalidFormatException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook);
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Cell cell = xssfSheet.getRow(0).createCell(9);
        cell.setCellValue("testssdf是士大夫否t");

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        sxssfWorkbook.write(fileout);
        sxssfWorkbook.dispose();
        sxssfWorkbook.close();

        xssfWorkbook.close();
    }

    @Test
    public void testread() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";

        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
        //
        // Cell cell = xssfSheet.getRow(0).createCell(9);

        String file1 = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";

        SXSSFWorkbook xssfWorkbook1 = new SXSSFWorkbook(new XSSFWorkbook(file1));
        Sheet xssfSheet1 = xssfWorkbook1.getXSSFWorkbook().getSheetAt(0);

        // Cell cell1 = xssfSheet1.getRow(0).createCell(9);

        xssfWorkbook.close();
        xssfWorkbook1.close();
    }

    @Test
    public void testreadRead() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        FileUtils.readFileToByteArray(new File(file));
    }

    @Test
    public void lastRowNum2332222() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);

        Cell cell = xssfSheet.getRow(0).createCell(9);
        cell.setCellValue("testssdf是士大夫否t");

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        xssfWorkbook.write(fileout);
    }

    @Test
    public void lastRowNum23443() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        xssfWorkbook.write(fileout);
        xssfWorkbook.close();
    }

    @Test
    public void lastRowNum2() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getPhysicalNumberOfRows());
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        LOGGER.info("一共行数:{}", xssfSheet.getFirstRowNum());

    }

    @Test
    public void lastRowNumXSSF2() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
    }

}
