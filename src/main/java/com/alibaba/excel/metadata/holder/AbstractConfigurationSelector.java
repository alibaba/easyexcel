package com.alibaba.excel.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public abstract class AbstractConfigurationSelector implements ConfigurationSelector {
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Write handler for workbook
     */
    private Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap;
    /**
     * Converter for workbook
     */
    private Map<Class, Converter> converterMap;
    /**
     * Excel head property
     */
    private ExcelHeadProperty excelHeadProperty;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer writeRelativeHeadRowIndex;
    /**
     * Record whether it's new or from cache
     */
    private Boolean newInitialization;

    /**
     * You can only choose one of the {@link AbstractConfigurationSelector#head} and
     * {@link AbstractConfigurationSelector#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link AbstractConfigurationSelector#head} and
     * {@link AbstractConfigurationSelector#clazz}
     */
    private Class clazz;

    public Boolean getNeedHead() {
        return needHead;
    }

    public void setNeedHead(Boolean needHead) {
        this.needHead = needHead;
    }

    public Map<Class<? extends WriteHandler>, List<WriteHandler>> getWriteHandlerMap() {
        return writeHandlerMap;
    }

    public void setWriteHandlerMap(Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap) {
        this.writeHandlerMap = writeHandlerMap;
    }

    public Map<Class, Converter> getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map<Class, Converter> converterMap) {
        this.converterMap = converterMap;
    }

    public ExcelHeadProperty getExcelHeadProperty() {
        return excelHeadProperty;
    }

    public void setExcelHeadProperty(ExcelHeadProperty excelHeadProperty) {
        this.excelHeadProperty = excelHeadProperty;
    }

    public Integer getWriteRelativeHeadRowIndex() {
        return writeRelativeHeadRowIndex;
    }

    public void setWriteRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        this.writeRelativeHeadRowIndex = writeRelativeHeadRowIndex;
    }

    public Boolean getNewInitialization() {
        return newInitialization;
    }

    public void setNewInitialization(Boolean newInitialization) {
        this.newInitialization = newInitialization;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    protected Map<Class<? extends WriteHandler>, List<WriteHandler>> sortAndClearUpHandler(
        List<WriteHandler> handlerList, Map<Class<? extends WriteHandler>, List<WriteHandler>> parentHandlerMap) {
        // add
        if (parentHandlerMap != null) {
            for (List<WriteHandler> parentHandlerList : parentHandlerMap.values()) {
                handlerList.addAll(parentHandlerList);
            }
        }
        // sort
        Map<Integer, List<WriteHandler>> orderExcelWriteHandlerMap = new TreeMap<Integer, List<WriteHandler>>();
        for (WriteHandler handler : handlerList) {
            int order = Integer.MIN_VALUE;
            if (handler instanceof Order) {
                order = ((Order)handler).order();
            }
            if (orderExcelWriteHandlerMap.containsKey(order)) {
                orderExcelWriteHandlerMap.get(order).add(handler);
            } else {
                List<WriteHandler> tempHandlerList = new ArrayList<WriteHandler>();
                tempHandlerList.add(handler);
                orderExcelWriteHandlerMap.put(order, tempHandlerList);
            }
        }
        // clean up
        Set<String> alreadyExistedHandlerSet = new HashSet<String>();
        List<WriteHandler> cleanUpHandlerList = new ArrayList<WriteHandler>();
        for (Map.Entry<Integer, List<WriteHandler>> entry : orderExcelWriteHandlerMap.entrySet()) {
            for (WriteHandler handler : entry.getValue()) {
                if (handler instanceof NotRepeatExecutor) {
                    String uniqueValue = ((NotRepeatExecutor)handler).uniqueValue();
                    if (alreadyExistedHandlerSet.contains(uniqueValue)) {
                        continue;
                    }
                    alreadyExistedHandlerSet.add(uniqueValue);
                }
                cleanUpHandlerList.add(handler);
            }
        }
        // classify
        Map<Class<? extends WriteHandler>, List<WriteHandler>> result =
            new HashMap<Class<? extends WriteHandler>, List<WriteHandler>>();
        result.put(WriteHandler.class, new ArrayList<WriteHandler>());
        result.put(WorkbookWriteHandler.class, new ArrayList<WriteHandler>());
        result.put(SheetWriteHandler.class, new ArrayList<WriteHandler>());
        result.put(RowWriteHandler.class, new ArrayList<WriteHandler>());
        result.put(CellWriteHandler.class, new ArrayList<WriteHandler>());
        for (WriteHandler writeHandler : cleanUpHandlerList) {
            if (writeHandler instanceof CellWriteHandler) {
                result.get(CellWriteHandler.class).add(writeHandler);
            }
            if (writeHandler instanceof RowWriteHandler) {
                result.get(RowWriteHandler.class).add(writeHandler);
            }
            if (writeHandler instanceof SheetWriteHandler) {
                result.get(SheetWriteHandler.class).add(writeHandler);
            }
            if (writeHandler instanceof SheetWriteHandler) {
                result.get(SheetWriteHandler.class).add(writeHandler);
            }
            if (writeHandler instanceof WorkbookWriteHandler) {
                result.get(WorkbookWriteHandler.class).add(writeHandler);
            }
            result.get(WriteHandler.class).add(writeHandler);
        }
        return result;
    }

    @Override
    public Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap() {
        return getWriteHandlerMap();
    }

    @Override
    public Map<Class, Converter> converterMap() {
        return getConverterMap();
    }

    @Override
    public boolean needHead() {
        return getNeedHead();
    }

    @Override
    public int writeRelativeHeadRowIndex() {
        return getWriteRelativeHeadRowIndex();
    }

    @Override
    public ExcelHeadProperty excelHeadProperty() {
        return getExcelHeadProperty();
    }

    @Override
    public boolean isNew() {
        return getNewInitialization();
    }

}
