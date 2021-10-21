package com.alibaba.excel.write.metadata.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.metadata.AbstractHolder;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.LoopMergeProperty;
import com.alibaba.excel.metadata.property.OnceAbsoluteMergeProperty;
import com.alibaba.excel.metadata.property.RowHeightProperty;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.DefaultWriteHandlerLoader;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteBasicParameter;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Write holder configuration
 *
 * @author Jiaju Zhuang
 */
@Data
@NoArgsConstructor
public abstract class AbstractWriteHolder extends AbstractHolder implements WriteHolder {
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    private Integer relativeHeadRowIndex;
    /**
     * Excel head property
     */
    private ExcelWriteHeadProperty excelWriteHeadProperty;
    /**
     * Write handler
     */
    private Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap;
    /**
     * Own write handler.Created in the sheet in the workbook interceptors will not be executed because the workbook to
     * create an event long past. So when initializing sheet, supplementary workbook event.
     */
    private Map<Class<? extends WriteHandler>, List<WriteHandler>> ownWriteHandlerMap;
    /**
     * Use the default style.Default is true.
     */
    private Boolean useDefaultStyle;
    /**
     * Whether to automatically merge headers.Default is true.
     */
    private Boolean automaticMergeHead;

    /**
     * Ignore the custom columns.
     */
    private Collection<Integer> excludeColumnIndexes;
    /**
     * Ignore the custom columns.
     */
    private Collection<String> excludeColumnFieldNames;
    /**
     * Only output the custom columns.
     */
    private Collection<Integer> includeColumnIndexes;
    /**
     * Only output the custom columns.
     */
    private Collection<String> includeColumnFieldNames;

    public AbstractWriteHolder(WriteBasicParameter writeBasicParameter, AbstractWriteHolder parentAbstractWriteHolder) {
        super(writeBasicParameter, parentAbstractWriteHolder);

        if (writeBasicParameter.getUseScientificFormat() != null) {
            throw new UnsupportedOperationException("Currently does not support setting useScientificFormat.");
        }

        if (writeBasicParameter.getNeedHead() == null) {
            if (parentAbstractWriteHolder == null) {
                this.needHead = Boolean.TRUE;
            } else {
                this.needHead = parentAbstractWriteHolder.getNeedHead();
            }
        } else {
            this.needHead = writeBasicParameter.getNeedHead();
        }

        if (writeBasicParameter.getRelativeHeadRowIndex() == null) {
            if (parentAbstractWriteHolder == null) {
                this.relativeHeadRowIndex = 0;
            } else {
                this.relativeHeadRowIndex = parentAbstractWriteHolder.getRelativeHeadRowIndex();
            }
        } else {
            this.relativeHeadRowIndex = writeBasicParameter.getRelativeHeadRowIndex();
        }

        if (writeBasicParameter.getUseDefaultStyle() == null) {
            if (parentAbstractWriteHolder == null) {
                this.useDefaultStyle = Boolean.TRUE;
            } else {
                this.useDefaultStyle = parentAbstractWriteHolder.getUseDefaultStyle();
            }
        } else {
            this.useDefaultStyle = writeBasicParameter.getUseDefaultStyle();
        }

        if (writeBasicParameter.getAutomaticMergeHead() == null) {
            if (parentAbstractWriteHolder == null) {
                this.automaticMergeHead = Boolean.TRUE;
            } else {
                this.automaticMergeHead = parentAbstractWriteHolder.getAutomaticMergeHead();
            }
        } else {
            this.automaticMergeHead = writeBasicParameter.getAutomaticMergeHead();
        }

        if (writeBasicParameter.getExcludeColumnFieldNames() == null && parentAbstractWriteHolder != null) {
            this.excludeColumnFieldNames = parentAbstractWriteHolder.getExcludeColumnFieldNames();
        } else {
            this.excludeColumnFieldNames = writeBasicParameter.getExcludeColumnFieldNames();
        }
        if (writeBasicParameter.getExcludeColumnIndexes() == null && parentAbstractWriteHolder != null) {
            this.excludeColumnIndexes = parentAbstractWriteHolder.getExcludeColumnIndexes();
        } else {
            this.excludeColumnIndexes = writeBasicParameter.getExcludeColumnIndexes();
        }
        if (writeBasicParameter.getIncludeColumnFieldNames() == null && parentAbstractWriteHolder != null) {
            this.includeColumnFieldNames = parentAbstractWriteHolder.getIncludeColumnFieldNames();
        } else {
            this.includeColumnFieldNames = writeBasicParameter.getIncludeColumnFieldNames();
        }
        if (writeBasicParameter.getIncludeColumnIndexes() == null && parentAbstractWriteHolder != null) {
            this.includeColumnIndexes = parentAbstractWriteHolder.getIncludeColumnIndexes();
        } else {
            this.includeColumnIndexes = writeBasicParameter.getIncludeColumnIndexes();
        }

        // Initialization property
        this.excelWriteHeadProperty = new ExcelWriteHeadProperty(this, getClazz(), getHead());

        // Set writeHandlerMap
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();

        // Initialization Annotation
        initAnnotationConfig(handlerList, writeBasicParameter);

        if (writeBasicParameter.getCustomWriteHandlerList() != null
            && !writeBasicParameter.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(writeBasicParameter.getCustomWriteHandlerList());
        }

        this.ownWriteHandlerMap = sortAndClearUpHandler(handlerList);

        Map<Class<? extends WriteHandler>, List<WriteHandler>> parentWriteHandlerMap = null;
        if (parentAbstractWriteHolder != null) {
            parentWriteHandlerMap = parentAbstractWriteHolder.getWriteHandlerMap();
        } else {
            handlerList.addAll(DefaultWriteHandlerLoader.loadDefaultHandler(useDefaultStyle));
        }
        this.writeHandlerMap = sortAndClearUpAllHandler(handlerList, parentWriteHandlerMap);

        // Set converterMap
        if (parentAbstractWriteHolder == null) {
            setConverterMap(DefaultConverterLoader.loadDefaultWriteConverter());
        } else {
            setConverterMap(new HashMap<>(parentAbstractWriteHolder.getConverterMap()));
        }
        if (writeBasicParameter.getCustomConverterList() != null
            && !writeBasicParameter.getCustomConverterList().isEmpty()) {
            for (Converter<?> converter : writeBasicParameter.getCustomConverterList()) {
                getConverterMap().put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
            }
        }
    }

