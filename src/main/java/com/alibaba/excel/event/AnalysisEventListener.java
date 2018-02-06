package com.alibaba.excel.event;

import com.alibaba.excel.context.AnalysisContext;

/**
 * 监听Excel解析每行数据
 * 不能单列，每次使用new一个
 * 不能单列，每次使用new一个
 * 不能单列，每次使用new一个
 * 重要事情说三遍
 *
 * @author jipengfei
 */
public abstract class AnalysisEventListener<T> {

    /**
     * when analysis one row trigger invoke function
     *
     * @param object  one row data
     * @param context analysis context
     */
    public abstract void invoke(T object, AnalysisContext context);

    /**
     * if have something to do after all  analysis
     *
     * @param context
     */
    public abstract void doAfterAllAnalysed(AnalysisContext context);
}
