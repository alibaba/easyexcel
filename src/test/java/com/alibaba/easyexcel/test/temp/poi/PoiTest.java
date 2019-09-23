package com.alibaba.easyexcel.test.temp.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.TestFileUtil;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class PoiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiTest.class);

    @Test
    public void lastRowNum() throws IOException {
        String file = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }

    @Test
    public void lastRowNumXSSF() throws IOException {
        String file = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }

    @Test
    public void lastRowNum233() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        Workbook xx=new XSSFWorkbook(file);

        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook();
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);

        Cell cell = xssfSheet.getRow(0).createCell(9);
        cell.setCellValue("testssdf是士大夫否t");

        FileOutputStream fileout = new FileOutputStream("d://test/r2" + System.currentTimeMillis() + ".xlsx");
        xssfWorkbook.write(fileout);
        xssfWorkbook.close();
    }


    @Test
    public void lastRowNum2332222() throws IOException {
        String file = TestFileUtil.getPath() + "fill" + File.separator + "simple.xlsx";
        Workbook xx=new XSSFWorkbook(file);
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook();
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
