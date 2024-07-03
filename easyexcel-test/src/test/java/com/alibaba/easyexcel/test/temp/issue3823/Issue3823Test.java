package com.alibaba.easyexcel.test.temp.issue3823;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson2.JSON;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Issue3823Test {




    @Test
    public void test() throws Exception {

        String fileName = TestFileUtil.getPath() + "temp" + File.separator + "issue3823" + File.separator + "bug.xlsx";

        EasyExcel.read(fileName, new ReadListener() {
                    @Override
                    public void invoke(Object data, AnalysisContext context) {
                        System.out.println(JSON.toJSONString(data));
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                }).sheet().doRead();


        XSSFWorkbook workbook = new XSSFWorkbook(fileName);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            StringBuilder sb = new StringBuilder();
            for (Cell cell : row) {
                sb.append(cell.getStringCellValue()).append(" ");
            }
            System.out.println(sb);
        }
    }

}
