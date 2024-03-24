package com.alibaba.easyexcel.test.core.model;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.ReadDefaultReturnEnum;
import com.alibaba.excel.exception.ExcelDataConvertGroupException;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Jasonyou
 * @date 2024/03/24
 */
@Slf4j
public class ModelBuildEventListenerTest {

        private static File file07;
        private static File file03;
        private static File fileCsv;
        private static File fileRepeat07;
        private static File fileRepeat03;
        private static File fileRepeatCsv;
        private static LocalDateTime localDateTime;
        private static String dateTimeStr;
        @BeforeAll
        public static void init() {
                file07 = TestFileUtil.createNewFile("Model07.xlsx");
                file03 = TestFileUtil.createNewFile("Model03.xls");
                fileCsv = TestFileUtil.createNewFile("ModelCsv.csv");
                fileRepeat07 = TestFileUtil.createNewFile("ModelRepeat07.xlsx");
                fileRepeat03 = TestFileUtil.createNewFile("ModelRepeat03.xls");
                fileRepeatCsv = TestFileUtil.createNewFile("ModelRepeatCsv.csv");
                dateTimeStr="2020-01-01 01:01:01";
        }

        @Test
        public void t01ReadAndWrite07WithModel() throws Exception {
                readAndWrite(file07, fileRepeat07, false);
        }
        @Test
        public void t02ReadAndWrite03() throws Exception {
                readAndWrite(file03, fileRepeat03, false);
        }

        @Test
        public void t03ReadAndWriteCsv() throws Exception {
                readAndWrite(fileCsv, fileRepeatCsv, true);
        }

        @Test void testConvertGroup()throws Exception{
                Assertions.assertThrows(ExcelDataConvertGroupException.class, ()->{
                        try {
                                EasyExcel.write(file07, DemoData.class).sheet().doWrite(data());
                                EasyExcel.read(file07).head(DemoDataConvertErro.class).sheet().doReadSync();

                        } catch (ExcelDataConvertGroupException e) {
                                log.info("testConvertGroup:{}",e.getMessage());
                                throw e;
                        }
                        });
        }

        private void readAndWrite(File file, File fileRepeat, boolean isCsv) throws Exception {
                EasyExcel.write(file, DemoData.class).sheet().doWrite(data());
                List<DemoData> result = EasyExcel.read(file).head(DemoData.class).sheet().doReadSync();
                Assertions.assertEquals(10, result.size());
                DemoData data10 = result.get(9);
                Assertions.assertEquals("string19", data10.getString());
                Assertions.assertEquals(109, data10.getDoubleData());
                Assertions.assertEquals(DateUtils.parseDate(dateTimeStr),data10.getDate());

                List<Map<Integer, Object>> actualDataList = EasyExcel.read(file)
                        .readDefaultReturn(ReadDefaultReturnEnum.ACTUAL_DATA)
                        .sheet()
                        .doReadSync();

                log.info("actualDataList:{}", JSON.toJSONString(actualDataList, JSONWriter.Feature.PrettyFormat) );
                Assertions.assertEquals(10, actualDataList.size());
                Map<Integer, Object> actualData10 = actualDataList.get(9);
                Assertions.assertEquals("string19", actualData10.get(0));
                if (isCsv) {
                        //  CSV only string type
                        Assertions.assertEquals("109.0", actualData10.get(1));
                        Assertions.assertEquals(dateTimeStr, actualData10.get(2));
                } else {
                        Assertions.assertEquals(0, new BigDecimal("109").compareTo((BigDecimal)actualData10.get(1)));
                        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1, 1), actualData10.get(2));
                }

                List<Map<Integer, ReadCellData<?>>> readCellDataList = EasyExcel.read(file)
                        .readDefaultReturn(ReadDefaultReturnEnum.READ_CELL_DATA)
                        .sheet()
                        .doReadSync();
                log.info("readCellDataList:{}", JSON.toJSONString(readCellDataList, JSONWriter.Feature.PrettyFormat));
                Assertions.assertEquals(10, readCellDataList.size());
                Map<Integer, ReadCellData<?>> readCellData10 = readCellDataList.get(9);
                Assertions.assertEquals("string19", readCellData10.get(0).getData());
                if (isCsv) {
                        //  CSV only string type
                        Assertions.assertEquals("109.0", readCellData10.get(1).getData());
                        Assertions.assertEquals(dateTimeStr, readCellData10.get(2).getData());
                } else {
                        Assertions.assertEquals(0, new BigDecimal("109").compareTo((BigDecimal)readCellData10.get(1).getData()));
                        Assertions.assertEquals(LocalDateTime.of(2020, 1, 1, 1, 1, 1), readCellData10.get(2).getData());
                }

                EasyExcel.write(fileRepeat, DemoData.class).sheet().doWrite(result);
                result = EasyExcel.read(fileRepeat).head(DemoData.class).sheet().doReadSync();
                Assertions.assertEquals(10, result.size());
                data10 = result.get(9);
                Assertions.assertEquals("string19", data10.getString());
                Assertions.assertEquals(109, data10.getDoubleData());
                Assertions.assertEquals(DateUtils.parseDate(dateTimeStr),data10.getDate());
        }

        private List<DemoData> data() throws Exception {
                List<DemoData> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                        DemoData demoData = new DemoData();
                        demoData.setString("string1"+i);
                        demoData.setDoubleData((double) (100+i));
                        demoData.setDate(DateUtils.parseDate(dateTimeStr));
                        list.add(demoData);
                }
                return list;
        }
}
