package com.alibaba.excel.metadata.holder.read;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.CellStyle;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.metadata.holder.write.AbstractWriteConfiguration;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.style.RowCellStyleStrategy;

/**
 * sheet holder
 *
 * @author zhuangjiaju
 */
public class TableHolder extends AbstractWriteConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableHolder.class);

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
    private Table tableParam;

    public static TableHolder buildWriteWorkTableHolder(Table table, SheetHolder sheetHolder,
                                                        WorkbookHolder workbookHolder) {
        TableHolder tableHolder = new TableHolder();
        tableHolder.setTableParam(table);
        tableHolder.setParentSheet(sheetHolder);
        tableHolder.setTableNo(table.getTableNo());
        boolean noHead = (table.getHead() == null || table.getHead().isEmpty()) && table.getClazz() == null;
        if (noHead) {
            // Use parent
            tableHolder.setHead(sheetHolder.getHead());
            tableHolder.setClazz(sheetHolder.getClazz());
        } else {
            tableHolder.setHead(table.getHead());
            tableHolder.setClazz(table.getClazz());
        }
        tableHolder.setNewInitialization(Boolean.TRUE);
        // Initialization property
        tableHolder.setExcelHeadProperty(
            new ExcelHeadProperty(tableHolder.getClazz(), tableHolder.getHead(), workbookHolder.getConvertAllFiled()));

        if (table.getNeedHead() == null) {
            tableHolder.setNeedHead(sheetHolder.needHead());
        } else {
            tableHolder.setNeedHead(table.getNeedHead());
        }
        if (table.getWriteRelativeHeadRowIndex() == null) {
            tableHolder.setWriteRelativeHeadRowIndex(sheetHolder.writeRelativeHeadRowIndex());
        } else {
            tableHolder.setWriteRelativeHeadRowIndex(table.getWriteRelativeHeadRowIndex());
        }
        // Compatible with old code
        compatibleOldCode(table);
        List<WriteHandler> handlerList = new ArrayList<WriteHandler>();
        if (table.getCustomWriteHandlerList() != null && !table.getCustomWriteHandlerList().isEmpty()) {
            handlerList.addAll(table.getCustomWriteHandlerList());
        }
        // Initialization Annotation
        tableHolder.initAnnotationConfig(handlerList);

        tableHolder
            .setWriteHandlerMap(tableHolder.sortAndClearUpHandler(handlerList, sheetHolder.getWriteHandlerMap()));
        Map<Class, Converter> converterMap = new HashMap<Class, Converter>(sheetHolder.getWriteConverterMap());
        if (table.getCustomConverterList() != null && !table.getCustomConverterList().isEmpty()) {
            for (Converter converter : table.getCustomConverterList()) {
                converterMap.put(converter.getClass(), converter);
            }
        }
        tableHolder.setWriteConverterMap(converterMap);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Table writeHandlerMap:{}", tableHolder.getWriteHandlerMap());
        }
        return tableHolder;
    }

    /**
     * Compatible with old code
     */
    @Deprecated
    private static void compatibleOldCode(Table table) {
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
