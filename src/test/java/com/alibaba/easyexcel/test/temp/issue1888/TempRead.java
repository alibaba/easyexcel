package com.alibaba.easyexcel.test.temp.issue1888;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import org.junit.Test;

public class TempRead {

    /**
     * Read sheet by name from .xls file.(single sheet)
     */
    @Test
    public void ReadIssueData1() {
        String fileName = "src/test/java/com/alibaba/easyexcel/test/temp/issue1888/INB21040528-332360-USA.xls";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();
            ReadSheet readSheet1 =
                EasyExcel.readSheet("F2V").head(TestData.class)
                    .registerReadListener(new TestListener()).build();
            excelReader.read(readSheet1);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    /**
     * Read sheet by name from .xls file.(multiple sheets)
     */
    @Test
    public void ReadIssueData2() {
        String fileName = "src/test/java/com/alibaba/easyexcel/test/temp/issue1888/INB21040528-332360-USA.xls";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName).build();
            ReadSheet readSheet1 =
                EasyExcel.readSheet("F2V").head(TestData.class)
                    .registerReadListener(new TestListener()).build();
            ReadSheet readSheet2 =
                EasyExcel.readSheet("DATA").head(TestData.class)
                    .registerReadListener(new TestListener()).build();
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
}
