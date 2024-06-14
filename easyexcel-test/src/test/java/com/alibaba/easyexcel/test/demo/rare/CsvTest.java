package com.alibaba.easyexcel.test.demo.rare;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.Test;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Collections;

/**
 * @author youzhiqiang
 * @date 2024/6/14
 */
@Slf4j
public class CsvTest {


    @Test
    public void customer() {
        String fileName = TestFileUtil.getPath() + "sheetWriteHandlerForCsv" + System.currentTimeMillis() + ".csv";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName)
                .registerWriteHandler(new SheetWriteHandler() {
                    @Override
                    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
                        Sheet sheet = writeSheetHolder.getSheet();

                        for (int column = 0; column < 10; column++) {
                            for (int rowIndex = 0; rowIndex < 20; rowIndex++) {
                                Row row = sheet.getRow(rowIndex);
                                if (row == null) {
                                    row = sheet.createRow(rowIndex);
                                }

                                Cell cell = row.getCell(column);
                                if (cell == null) {
                                    cell = row.createCell(column);
                                }
                                cell.setCellValue("测试" + column + ":" + rowIndex);
                            }
                        }
                    }
                })
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        excelWriter.write(Collections.emptyList(), writeSheet);
        excelWriter.finish();
    }

}
