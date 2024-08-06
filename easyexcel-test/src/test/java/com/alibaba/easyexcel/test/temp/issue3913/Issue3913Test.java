package com.alibaba.easyexcel.test.temp.issue3913;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Issue3913Test {
    //Issue link: https://github.com/alibaba/easyexcel/issues/3913
    @Test
    public void IssueTest1() {
        String fileName = "d:/out.xlsx";
        // 这里 需要指定写用哪个class去写
        try (com.alibaba.excel.ExcelWriter excelWriter = EasyExcel.write(fileName, CsbDataItemTest.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("output").build();
            excelWriter.write(genData(), writeSheet);
        }
        log.info("write file success");
    }

    private List<CsbDataItemTest> genData() {
        List<CsbDataItemTest> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CsbDataItemTest item = new CsbDataItemTest();
            item.setField1("云订单");
            item.setA("comment");
            item.setField3(i);
            item.setD(i * 20);
            list.add(item);
        }
        log.info("the size : {}", list.size());
        return list;
    }


}
