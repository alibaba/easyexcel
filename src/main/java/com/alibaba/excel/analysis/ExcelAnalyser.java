package com.alibaba.excel.analysis;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {

    void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener eventListener,
              boolean trim);

    void analysis(Sheet sheetParam);



    void analysis();


    List<Sheet> getSheets();



}
