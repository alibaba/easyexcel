package com.alibaba.excel.write.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.event.Order;
import com.alibaba.excel.metadata.AbstractHolder;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.RowHeightProperty;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.DefaultWriteHandlerLoader;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteBasicParameter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 * Write holder configuration
 *
 * @author Jiaju Zhuang
 */
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
     * Write handler for workbook
     */
    private Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap;

    public AbstractWriteHolder(WriteBasicParameter writeBasicParameter, AbstractWriteHolder parentAbstractWriteHolder,
        Boolean convertAllFiled) {
        super(writeBasicParameter, parentAbstractWriteHolder);
        if (writeBasicParameter.getUse1904windowing() == null) {
            if (parentAbstractWriteHolder == null) {
                getGlobalConfiguration().setUse1904windowing(Boolean.FALSE);
            } else {
                getGlobalConfiguration()
                    .setUse1904windowing(parentAbstractWriteHolder.getGlobalConfiguration().getUse1904windowing());
            }
        } else {
            getGlobalConfiguration().setUse1904windowing(writeBasicParameter.getUse1904windowing());
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

        // Initialization property
        this.excelWriteHeadProperty = new ExcelWriteHeadProperty(getClazz(), getHead(), convertAllFiled);

        // Compatible with old code
        compatibleOldCode(writeBasicParameter);

        // Set writeHandlerMap
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();

        // Initialization Annotation
        initAnnotationConfig(handlerList);

        if (writeBasicParameter.getCustomWriteHandlerList() != null
            && !writeBasicParameter.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(writeBasicParameter.getCustomWriteHandlerList());
        }

        Map<Class<? extends WriteHandler>, List<WriteHandler>> parentWriteHandlerMap = null;
        if (parentAbstractWriteHolder != null) {
            parentWriteHandlerMap = parentAbstractWriteHolder.getWriteHandlerMap();
        } else {
            handlerList.addAll(DefaultWriteHandlerLoader.loadDefaultHandler());
        }

        this.writeHandlerMap = sortAndClearUpHandler(handlerList, parentWriteHandlerMap);

        // Set converterMap
        if (parentAbstractWriteHolder == null) {
            setConverterMap(DefaultConverterLoader.loadDefaultWriteConverter());
        } else {
            setConverterMap(new HashMap<String, Converter>(parentAbstractWriteHolder.getConverterMap()));
        }
        if (writeBasicParameter.getCustomConverterList() != null
            && !writeBasicParameter.getCustomConverterList().isEmpty()) {
            for (Converter converter : writeBasicParameter.getCustomConverterList()) {
                getConverterMap().put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey()), converter);
            }
        }
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private void compatibleOldCode(WriteBasicParameter writeBasicParameter) {
        switch (holderType()) {
            case SHEET:
                compatibleOldCodeCreateRowCellStyleStrategy(writeBasicParameter,
                    ((WriteSheet)writeBasicParameter).getTableStyle());
                compatibleOldCodeCreateHeadColumnWidthStyleStrategy(writeBasicParameter,
                    ((WriteSheet)writeBasicParameter).getColumnWidthMap());
                return;
            case TABLE:
                compatibleOldCodeCreateRowCellStyleStrategy(writeBasicParameter,
                    ((WriteTable)writeBasicParameter).getTableStyle());
                return;
            default:
        }
    }

    @Deprecated
    private void compatibleOldCodeCreateRowCellStyleStrategy(WriteBasicParameter writeBasicParameter,
        TableStyle tableStyle) {
        if (tableStyle == null) {
            return;
        }
        if (writeBasicParameter.getCustomWriteHandlerList() == null) {
            writeBasicParameter.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        writeBasicParameter.getCustomWriteHandlerList()
            .add(new HorizontalCellStyleStrategy(
                buildWriteCellStyle(tableStyle.getTableHeadFont(), tableStyle.getTableHeadBackGroundColor()),
                buildWriteCellStyle(tableStyle.getTableContentFont(), tableStyle.getTableContentBackGroundColor())));
    }

    @Deprecated
    private WriteCellStyle buildWriteCellStyle(Font font, IndexedColors indexedColors) {
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        if (indexedColors != null) {
            writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            writeCellStyle.setFillForegroundColor(indexedColors.getIndex());
        }
        if (font != null) {
            WriteFont writeFont = new WriteFont();
            writeFont.setFontName(font.getFontName());
            writeFont.setFontHeightInPoints(font.getFontHeightInPoints());
            writeFont.setBold(font.isBold());
            writeCellStyle.setWriteFont(writeFont);
        }
        return writeCellStyle;
    }

    @Deprecated
    private void compatibleOldCodeCreateHeadColumnWidthStyleStrategy(WriteBasicParameter writeBasicParameter,
        final Map<Integer, Integer> columnWidthMap) {
        if (columnWidthMap == null || columnWidthMap.isEmpty()) {
            return;
        }
        if (writeBasicParameter.getCustomWriteHandlerList() == null) {
            writeBasicParameter.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        writeBasicParameter.getCustomWriteHandlerList().add(new AbstractHeadColumnWidthStyleStrategy() {
            @Override
            protected Integer columnWidth(Head head) {
                if (columnWidthMap.containsKey(head.getColumnIndex())) {
                    return columnWidthMap.get(head.getColumnIndex()) / 256;
                }
                return 20;
            }
        });
    }

    protected void initAnnotationConfig(List<WriteHandler> handlerList) {
        if (!HeadKindEnum.CLASS.equals(getExcelWriteHeadProperty().getHeadKind())) {
            return;
        }
        Map<Integer, Head> headMap = getExcelWriteHeadProperty().getHeadMap();

        boolean hasColumnWidth = false;
        for (Map.Entry<Integer, Head> entry : headMap.entrySet()) {
            if (entry.getValue().getColumnWidthProperty() != null) {
                hasColumnWidth = true;
                break;
            }
        }

        if (hasColumnWidth) {
            dealColumnWidth(handlerList);
        }
        dealRowHigh(handlerList);
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
            protected Integer columnWidth(Head head) {
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

    protected Map<Class<? extends WriteHandler>, List<WriteHandler>> sortAndClearUpHandler(
        List<WriteHandler> handlerList, Map<Class<? extends WriteHandler>, List<WriteHandler>> parentHandlerMap) {

        // add
        if (parentHandlerMap != null) {
            List<WriteHandler> parentWriteHandler = parentHandlerMap.get(WriteHandler.class);
            if (!CollectionUtils.isEmpty(parentWriteHandler)) {
                handlerList.addAll(parentWriteHandler);
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

    public ExcelWriteHeadProperty getExcelWriteHeadProperty() {
        return excelWriteHeadProperty;
    }

    public void setExcelWriteHeadProperty(ExcelWriteHeadProperty excelWriteHeadProperty) {
        this.excelWriteHeadProperty = excelWriteHeadProperty;
    }

    public Integer getRelativeHeadRowIndex() {
        return relativeHeadRowIndex;
    }

    public void setRelativeHeadRowIndex(Integer relativeHeadRowIndex) {
        this.relativeHeadRowIndex = relativeHeadRowIndex;
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
    public boolean needHead() {
        return getNeedHead();
    }

    @Override
    public int relativeHeadRowIndex() {
        return getRelativeHeadRowIndex();
    }
}
