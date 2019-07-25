package com.alibaba.easyexcel.test.read.large;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.FileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;

/**
 * Simple data test
 * 
 * @author zhuangjiaju
 */
public class LargeData07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeData07Test.class);

    @Test
    public void large() throws Exception {
        EasyExcelFactory.read().registerReadListener(listener).file(in).sheet().sheetNo(sheetNo).doRead().finish();

//
//        public static List<Object> read(InputStream in, Sheet sheet) {
//            final List<Object> rows = new ArrayList<Object>();
//            new ExcelReader(in, null, new AnalysisEventListener<Object>() {
//                @Override
//                public void invoke(Object object, AnalysisContext context) {
//                    rows.add(object);
//                }
//
//                @Override
//                public void doAfterAllAnalysed(AnalysisContext context) {}
//            }, false).read(sheet);
//            return rows;
//        }
        // LOGGER.info("start");
        // long start = System.currentTimeMillis();
        // // InputStream inputStream = FileUtil.readFile("large/large07.xlsx");
        // ExcelReader excelReader = EasyExcelFactory.getReader(null, new LargeDataListener());
        // excelReader.read(new Sheet(1, 1));
        // // inputStream.close();
        // LOGGER.info("time:{}", System.currentTimeMillis() - start);
    }

    @Test
    public void hello() throws Exception {
        LOGGER.info("start");
    }
}
