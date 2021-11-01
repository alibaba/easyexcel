package com.alibaba.excel.read.listener;

import java.util.List;
import java.util.function.Consumer;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.ListUtils;

/**
 * page read listener
 *
 * @author Jiaju Zhuang
 */
public class PageReadListener<T> implements ReadListener<T> {
    /**
     * Single handle the amount of data
     */
    public static int BATCH_COUNT = 3000;
    /**
     * Temporary storage of data
     */
    private List<T> cachedData = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * consumer
     */
    private final Consumer<List<T>> consumer;

    public PageReadListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        cachedData.add(data);
        if (cachedData.size() >= BATCH_COUNT) {
            consumer.accept(cachedData);
            cachedData = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        consumer.accept(cachedData);
    }

}
