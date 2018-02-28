package com.alibaba.excel.read.event;

import com.alibaba.excel.read.context.AnalysisContext;

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
     * when read one row trigger invoke function
     *
     * @param object  one row data
     * @param context read context
     */
    public abstract void invoke(T object, AnalysisContext context);

    /**
     * if have something to do after all  read
     *
     * @param context context
     */
    public abstract void doAfterAllAnalysed(AnalysisContext context);
}
