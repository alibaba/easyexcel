package com.alibaba.easyexcel.test.temp.issue1595;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 读的常见写法
 *
 * @author Jiaju Zhuang
 */
@Ignore
public class ReadOLAY {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadOLAY.class);

    @Test
    public void simpleRead() {
        String fileName = TestFileUtil.getPath() + "issue1595" + File.separator + "OLAY.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, OLAYData.class, new OLAYDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
}
