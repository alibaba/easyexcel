package com.alibaba.easyexcel.test.core.enumformat;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiajiafu
 * @since 2022/7/14
 */
public class EnumFormatAnnotationTest {

    private static File myFile;

    @BeforeClass
    public static void init() {
        myFile = TestFileUtil.createNewFile("enumFormatAnnotation.xlsx");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        EasyExcel.write().file(myFile).head(EnumValueData.class).sheet().doWrite(data());
    }

    private List<EnumValueData> data() {
        List<EnumValueData> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            EnumValueData data = new EnumValueData();
            data.setName("人员" + i);
            data.setOuType(i % 2 + 1);
            if (i % 2 == 0) {
                data.setSexType("male");
            } else {
                data.setSexType("female");
            }
            list.add(data);
        }
        return list;
    }

}
