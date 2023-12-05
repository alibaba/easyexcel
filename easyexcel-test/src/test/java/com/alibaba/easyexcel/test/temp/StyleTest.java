package com.alibaba.easyexcel.test.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/

public class StyleTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StyleTest.class);

    @Test
    public void test() {
        List<Object> list = EasyExcel.read("D:\\test\\styleTest.xls").sheet().headRowNumber(0).doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void poi() throws Exception {
        InputStream is = new FileInputStream("D:\\test\\styleTest.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        HSSFRow hssfRow = hssfSheet.getRow(0);
        System.out.println(hssfRow.getCell(0).getCellStyle().getDataFormatString());
        DataFormatter formatter = new DataFormatter();
        System.out.println(hssfRow.getCell(0).getNumericCellValue());
        System.out.println(hssfRow.getCell(1).getNumericCellValue());
        System.out.println(hssfRow.getCell(2).getNumericCellValue());
        System.out.println(hssfRow.getCell(0).getCellStyle().getDataFormatString());
        System.out.println(hssfRow.getCell(1).getCellStyle().getDataFormatString());
        System.out.println(hssfRow.getCell(2).getCellStyle().getDataFormatString());

    }

    @Test
    public void poi07() throws Exception {
        InputStream is = new FileInputStream("D:\\test\\styleTest.xlsx");
        Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel 2003/2007/2010 都是可以处理的
        Sheet sheet = workbook.getSheetAt(0);
        Row hssfRow = sheet.getRow(0);
        System.out.println(hssfRow.getCell(0).getCellStyle().getDataFormatString());
        DataFormatter formatter = new DataFormatter();
        System.out.println(hssfRow.getCell(0).getNumericCellValue());
        System.out.println(hssfRow.getCell(1).getNumericCellValue());
        System.out.println(hssfRow.getCell(2).getNumericCellValue());
        System.out.println(hssfRow.getCell(0).getCellStyle().getDataFormat());
        System.out.println(hssfRow.getCell(1).getCellStyle().getDataFormat());
        System.out.println(hssfRow.getCell(2).getCellStyle().getDataFormat());
        System.out.println(hssfRow.getCell(3).getCellStyle().getDataFormat());
        System.out.println(hssfRow.getCell(0).getCellStyle().getDataFormatString());
        System.out.println(hssfRow.getCell(1).getCellStyle().getDataFormatString());
        System.out.println(hssfRow.getCell(2).getCellStyle().getDataFormatString());
        System.out.println(hssfRow.getCell(3).getCellStyle().getDataFormatString());
        isDate(hssfRow.getCell(0));
        isDate(hssfRow.getCell(1));
        isDate(hssfRow.getCell(2));
        isDate(hssfRow.getCell(3));

    }

    @Test
    public void poi0701() throws Exception {
        InputStream is = new FileInputStream("D:\\test\\f1.xlsx");
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);
        print(sheet.getRow(0).getCell(0));
        print(sheet.getRow(1).getCell(0));
        print(sheet.getRow(2).getCell(0));
        print(sheet.getRow(3).getCell(0));
    }

    @Test
    public void poi0702() throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\test\\t2.xlsx"));
        workbook = WorkbookFactory.create(new File("D:\\test\\t2.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        System.out.println(row.getCell(0).getNumericCellValue());
    }

    @Test
    public void poi0703() throws Exception {
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream("D:\\test\\t2.xlsx"));
            System.out.println(poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new File("D:\\test\\t2.xlsx"));
            System.out.println(poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream("D:\\test\\t222.xlsx"));
            System.out.println(poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new File("D:\\test\\t222.xlsx"));
            System.out.println(poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void print(Cell cell) {
        System.out.println(
            DateUtil.isADateFormat(cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString()));
        System.out.println(cell.getCellStyle().getDataFormat());
        System.out.println(cell.getCellStyle().getDataFormatString());
        DataFormatter f = new DataFormatter();
        System.out.println(f.formatCellValue(cell));
        if (cell.getCellStyle().getDataFormatString() != null) {

        }
        ExcelStyleDateFormatter ff = new ExcelStyleDateFormatter(cell.getCellStyle().getDataFormatString());

    }

    @Test
    public void testFormatter() throws Exception {
        ExcelStyleDateFormatter ff = new ExcelStyleDateFormatter("yyyy年m月d日");

        System.out.println(ff.format(new Date()));
    }

    @Test
    public void testFormatter2() throws Exception {
        StyleData styleData = new StyleData();
        Field field = styleData.getClass().getDeclaredField("byteValue");
        LOGGER.info("field:{}", field.getType().getName());
        field = styleData.getClass().getDeclaredField("byteValue2");
        LOGGER.info("field:{}", field.getType().getName());
        field = styleData.getClass().getDeclaredField("byteValue4");
        LOGGER.info("field:{}", field.getType());
        field = styleData.getClass().getDeclaredField("byteValue3");
        LOGGER.info("field:{}", field.getType());
    }

    @Test
    public void testFormatter3() throws Exception {
        LOGGER.info("field:{}", Byte.class == Byte.class);
    }

    private void isDate(Cell cell) {
        System.out.println(
            DateUtil.isADateFormat(cell.getCellStyle().getDataFormat(), cell.getCellStyle().getDataFormatString()));
        //System.out.println(HSSFDateUtil.isCellDateFormatted(cell));
        DataFormatter f = new DataFormatter();
        System.out.println(f.formatCellValue(cell));

    }

    @Test
    public void testBuiltinFormats() throws Exception {
        System.out.println(BuiltinFormats.getBuiltinFormat(48));
        System.out.println(BuiltinFormats.getBuiltinFormat(57));
        System.out.println(BuiltinFormats.getBuiltinFormat(28));

    }

}
