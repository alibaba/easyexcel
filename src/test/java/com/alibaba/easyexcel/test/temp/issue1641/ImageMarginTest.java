package com.alibaba.easyexcel.test.temp.issue1641;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageMarginTest {
    /**
     *
     * 测试导入图片后是否会预留边距
     */
    @Test
    public void MutiImageWrite() {
        String fileName = TestFileUtil.getPath() + "MutiImageWrite" + System.currentTimeMillis() + ".xlsx";
        List<ImageMarginData> list = new ArrayList<ImageMarginData>();
        ImageMarginData imageData = new ImageMarginData();
        ImageMarginData imageData2 = new ImageMarginData();
        list.add(imageData);
        list.add(imageData2);
        String imagePath = "src/test/java/com/alibaba/easyexcel/test/temp/issue1641/2.jpg";
        String imagePath2 = "src/test/java/com/alibaba/easyexcel/test/temp/issue1641/img.png";
        imageData.setFile(new File(imagePath));
        imageData2.setFile(new File(imagePath2));
        EasyExcel.write(fileName, ImageMarginData.class).sheet().doWrite(list);
    }
    @Test
    public void ImageWrite(){
        String fileName = TestFileUtil.getPath() + "MutiImageWrite" + System.currentTimeMillis() + ".xlsx";
        List<ImageMarginData> list = new ArrayList<ImageMarginData>();
        ImageMarginData imageData = new ImageMarginData();
        list.add(imageData);
        String imagePath = "src/test/java/com/alibaba/easyexcel/test/temp/issue1641/2.jpg";
        imageData.setFile(new File(imagePath));
        EasyExcel.write(fileName, ImageMarginData.class).sheet().doWrite(list);
    }
}
