package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadImageTest {

    /**
     * 使用文件名读图片信息，默认读所有sheet中的图片，图片数据放在imageDataList中
     *
     * @author Pengliang Zhao
     */
    @Test
    public void readImageWithFileNameTest() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);
        EasyExcel.readImage(fileName, imageDataReadListener);
    }

    /**
     * 使用输入流读图片信息，默认读所有sheet中的图片，图片数据放在imageDataList中
     */
    @Test
    public void readImageWithStreamTest() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);
        File imageFile = new File(fileName);
        InputStream in = new FileInputStream(imageFile);
        EasyExcel.readImage(in, 1, imageDataReadListener);
        in.close();
    }

    /**
     * 使用文件名读指定sheet图片信息，图片数据放在imageDataList中
     */
    @Test
    public void readImageWithSheetNameTest() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);

        EasyExcel.readImage(fileName, "image1", imageDataReadListener);
    }

    /**
     * 使用输入流读指定sheet图片信息，图片数据放在imageDataList中
     */
    @Test
    public void readImageWithSheetNameTest1() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);
        File imageFile = new File(fileName);
        InputStream in = new FileInputStream(imageFile);

        EasyExcel.readImage(in, "image1", imageDataReadListener);
        in.close();
    }

    /**
     * 使用文件名读指定索引的sheet中的图片信息，图片数据放在imageDataList中
     */
    @Test
    public void readImageWithSheetNoTest() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);

        EasyExcel.readImage(fileName, 2, imageDataReadListener);
    }

    /**
     * 使用输入流读指定索引的sheet中的图片信息，图片数据放在imageDataList中
     */
    @Test
    public void readImageWithSheetNoTest1() throws IOException {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "imageRead.xlsx";
        ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
        ImageDataReadListener imageDataReadListener = new ImageDataReadListener(imageDataList);
        File imageFile = new File(fileName);
        InputStream in = new FileInputStream(imageFile);

        EasyExcel.readImage(in, 2, imageDataReadListener);
        in.close();
    }
}
