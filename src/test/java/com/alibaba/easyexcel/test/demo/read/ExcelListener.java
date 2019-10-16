package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelExitException;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener<Object> {


    private List<Object> rows = new ArrayList<Object>();

    @Override
    public void invoke(Object data, AnalysisContext context) {
        System.out.println(
            "sheetNo = " + context.readSheetHolder().getSheetNo() + "; sheetName = " + context.readSheetHolder()
                .getSheetName());

        ReadSheetHolder readSheetHolder = context.readSheetHolder();
        // 表头行号
        Integer headRowNumber = readSheetHolder.getHeadRowNumber();

        ReadRowHolder readRowHolder = context.readRowHolder();
        // 当前行索引
        Integer rowIndex = readRowHolder.getRowIndex();

        rows.add(data);
        if(rows.size() > 100){
            throw new ExcelExitException("Take the initiative to stop Analysis");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        doSomething();
        int headRowNumber = context.readSheetHolder().getHeadRowNumber();
        int rowIndex = context.readRowHolder().getRowIndex();
        System.out.println(
            "Analysis Complete： headRowNumber = " + headRowNumber + "; rowIndex = " + rowIndex);
    }


    public void doSomething(){
        for (Object row : rows) {
            System.out.println(row);
        }
    }
}
