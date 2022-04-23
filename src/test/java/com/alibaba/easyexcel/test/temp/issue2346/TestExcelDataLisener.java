package com.alibaba.easyexcel.test.temp.issue2346;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import org.junit.Test;

import java.util.ArrayList;
@Getter
public class TestExcelDataLisener extends AnalysisEventListener<TestExcel> {

    private ArrayList<TestExcel> allData = new ArrayList<TestExcel>(0);

    @Override
    public void invoke(TestExcel data, AnalysisContext context) {
        // store the data in the listener
        allData.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
