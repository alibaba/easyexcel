package com.alibaba.excel.read.listener;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.EasyExcelConsumer;

/**
 * ConsumerEasyExcelDataListener
 * @author gmx@yiynx.cn
 *
 * @param <T> the type of the input to the operation
 * @see <a href="https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/read/DemoDataListener.java">DemoDataListener</a>
 * @since 1.7
 */
public class EasyExcelDataConsumerListener<T> extends AnalysisEventListener<T> {
    private int pageSize = Integer.MAX_VALUE - 1; // default int max
    private List<T> list = new ArrayList<T>();
    private EasyExcelConsumer<List<T>> consumer;
    
    private EasyExcelDataConsumerListener() {
    }
    
    /**
     * ConsumerEasyExcelDataListener
     * @param pageSize
     * @param consumer
     */
    public EasyExcelDataConsumerListener(Integer pageSize, EasyExcelConsumer<List<T>> consumer) {
        this.pageSize = pageSize - 1;
        this.consumer = consumer;
    }
    
    /**
     * ConsumerEasyExcelDataListener
     * @param consumer
     */
    public EasyExcelDataConsumerListener(EasyExcelConsumer<List<T>> consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public void invoke(T data, AnalysisContext context) {
        list.add(data);
        if (list.size() > pageSize) {
            consumer.accept(list);
            list.clear();
        }
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        consumer.accept(list);
        list.clear();
    }

}
