package com.alibaba.easyexcel.test.temp.issue_paddle;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class TestExcelDataLisener extends AnalysisEventListener<TestExcel> {

    private ArrayList<TestExcel> allData = new ArrayList<TestExcel>(0);

    @Override
    public void invoke(TestExcel data, AnalysisContext context) {
        // store the data in the listener
        System.out.println("data:" + data);
        allData.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
