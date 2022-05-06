package com.alibaba.easyexcel.test.temp.bug;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author jiaosong
 * @desc
 * @date 2020/4/6
 */
public class ExcelCreat {


    public static void main(String[] args) throws FileNotFoundException {
        List<DataType> data = getData();
        ExcelWriter excelWriter = null;
        excelWriter = EasyExcel.write(new FileOutputStream("all.xlsx")).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(1, "test")
            .head(HeadType.class)
            .build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
    }

    private static List<DataType> getData() {
        DataType vo = new DataType();
        vo.setId(738);
        vo.setFirstRemark("1222");
        vo.setSecRemark("22222");
        return Collections.singletonList(vo);
    }
}
