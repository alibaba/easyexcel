package com.alibaba.easyexcel.test.temp.issue1641;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageDTOListener extends AnalysisEventListener<ImageDTO> {
    private List<ImageDTO> imgExcelDTOS = new ArrayList<>();

    @Override
    public void invoke(ImageDTO imgExcelDTO, AnalysisContext analysisContext) {
        imgExcelDTOS.add(imgExcelDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (ImageDTO a : imgExcelDTOS) {
            System.out.println(a);
        }
    }
}
