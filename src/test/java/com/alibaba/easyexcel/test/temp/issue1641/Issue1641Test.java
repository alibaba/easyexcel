package com.alibaba.easyexcel.test.temp.issue1641;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Issue1641Test {
    public static void main(String[] args) throws IOException {
        String fileName = TestFileUtil.getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";
        InputStream inputStream = null;
        try {
            List<ImageDTO> list = new ArrayList<ImageDTO>();
            ImageDTO imageData = new ImageDTO();
            list.add(imageData);
            String imagePath = "src/test/java/com/alibaba/easyexcel/test/temp/issue1641/1.jpg";
            inputStream = FileUtils.openInputStream(new File(imagePath));
            imageData.setWorkerImg(inputStream);
            EasyExcel.write(fileName, ImageDTO.class).sheet().doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}

