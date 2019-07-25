package com.alibaba.easyexcel.test.core.nohead;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.easyexcel.test.core.order.OrderData;

/**
 * Order data test
 * 
 * @author zhuangjiaju
 */
public class NoHeadData07Test {

    @Test
    public void simple() {
        // ExcelWriter writer = EasyExcelFactory.writerBuilder().outputFile(TestFileUtil.createNewWriteFile("order07.xlsx"))
        // .head(OrderData.class).build();
        // Sheet sheet = EasyExcelFactory.writerSheetBuilder().sheetNo(0).sheetName("order").build();
        // writer.write(createData(10000 * 100), sheet);
        // writer.finish();
    }

    private List<OrderData> createData(int count) {
        List<OrderData> list = new ArrayList<OrderData>();
        for (int i = 0; i < count; i++) {
            OrderData orderData = new OrderData();
            orderData.setIndex1("排序1：" + i);
            orderData.setIndex10("排序10:" + i);
            list.add(orderData);
        }
        return list;
    }
}
