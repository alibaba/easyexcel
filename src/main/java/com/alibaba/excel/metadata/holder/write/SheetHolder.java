package com.alibaba.excel.metadata.holder.write;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class SheetHolder extends AbstractWriteConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(SheetHolder.class);
    /***
     * poi sheet
     */
    private Sheet sheet;
    /***
     * sheetNo
     */
    private Integer sheetNo;
    /***
     * sheetName
     */
    private String sheetName;
    /***
     * poi sheet
     */
    private WorkbookHolder parentWorkBook;
    /***
     * has been initialized table
     */
    private Map<Integer, TableHolder> hasBeenInitializedTable;
    /**
     * current param
     */
    private com.alibaba.excel.metadata.Sheet sheetParam;

    public static SheetHolder buildWriteWorkSheetHolder(com.alibaba.excel.metadata.Sheet sheet,
                                                        WorkbookHolder workbookHolder) {
        SheetHolder sheetHolder = buildBaseSheetHolder(sheet, workbookHolder);

        sheetHolder.setNewInitialization(Boolean.TRUE);
        if (sheet.getNeedHead() == null) {
            sheetHolder.setNeedHead(workbookHolder.needHead());
        } else {
            sheetHolder.setNeedHead(sheet.getNeedHead());
        }
        if (sheet.getWriteRelativeHeadRowIndex() == null) {
            sheetHolder.setWriteRelativeHeadRowIndex(workbookHolder.writeRelativeHeadRowIndex());
        } else {
            sheetHolder.setWriteRelativeHeadRowIndex(sheet.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(sheet);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (sheet.getCustomWriteHandlerList() != null && !sheet.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(sheet.getCustomWriteHandlerList());
        }
        // Initialization Annotation
        sheetHolder.initAnnotationConfig(handlerList);

        sheetHolder
            .setWriteHandlerMap(sheetHolder.sortAndClearUpHandler(handlerList, workbookHolder.getWriteHandlerMap()));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(workbookHolder.getWriteConverterMap());
        if (sheet.getCustomConverterList() != null && !sheet.getCustomConverterList().isEmpty()) {
            for (Converter converter : sheet.getCustomConverterList()) {
                converterMap.put(converter.getClass(), converter);
            }
        }
        sheetHolder.setWriteConverterMap(converterMap);
        sheetHolder.setHasBeenInitializedTable(new HashMap<Integer, TableHolder>());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Sheet writeHandlerMap:{}", sheetHolder.getWriteHandlerMap());
        }
        return sheetHolder;
    }

    public static SheetHolder buildReadWorkSheetHolder(com.alibaba.excel.metadata.Sheet sheet,
                                                       WorkbookHolder workbookHolder) {
        SheetHolder sheetHolder = buildBaseSheetHolder(sheet, workbookHolder);
        if (sheet.getReadHeadRowNumber() == null) {
            if (workbookHolder.getReadHeadRowNumber() == null) {
                sheetHolder.setReadHeadRowNumber(sheetHolder.getExcelHeadProperty().getHeadRowNumber());
            } else {
                sheetHolder.setReadHeadRowNumber(workbookHolder.getReadHeadRowNumber());
            }
        } else {
            sheetHolder.setReadHeadRowNumber(sheet.getReadHeadRowNumber());
        }

        Map<ConverterKey, Converter> converterMap =
            new HashMap<ConverterKey, Converter>(workbookHolder.getReadConverterMap());
        if (sheet.getCustomConverterList() != null && !sheet.getCustomConverterList().isEmpty()) {
            for (Converter converter : sheet.getCustomConverterList()) {
                converterMap.put(ConverterKey.buildConverterKey(converter), converter);
            }
        }
        sheetHolder.setReadConverterMap(converterMap);

        List<ReadListener> readListenerList = new ArrayList<ReadListener>();
        if (sheet.getCustomReadListenerList() != null && !sheet.getCustomReadListenerList().isEmpty()) {
            readListenerList.addAll(sheet.getCustomReadListenerList());
        }
        sheetHolder.setReadListenerList(readListenerList);
        return sheetHolder;
    }

    private static SheetHolder buildBaseSheetHolder(com.alibaba.excel.metadata.Sheet sheet,
                                                    WorkbookHolder workbookHolder) {
        SheetHolder sheetHolder = new SheetHolder();
        sheetHolder.setSheetParam(sheet);
        sheetHolder.setParentWorkBook(workbookHolder);

        boolean noHead = (sheet.getHead() == null || sheet.getHead().isEmpty()) && sheet.getClazz() == null;
        if (noHead) {
            // Use parent
            sheetHolder.setHead(workbookHolder.getHead());
            sheetHolder.setClazz(workbookHolder.getClazz());
        } else {
            sheetHolder.setHead(sheet.getHead());
            sheetHolder.setClazz(sheet.getClazz());
        }

        if (sheet.getAutoTrim() == null) {
            workbookHolder.setAutoTrim(workbookHolder.getAutoTrim());
        } else {
            workbookHolder.setAutoTrim(sheet.getNeedHead());
        }
        // Initialization property
        sheetHolder
            .setExcelHeadProperty(new ExcelHeadProperty(sheetHolder.getClazz(), sheetHolder.getHead(), workbookHolder));
        return sheetHolder;
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private static void compatibleOldCode(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet.getColumnWidthMap() != null && !sheet.getColumnWidthMap().isEmpty()) {
            final Map<Integer, Integer> columnWidthMap = sheet.getColumnWidthMap();
            if (sheet.getCustomWriteHandlerList() == null) {
                sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            sheet.getCustomWriteHandlerList().add(new AbstractHeadColumnWidthStyleStrategy() {
                @Override
                protected Integer columnWidth(Head head) {
                    if (columnWidthMap.containsKey(head.getColumnIndex())) {
                        columnWidthMap.get(head.getColumnIndex());
                    }
                    return 20;
                }
            });
        }
        if (sheet.getTableStyle() != null) {
            final TableStyle tableStyle = sheet.getTableStyle();
            if (sheet.getCustomWriteHandlerList() == null) {
                sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            CellStyle headCellStyle = new CellStyle();
            headCellStyle.setFont(tableStyle.getTableHeadFont());
            headCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            CellStyle contentCellStyle = new CellStyle();
            contentCellStyle.setFont(tableStyle.getTableContentFont());
            contentCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            sheet.getCustomWriteHandlerList().add(new RowCellStyleStrategy(headCellStyle, contentCellStyle));
        }
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Integer getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public WorkbookHolder getParentWorkBook() {
        return parentWorkBook;
    }

    public void setParentWorkBook(WorkbookHolder parentWorkBook) {
        this.parentWorkBook = parentWorkBook;
    }

    public Map<Integer, TableHolder> getHasBeenInitializedTable() {
        return hasBeenInitializedTable;
    }

    public void setHasBeenInitializedTable(Map<Integer, TableHolder> hasBeenInitializedTable) {
        this.hasBeenInitializedTable = hasBeenInitializedTable;
    }

    public com.alibaba.excel.metadata.Sheet getSheetParam() {
        return sheetParam;
    }

    public void setSheetParam(com.alibaba.excel.metadata.Sheet sheetParam) {
        this.sheetParam = sheetParam;
    }
}
