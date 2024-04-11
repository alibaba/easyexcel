package com.alibaba.easyexcel.test.temp.poi;

import java.io.FileInputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Slf4j
public class PoiImageTest {

    @Test
    public void xls() throws Exception {

        FileInputStream fis = new FileInputStream("/Users/zhuangjiaju/测试数据/imagetest.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(fis);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFPatriarch patriarch = sheet.getDrawingPatriarch();

        for (HSSFShape shape : patriarch.getChildren()) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture)shape;
                HSSFPictureData pictureData = picture.getPictureData();
                byte[] data = pictureData.getData();

                log.info("图片:{}", data.length);
            }
        }

        workbook.close();
        fis.close();
    }

    @Test
    public void xlsx() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/zhuangjiaju/测试数据/imagetest.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFDrawing drawing = sheet.getDrawingPatriarch();

        for (XSSFShape shape : drawing.getShapes()) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture picture = (XSSFPicture)shape;
                XSSFPictureData pictureData = picture.getPictureData();
                byte[] data = pictureData.getData();
                log.info("图片:{}", data.length);
                log.info("图片:{}", pictureData.getPictureType());
                log.info("图片:{}", pictureData.getMimeType());
                log.info("图片:{}", pictureData.suggestFileExtension());
                //log.info("图片:{}", pictureData.suggestFileExtension());

            }
        }

        workbook.close();
        fis.close();
    }

}
