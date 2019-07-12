package com.alibaba.excel.metadata.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class TableHolder extends AbstractConfigurationSelector {
    /***
     * poi sheet
     */
    private SheetHolder parentSheet;
    /***
     * tableNo
     */
    private Integer tableNo;
    /**
     * current table param
     */
    private com.alibaba.excel.metadata.Table tableParam;

    public TableHolder(com.alibaba.excel.metadata.Table table, SheetHolder sheetHolder) {
        super();
        this.tableParam = table;
        this.parentSheet = sheetHolder;
        this.tableNo = table.getTableNo();
        boolean noHead = (table.getHead() == null || table.getHead().isEmpty()) && table.getClazz() == null;
        if (noHead) {
            // Use parent
            setHead(sheetHolder.getHead());
            setClazz(sheetHolder.getClazz());
        } else {
            setHead(table.getHead());
            setClazz(table.getClazz());
        }
        setNewInitialization(Boolean.TRUE);
        if (table.getNeedHead() == null) {
            setNeedHead(sheetHolder.needHead());
        } else {
            setNeedHead(table.getNeedHead());
        }
        if (table.getWriteRelativeHeadRowIndex() == null) {
            setWriteRelativeHeadRowIndex(sheetHolder.writeRelativeHeadRowIndex());
        } else {
            setWriteRelativeHeadRowIndex(table.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(table);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (table.getCustomWriteHandlerList() != null && !table.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(table.getCustomWriteHandlerList());
        }
        setWriteHandlerMap(sortAndClearUpHandler(handlerList, sheetHolder.getWriteHandlerMap()));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(sheetHolder.converterMap());
        if (table.getCustomConverterMap() != null && !table.getCustomConverterMap().isEmpty()) {
            converterMap.putAll(table.getCustomConverterMap());
        }
        setConverterMap(converterMap);
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private void compatibleOldCode(com.alibaba.excel.metadata.Table table) {
        if (table.getTableStyle() != null) {
            final TableStyle tableStyle = table.getTableStyle();
            if (table.getCustomWriteHandlerList() == null) {
                table.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
            }
            CellStyle headCellStyle = new CellStyle();
            headCellStyle.setFont(tableStyle.getTableHeadFont());
            headCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            CellStyle contentCellStyle = new CellStyle();
            contentCellStyle.setFont(tableStyle.getTableContentFont());
            contentCellStyle.setIndexedColors(tableStyle.getTableContentBackGroundColor());
            table.getCustomWriteHandlerList().add(new RowCellStyleStrategy(headCellStyle, contentCellStyle));
        }
    }

    public SheetHolder getParentSheet() {
        return parentSheet;
    }

    public void setParentSheet(SheetHolder parentSheet) {
        this.parentSheet = parentSheet;
    }

    public Integer getTableNo() {
        return tableNo;
    }

    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Table getTableParam() {
        return tableParam;
    }

    public void setTableParam(Table tableParam) {
        this.tableParam = tableParam;
    }
}
