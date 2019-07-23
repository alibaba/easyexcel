package com.alibaba.excel.metadata.holder.read;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.holder.write.WriteConfiguration;
import com.alibaba.excel.metadata.property.CellStyleProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.metadata.property.RowHeightProperty;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.listener.ReadListenerRegistryCenter;
import com.alibaba.excel.read.listener.event.AnalysisFinishEvent;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.AbstractColumnCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public abstract class AbstractWriteConfiguration
    implements WriteConfiguration, ReadConfiguration, ReadListenerRegistryCenter {
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Write handler for workbook
     */
    private Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap;
    /**
     * Read listener
     */
    private List<ReadListener> readListenerList;
    /**
     * Converter for workbook
     */
    private Map<ConverterKey, Converter> readConverterMap;
    /**
     * Converter for workbook
     */
    private Map<Class, Converter> writeConverterMap;
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
     * Count the number of added heads when read sheet.
     *
     * <li>0 - This Sheet has no head ,since the first row are the data
     * <li>1 - This Sheet has one row head , this is the default
     * <li>2 - This Sheet has two row head ,since the third row is the data
     */
    private Integer readHeadRowNumber;
    /**
     * You can only choose one of the {@link AbstractWriteConfiguration#head} and
     * {@link AbstractWriteConfiguration#clazz}
     */
    private List<List<String>> head;
    /**
     * You can only choose one of the {@link AbstractWriteConfiguration#head} and
     * {@link AbstractWriteConfiguration#clazz}
     */
    private Class clazz;
    /**
     * Automatic trim includes sheet name and content
     */
    private Boolean autoTrim;

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

    public Boolean getAutoTrim() {
        return autoTrim;
    }

    public void setAutoTrim(Boolean autoTrim) {
        this.autoTrim = autoTrim;
    }

    public List<ReadListener> getReadListenerList() {
        return readListenerList;
    }

    public void setReadListenerList(List<ReadListener> readListenerList) {
        this.readListenerList = readListenerList;
    }

    public Map<ConverterKey, Converter> getReadConverterMap() {
        return readConverterMap;
    }

    public void setReadConverterMap(Map<ConverterKey, Converter> readConverterMap) {
        this.readConverterMap = readConverterMap;
    }

    public Map<Class, Converter> getWriteConverterMap() {
        return writeConverterMap;
    }

    public void setWriteConverterMap(Map<Class, Converter> writeConverterMap) {
        this.writeConverterMap = writeConverterMap;
    }

    public Integer getReadHeadRowNumber() {
        return readHeadRowNumber;
    }

    public void setReadHeadRowNumber(Integer readHeadRowNumber) {
        this.readHeadRowNumber = readHeadRowNumber;
    }

    @Override
    public void register(AnalysisEventListener listener) {
        readListenerList.add(listener);
    }

    @Override
    public void notifyEndOneRow(AnalysisFinishEvent event, AnalysisContext analysisContext) {
        List<CellData> cellDataList = (List<CellData>)event.getAnalysisResult();
        if (analysisContext.currentRowNum() > analysisContext.currentSheetHolder().getReadHeadRowNumber()) {
            for (ReadListener readListener : readListenerList) {
                try {
                    readListener.invoke(analysisContext.currentRowAnalysisResult(), analysisContext);
                } catch (Exception e) {
                    for (ReadListener readListenerException : readListenerList) {
                        try {
                            readListenerException.onException(e, analysisContext);
                        } catch (Exception exception) {
                            throw new ExcelAnalysisException("Listen error!", exception);
                        }
                    }
                }
            }
        }
        // Now is header
        if (analysisContext.currentRowNum().equals(analysisContext.currentSheetHolder().getReadHeadRowNumber())) {
            buildHead(analysisContext, cellDataList);
        }
    }

    @Override
    public void notifyAfterAllAnalysed(AnalysisContext analysisContext) {
        for (ReadListener readListener : readListenerList) {
            readListener.doAfterAllAnalysed(analysisContext);
        }
    }

    private void buildHead(AnalysisContext analysisContext, List<CellData> cellDataList) {
        if (!HeadKindEnum.CLASS.equals(analysisContext.currentConfiguration().excelHeadProperty().getHeadKind())) {
            return;
        }
        List<String> dataList = (List<String>)buildStringList(cellDataList, analysisContext.currentConfiguration());
        ExcelHeadProperty excelHeadPropertyData = analysisContext.currentConfiguration().excelHeadProperty();
        Map<Integer, Head> headMapData = excelHeadPropertyData.getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMapData = excelHeadPropertyData.getContentPropertyMap();
        Map<Integer, Head> tmpHeadMap = new HashMap<Integer, Head>(headMapData.size() * 4 / 3 + 1);
        Map<Integer, ExcelContentProperty> tmpContentPropertyMap =
            new HashMap<Integer, ExcelContentProperty>(contentPropertyMapData.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, Head> entry : headMapData.entrySet()) {
            Head headData = entry.getValue();
            if (headData.getForceIndex()) {
                tmpHeadMap.put(entry.getKey(), headData);
                tmpContentPropertyMap.put(entry.getKey(), contentPropertyMapData.get(entry.getKey()));
                continue;
            }
            String headName = headData.getHeadNameList().get(0);
            for (int i = 0; i < dataList.size(); i++) {
                String headString = dataList.get(i);
                if (StringUtils.isEmpty(headString)) {
                    continue;
                }
                if (analysisContext.currentSheetHolder().getAutoTrim()) {
                    headString = headString.trim();
                }
                if (headName.equals(headString)) {
                    headData.setColumnIndex(i);
                    tmpHeadMap.put(i, headData);
                    tmpContentPropertyMap.put(i, contentPropertyMapData.get(entry.getKey()));
                    break;
                }
            }
        }
        excelHeadPropertyData.setHeadMap(tmpHeadMap);
        excelHeadPropertyData.setContentPropertyMap(tmpContentPropertyMap);
    }

    private Object buildStringList(List<CellData> data, ReadConfiguration readConfiguration) {
        List<String> list = new ArrayList<String>();
        for (CellData cellData : data) {
            Converter converter = readConfiguration.readConverterMap()
                .get(ConverterKey.buildConverterKey(String.class, cellData.getType()));
            if (converter == null) {
                throw new ExcelDataConvertException(
                    "Converter not found, convert " + cellData.getType() + " to String");
            }
            try {
                list.add((String)(converter.convertToJavaData(cellData, null)));
            } catch (Exception e) {
                throw new ExcelDataConvertException("Convert data " + cellData + " to String error ", e);
            }
        }
        return list;
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

    protected void initAnnotationConfig(List<WriteHandler> handlerList) {
        if (!HeadKindEnum.CLASS.equals(getExcelHeadProperty().getHeadKind())) {
            return;
        }
        Map<Integer, Head> headMap = getExcelHeadProperty().getHeadMap();
        Map<Integer, ExcelContentProperty> contentPropertyMap = getExcelHeadProperty().getContentPropertyMap();

        boolean hasCellStyle = false;
        boolean hasColumnWidth = false;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (entry.getValue().getCellStyleProperty() != null) {
                hasCellStyle = true;
            }
            if (entry.getValue().getColumnWidthProperty() != null) {
                hasColumnWidth = true;
            }
            ExcelContentProperty excelContentProperty = contentPropertyMap.get(entry.getKey());
            if (excelContentProperty.getCellStyleProperty() != null) {
                hasCellStyle = true;
            }
        }

        if (hasCellStyle) {
            dealCellStyle(handlerList, contentPropertyMap);
        }
        if (hasColumnWidth) {
            dealColumnWidth(handlerList);
        }
        dealRowHigh(handlerList, contentPropertyMap);
    }

    private void dealRowHigh(List<WriteHandler> handlerList, Map<Integer, ExcelContentProperty> contentPropertyMap) {
        RowHeightProperty headRowHeightProperty = excelHeadProperty.getHeadRowHeightProperty();
        RowHeightProperty contentRowHeightProperty = excelHeadProperty.getContentRowHeightProperty();
        if (headRowHeightProperty == null && contentRowHeightProperty == null) {
            return;
        }
        Short headRowHeight = null;
        if (headRowHeightProperty != null) {
            headRowHeight = headRowHeightProperty.getHeight();
        }
        Short contentRowHeight = null;
        if (contentRowHeightProperty != null) {
            contentRowHeight = contentRowHeightProperty.getHeight();
        }
        handlerList.add(new SimpleRowHeightStyleStrategy(headRowHeight, contentRowHeight));
    }

    private void dealColumnWidth(List<WriteHandler> handlerList) {
        WriteHandler columnWidthStyleStrategy = new AbstractColumnWidthStyleStrategy() {
            @Override
            protected void setColumnWidth(Sheet sheet, Cell cell, Head head) {
                if (head == null) {
                    return;
                }
                if (head.getColumnWidthProperty() != null) {
                    sheet.setColumnWidth(head.getColumnIndex(), head.getColumnWidthProperty().getWidth());
                }
            }
        };
        handlerList.add(columnWidthStyleStrategy);
    }

    private void dealCellStyle(List<WriteHandler> handlerList,
        final Map<Integer, ExcelContentProperty> contentPropertyMap) {
        WriteHandler columnCellStyleStrategy = new AbstractColumnCellStyleStrategy() {
            @Override
            protected CellStyle headCellStyle(Head head) {
                if (head == null || head.getCellStyleProperty() == null) {
                    return null;
                }
                CellStyleProperty cellStyleProperty = head.getCellStyleProperty();
                return new CellStyle(cellStyleProperty.getFontName(), cellStyleProperty.getFontHeightInPoints(),
                    cellStyleProperty.getBold(), cellStyleProperty.getIndexedColors());
            }

            @Override
            protected CellStyle contentCellStyle(Head head) {
                if (head == null) {
                    return null;
                }
                ExcelContentProperty excelContentProperty = contentPropertyMap.get(head.getColumnIndex());
                if (excelContentProperty == null || excelContentProperty.getCellStyleProperty() == null) {
                    return null;
                }
                CellStyleProperty cellStyleProperty = excelContentProperty.getCellStyleProperty();
                return new CellStyle(cellStyleProperty.getFontName(), cellStyleProperty.getFontHeightInPoints(),
                    cellStyleProperty.getBold(), cellStyleProperty.getIndexedColors());
            }
        };
        handlerList.add(columnCellStyleStrategy);
    }

    @Override
    public Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap() {
        return getWriteHandlerMap();
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

    @Override
    public List<ReadListener> readListenerList() {
        return getReadListenerList();
    }

    @Override
    public Map<ConverterKey, Converter> readConverterMap() {
        return getReadConverterMap();
    }

    @Override
    public Map<Class, Converter> writeConverterMap() {
        return getWriteConverterMap();
    }
}
