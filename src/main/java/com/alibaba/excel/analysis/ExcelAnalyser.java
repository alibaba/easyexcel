package com.alibaba.excel.analysis;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Excel解析器
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {

    /**
     * Excel解析初始化
     *
     * @param inputStream 解析为文件流
     * @param excelTypeEnum 解析文件类型
     * @param custom 用户自定义参数用户回调时候可以获取到
     * @param eventListener 解析器需要的监听器
     * @param trim
     */
    void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener eventListener,
              boolean trim);

    /**
     * 解析指定sheet,{@link AnalysisEventListener}监听中使用
     *
     * @param sheetParam
     */
    void analysis(Sheet sheetParam);


    /**
     *
     * 默认解析第一个sheet，解析结果以List<String> 的格式在 {@link AnalysisEventListener}监听中使用
     */
    void analysis();

    /**
     * 返回excel中包含哪些sheet
     *
     * @return
     */
    List<Sheet> getSheets();

    /**
     * 关闭流
     */
    void stop();
}
