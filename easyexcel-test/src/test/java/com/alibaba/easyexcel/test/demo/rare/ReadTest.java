package com.alibaba.easyexcel.test.demo.rare;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * 记录一些不太常见的案例
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ReadTest {


    /**
     * 当excel有需要转义的 如x005特殊符号时需要通过utf decode解码
     *
     **/
    @Test
    public void readX005() throws Exception{
        String fileName = TestFileUtil.pathBuild().sub("temp").sub("utfdecode").sub("demo.xlsx").getPath();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileName);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        XSSFRow row = xssfSheet.getRow(0);
        String poiValue = row.getCell(0).getStringCellValue();
        List<Map<Integer,Object>> list = EasyExcel.read(fileName)
            //.useDefaultListener(false)
            .sheet(0)
            .headRowNumber(0).doReadSync();
        Map<Integer, Object> easyExcelRow = list.get(0);
        Assert.assertEquals(easyExcelRow.get(0).toString(),poiValue);
    }
}
