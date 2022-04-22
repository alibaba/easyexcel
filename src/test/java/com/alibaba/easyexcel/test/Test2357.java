package com.alibaba.easyexcel.test;

import com.alibaba.easyexcel.test.demo.read.IndexOrNameData;
import com.alibaba.easyexcel.test.demo.read.IndexOrNameDataListener;
import com.alibaba.easyexcel.test.temp.large.LargeData;
import com.alibaba.easyexcel.test.temp.large.LargeDataListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Test2357 {
    public static void main(String[] args) throws FileNotFoundException {

        EasyExcel.read(new FileInputStream("D:\\SUSTech\\CS304\\easyexcel-master\\easyexcel-master\\src\\test\\resources\\test.csv"), LargeData.class, new LargeDataListener(),ExcelTypeEnum.CSV)
            .headRowNumber(2).sheet().doRead();
    }

}
