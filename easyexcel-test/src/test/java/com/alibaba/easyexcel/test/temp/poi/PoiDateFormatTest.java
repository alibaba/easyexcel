package com.alibaba.easyexcel.test.temp.poi;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

public class PoiDateFormatTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiDateFormatTest.class);

    @Test
    public void read() throws IOException {
        String file
            = "/Users/zhuangjiaju/IdeaProjects/easyexcel/easyexcel-test/src/test/resources/dataformat/dataformat.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook( file);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(7);
        XSSFCell cell = row.getCell(0);
        LOGGER.info("dd{}", cell.getDateCellValue());
        LOGGER.info("dd{}", cell.getNumericCellValue());

        LOGGER.info("dd{}", DateUtil.isCellDateFormatted(cell));


    }

}
