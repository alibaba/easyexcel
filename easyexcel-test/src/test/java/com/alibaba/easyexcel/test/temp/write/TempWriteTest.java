package com.alibaba.easyexcel.test.temp.write;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.easyexcel.test.demo.read.CustomStringStringConverter;
import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.demo.write.ImageDemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.ImageData.ImageType;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;

@Ignore
@Slf4j
public class TempWriteTest {

    @Test
    public void write() {
        TempWriteData tempWriteData = new TempWriteData();
        tempWriteData.setName("zs\r\n \\ \r\n t4");
        EasyExcel.write(TestFileUtil.getPath() + "TempWriteTest" + System.currentTimeMillis() + ".xlsx",
                TempWriteData.class)
            .sheet()
            .registerConverter(new CustomStringStringConverter())
            .doWrite(ListUtils.newArrayList(tempWriteData));

        EasyExcel.write(TestFileUtil.getPath() + "TempWriteTest" + System.currentTimeMillis() + ".xlsx",
                TempWriteData.class)
            .sheet()
            .doWrite(ListUtils.newArrayList(tempWriteData));

    }

    @Test
    public void cglib() {
        TempWriteData tempWriteData = new TempWriteData();
        tempWriteData.setName("1");
        tempWriteData.setName1("2");
        BeanMap beanMap = BeanMapUtils.create(tempWriteData);

        log.info("d1{}", beanMap.get("name"));
        log.info("d2{}", beanMap.get("name1"));

        TempWriteData tempWriteData2 = new TempWriteData();

        Map<String, String> map = new HashMap<>();
        map.put("name", "zs");
        BeanMap beanMap2 = BeanMapUtils.create(tempWriteData2);
        beanMap2.putAll(map);
        log.info("3{}", tempWriteData2.getName());

    }

    @Test
    public void imageWrite() throws Exception {
        //String fileName = TestFileUtil.getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";
        //
        //// 这里 需要指定写用哪个class去写
        //try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
        //    // 这里注意 如果同一个sheet只要创建一次
        //    WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        //    // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
        //    for (int i = 0; i < 5; i++) {
        //        // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
        //        List<DemoData> data = data();
        //        excelWriter.write(data, writeSheet);
        //    }
        //}
    }

    @Test
    public void imageWritePoi() throws Exception {
        String file = "/Users/zhuangjiaju/test/imagetest" + System.currentTimeMillis() + ".xlsx";
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("测试");
        CreationHelper helper = workbook.getCreationHelper();
        SXSSFDrawing sxssfDrawin = sheet.createDrawingPatriarch();

        byte[] imagebyte = FileUtils.readFileToByteArray(new File("/Users/zhuangjiaju/Documents/demo.jpg"));

        for (int i = 0; i < 1 * 10000; i++) {
            SXSSFRow row = sheet.createRow(i);
            SXSSFCell cell = row.createCell(0);
            cell.setCellValue(i);
            int pictureIdx = workbook.addPicture(imagebyte, Workbook.PICTURE_TYPE_JPEG);
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(i);
            // 插入图片
            Picture pict = sxssfDrawin.createPicture(anchor, pictureIdx);
            pict.resize();
            log.info("新增行:{}", i);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        workbook.close();
    }
}