    protected void initAnnotationConfig(List<WriteHandler> handlerList, WriteBasicParameter writeBasicParameter) {
        if (!HeadKindEnum.CLASS.equals(getExcelWriteHeadProperty().getHeadKind())) {
            return;
        }
        if (writeBasicParameter.getClazz() == null) {
            return;
        }
        Map<Integer, Head> headMap = getExcelWriteHeadProperty().getHeadMap();
        boolean hasColumnWidth = false;
        boolean hasStyle = false;

        for (Head head : headMap.values()) {
            if (head.getColumnWidthProperty() != null) {
                hasColumnWidth = true;
            }
            if (head.getHeadStyleProperty() != null || head.getHeadFontProperty() != null) {
                hasStyle = true;
            }
            dealLoopMerge(handlerList, head);
        }

        if (hasColumnWidth) {
            dealColumnWidth(handlerList);
        }

        //if (hasStyle) {
            dealStyle(handlerList);
        //}

        dealRowHigh(handlerList);
        dealOnceAbsoluteMerge(handlerList);
    }

    private void dealStyle(List<WriteHandler> handlerList) {
        WriteHandler styleStrategy = new AbstractVerticalCellStyleStrategy() {
            @Override
            public int order() {
                return OrderConstant.ANNOTATION_DEFINE_STYLE;
            }

            @Override
            protected WriteCellStyle headCellStyle(CellWriteHandlerContext context) {
                ExcelContentProperty excelContentProperty = context.getExcelContentProperty();
                return WriteCellStyle.build(excelContentProperty.getContentStyleProperty(),
                    excelContentProperty.getContentFontProperty());
            }

            @Override
            protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
                ExcelContentProperty excelContentProperty = context.getExcelContentProperty();
                return WriteCellStyle.build(excelContentProperty.getContentStyleProperty(),
                    excelContentProperty.getContentFontProperty());
            }
        };
        handlerList.add(styleStrategy);
    }

    private void dealLoopMerge(List<WriteHandler> handlerList, Head head) {
        LoopMergeProperty loopMergeProperty = head.getLoopMergeProperty();
        if (loopMergeProperty == null) {
            return;
        }
        handlerList.add(new LoopMergeStrategy(loopMergeProperty, head.getColumnIndex()));
    }

    private void dealOnceAbsoluteMerge(List<WriteHandler> handlerList) {
        OnceAbsoluteMergeProperty onceAbsoluteMergeProperty =
            getExcelWriteHeadProperty().getOnceAbsoluteMergeProperty();
        if (onceAbsoluteMergeProperty == null) {
            return;
        }
        handlerList.add(new OnceAbsoluteMergeStrategy(onceAbsoluteMergeProperty));
    }

    private void dealRowHigh(List<WriteHandler> handlerList) {
        RowHeightProperty headRowHeightProperty = getExcelWriteHeadProperty().getHeadRowHeightProperty();
        RowHeightProperty contentRowHeightProperty = getExcelWriteHeadProperty().getContentRowHeightProperty();
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
        WriteHandler columnWidthStyleStrategy = new AbstractHeadColumnWidthStyleStrategy() {
            @Override
            protected Integer columnWidth(Head head, Integer columnIndex) {
                if (head == null) {
                    return null;
                }
                if (head.getColumnWidthProperty() != null) {
                    return head.getColumnWidthProperty().getWidth();
                }
                return null;
            }
        };
        handlerList.add(columnWidthStyleStrategy);
    }

    protected Map<Class<? extends WriteHandler>, List<WriteHandler>> sortAndClearUpAllHandler(
        List<WriteHandler> handlerList, Map<Class<? extends WriteHandler>, List<WriteHandler>> parentHandlerMap) {
        // add
        if (parentHandlerMap != null) {
            List<WriteHandler> parentWriteHandler = parentHandlerMap.get(WriteHandler.class);
            if (!CollectionUtils.isEmpty(parentWriteHandler)) {
                handlerList.addAll(parentWriteHandler);
            }
        }
        return sortAndClearUpHandler(handlerList);
    }

    protected Map<Class<? extends WriteHandler>, List<WriteHandler>> sortAndClearUpHandler(
        List<WriteHandler> handlerList) {
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
            new HashMap<Class<? extends WriteHandler>, List<WriteHandler>>(16);
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
            if (writeHandler instanceof WorkbookWriteHandler) {
                result.get(WorkbookWriteHandler.class).add(writeHandler);
            }
            result.get(WriteHandler.class).add(writeHandler);
        }
        return result;
    }

    @Override
    public boolean ignore(String fieldName, Integer columnIndex) {
        if (fieldName != null) {
            if (includeColumnFieldNames != null && !includeColumnFieldNames.contains(fieldName)) {
                return true;
            }
            if (excludeColumnFieldNames != null && excludeColumnFieldNames.contains(fieldName)) {
                return true;
            }
        }
        if (columnIndex != null) {
            if (includeColumnIndexes != null && !includeColumnIndexes.contains(columnIndex)) {
                return true;
            }
            if (excludeColumnIndexes != null && excludeColumnIndexes.contains(columnIndex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ExcelWriteHeadProperty excelWriteHeadProperty() {
        return getExcelWriteHeadProperty();
    }

    @Override
    public Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap() {
        return getWriteHandlerMap();
    }

    @Override
    public Map<Class<? extends WriteHandler>, List<WriteHandler>> ownWriteHandlerMap() {
        return getOwnWriteHandlerMap();
    }

    @Override
    public boolean needHead() {
        return getNeedHead();
    }

    @Override
    public int relativeHeadRowIndex() {
        return getRelativeHeadRowIndex();
    }

    @Override
    public boolean automaticMergeHead() {
        return getAutomaticMergeHead();
    }
}
