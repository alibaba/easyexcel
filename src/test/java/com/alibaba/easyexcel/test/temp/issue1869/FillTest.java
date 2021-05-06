package com.alibaba.easyexcel.test.temp.issue1869;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FillTest {

    /**
     * Test fill XLS.
     */
    @Test
    public void testXLS() {

        String templateFileName =
            TestFileUtil.getPath() + "issue1869" + File.separator + "list.xls";
        String fileName =
            TestFileUtil.getPath() + "issue1869" + File.separator + "1869.xls";

        WriteCellStyle headerWriteCellStyle = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setWrapped(true);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(
                headerWriteCellStyle,
                contentWriteCellStyle);

        ExcelWriter excelWriter = EasyExcel.write(fileName)
            .withTemplate(templateFileName)
            .excelType(ExcelTypeEnum.XLS)
            .registerWriteHandler(horizontalCellStyleStrategy)
            .build();
        // Wrap fillConfig.
        FillConfig build = FillConfig.builder().Wrapped(true).build();

        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        excelWriter.fill(data(), build, writeSheet);
        excelWriter.fill(data(), build, writeSheet);

        excelWriter.finish();
    }

    /**
     * Test fill XLSX.
     */
    @Test
    public void testXLSX() {

        String templateFileName =
            TestFileUtil.getPath() + "issue1869" + File.separator + "list.xlsx";
        String fileName =
            TestFileUtil.getPath() + "issue1869" + File.separator + "1869.xlsx";

        WriteCellStyle headerWriteCellStyle = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setWrapped(true);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(
                headerWriteCellStyle,
                contentWriteCellStyle);

        ExcelWriter excelWriter = EasyExcel.write(fileName)
            .withTemplate(templateFileName)
            .excelType(ExcelTypeEnum.XLSX)
            .registerWriteHandler(horizontalCellStyleStrategy)
            .build();
        // Wrap fillConfig.
        FillConfig build = FillConfig.builder().Wrapped(true).build();

        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        excelWriter.fill(data(), build, writeSheet);
        excelWriter.fill(data(), build, writeSheet);

        excelWriter.finish();
    }

    private List<FillData> data() {
        List<FillData> list = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName("张三aaaaaaaaaaaaaaaaaaaaaaaaaa");
            fillData.setNumber(5.2);
        }
        return list;
    }
}
