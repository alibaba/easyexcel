package com.alibaba.excel.read;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.read.event.AnalysisEventListener;
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
     * @param trim 是否去空格
     */
    void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom, AnalysisEventListener eventListener,
              boolean trim);

    /**
     * 解析指定sheet,{@link AnalysisEventListener}监听中使用
     *
     * @param sheetParam 入参
     */
    void analysis(Sheet sheetParam);


    /**
     *
     * 默认解析第一个sheet，解析结果在 {@link AnalysisEventListener}监听中使用
     */
    void analysis();

    /**
     * 返回excel中包含哪些sheet
     *
     * @return 返回所有sheet
     */
    List<Sheet> getSheets();

    /**
     * 关闭流
     */
    void stop();
}
