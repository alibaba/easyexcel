package com.alibaba.excel.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class SheetHolder extends AbstractConfigurationSelector {
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

    public SheetHolder(com.alibaba.excel.metadata.Sheet sheet, WorkbookHolder workbookHolder) {
        super();
        this.sheetParam = sheet;
        this.parentWorkBook = workbookHolder;
        boolean noHead = (sheet.getHead() == null || sheet.getHead().isEmpty()) && sheet.getClazz() == null;
        if (noHead) {
            // Use parent
            setHead(workbookHolder.getHead());
            setClazz(workbookHolder.getClazz());
        } else {
            setHead(sheet.getHead());
            setClazz(sheet.getClazz());
        }
        setNewInitialization(Boolean.TRUE);
        if (sheet.getNeedHead() == null) {
            setNeedHead(workbookHolder.needHead());
        } else {
            setNeedHead(sheet.getNeedHead());
        }
        if (sheet.getWriteRelativeHeadRowIndex() == null) {
            setWriteRelativeHeadRowIndex(workbookHolder.writeRelativeHeadRowIndex());
        } else {
            setWriteRelativeHeadRowIndex(sheet.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(sheet);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (sheet.getCustomWriteHandlerList() != null && !sheet.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(sheet.getCustomWriteHandlerList());
        }
        setWriteHandlerMap(sortAndClearUpHandler(handlerList, workbookHolder.getWriteHandlerMap()));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(workbookHolder.converterMap());
        if (sheet.getCustomConverterMap() != null && !sheet.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(sheet.getCustomConverterMap());
        }
        setConverterMap(converterMap);
        setHasBeenInitializedTable(new HashMap<Integer, TableHolder>());
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private void compatibleOldCode(com.alibaba.excel.metadata.Sheet sheet) {
        if (sheet.getColumnWidthMap() != null && !sheet.getColumnWidthMap().isEmpty()) {
            final Map<Integer, Integer> columnWidthMap = sheet.getColumnWidthMap();
            if (sheet.getCustomWriteHandlerList() == null) {
                sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            sheet.getCustomWriteHandlerList().add(new AbstractHeadColumnWidthStyleStrategy() {
                @Override
                protected int columnWidth(Head head) {
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
